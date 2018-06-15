#include <time.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "Sockets.h"
#include "AuthGenerator/AuthKeyGenerator.h"
#include "review.h"
#include "Cominhos.h"
#include "aes.h"
#include "SSL_Files.h"

/*Constant that represents the failure code*/
#define FAILURE_CODE 400

/* Matrix with all products */
char produtos[][20] = {
	"Iogurte Continente",
	"Vinho Verde",
	"Massa Esparguete",
	"Agua 50cl",
	"Lápis",
	"Livro",
	"Pao",
	"Cereais",
	"Leite"};


/*Runs the Machine*/
/*The server IP address is received through an UDP broadcast reply*/
int main(){

	struct sockaddr_storage serverAddr;
	int udp_sock, udp_bcast_flag, udp_err;
	unsigned int serverAddrLen;	
	struct addrinfo udp_req, *udp_list;

	bzero((char *)&udp_req, sizeof(udp_req)); 	//Clears the structure
	udp_req.ai_family = AF_INET;				//request IPv4 address in order to use broadcast
	udp_req.ai_socktype = SOCK_DGRAM;			//use UDP socket for broadcasting

	udp_err = getaddrinfo(BCAST_ADDRESS, UDP_PORT, &udp_req, &udp_list);	//attempt to get the broadcast address for port "9998"

	if(udp_err){
		printf("Failed to get broadcast address\n");
		return 0;
	}

	serverAddrLen = udp_list->ai_addrlen;
	memcpy(&serverAddr, udp_list->ai_addr, serverAddrLen);	//store broadcast address
	freeaddrinfo(udp_list);									//clear address list

	bzero((char *)&udp_req, sizeof(udp_req));				//clear structure
	udp_req.ai_family = AF_INET;							//use IPv4
	udp_req.ai_socktype = SOCK_DGRAM;						//use UDP
	udp_req.ai_flags = AI_PASSIVE;							//local address
	
	udp_err = getaddrinfo(NULL, "0", &udp_req, &udp_list);	//Port 0 = auto assign	

	if(udp_err){
		printf("Failed to get local address\n");
		return 0;
	}

	//create socket
	udp_sock = socket(udp_list->ai_family, udp_list->ai_socktype, udp_list->ai_protocol);

	if(udp_sock == -1){
		printf("Failed to open UDP socket\n");
		freeaddrinfo(udp_list);
		return 0;
	}

	//activate broadcast permission
	udp_bcast_flag = 1;
	setsockopt(udp_sock, SOL_SOCKET, SO_BROADCAST, &udp_bcast_flag, sizeof(udp_bcast_flag));

	int bind_status = bind(udp_sock, (struct sockaddr *)udp_list->ai_addr, udp_list->ai_addrlen);

	if(bind_status == -1){
		printf("Error while binding socket\n");
		freeaddrinfo(udp_list);
		return 0;
	}

	freeaddrinfo(udp_list);

	//use previously saved server address information
	int sent_status= sendto(udp_sock, (char *) NULL, 0, 0, (struct sockaddr*)&serverAddr, serverAddrLen);

	if(sent_status == -1){
		printf("Connection declined. Data not sent\n");
		return 0;
	}

	int reception_status = recvfrom(udp_sock, (char *) NULL, 0, 0, (struct sockaddr*)&serverAddr, &serverAddrLen);

	if(reception_status == -1){
		printf("An error occured while receiving data from server");
		return 0;
	}
	close(udp_sock); //close udp socket connection since review data will be sent using tcp

	char server_ip[BUF_SIZE];
	char server_port[BUF_SIZE];


	if(!getnameinfo((struct sockaddr *)&serverAddr, serverAddrLen, server_ip, BUF_SIZE,
                            server_port, BUF_SIZE, NI_NUMERICHOST | NI_NUMERICSERV)){

		printf("Server IP: %s\n", server_ip);
		printf("Server UDP port: %s\n", server_port);	
	}


	
	//FINISH USING UDP, USE TCP FROM THIS POINT
    
	SSL_CTX *sslctx; //SSL context structure
	SSL *cSSL; //SSL socket
	
	initializeSSL(); //Initializes SSL libraries
	
	
	
    struct addrinfo req, *list;
    bzero((char *)&req, sizeof(req)); //Clears the structure
    req.ai_family = AF_UNSPEC; //Defines the family of the connection
    req.ai_socktype = SOCK_STREAM; //Defines the type of the socket
    
	//instead of using a parametrized IP address, use the one in the broadcast reply
    int failure=getaddrinfo(server_ip, TCP_PORT, &req, &list); //Fills the structure with the needed information
    if(failure){
        printf("An error ocured while retrieving server info\n");
        return 0;
    }

    int sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol); //Creates a socket to use in the TCP connection
    if(sock==-1){
        printf("An error ocured while creating the TCP Connection Socket\n");
        return 0;
    }

    int connectionStatus=connect(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen); //Creates the TCP connection
    if(connectionStatus==-1){
        printf("An error occured while connecting to the TCP Connection\n");
        return 0;
    }
	
	/*Context structure for server and client with SSLv2_method*/
	sslctx = SSL_CTX_new(SSLv2_method()); 
	
	/*Load CA Certificate*/
	int load_cert = SSL_CTX_load_verify_locations(sslctx, NULL, CERT_FILE);
	SSL_CTX_set_options(sslctx, SSL_OP_SINGLE_DH_USE);
	if(load_cert <= 0){
		printf("An error occured while loading the server's certificate\n");
		return 0;
	}
	
	/*Certificate and key set up*/
	int use_cert = SSL_CTX_use_certificate_file(sslctx, CERT_FILE , SSL_FILETYPE_PEM);
	if(use_cert <= 0){
		printf("An error occured while setting up the client's certificate file\n");
		return 0;
	}
	
	/*Load the password for the Private Key*/
	SSL_CTX_set_default_passwd_cb_userdata(sslctx,KEY_PASSWD);
	
	int use_prv = SSL_CTX_use_PrivateKey_file(sslctx, PRIVATE_KEY_FILE, SSL_FILETYPE_PEM);
	if(use_prv <= 0){
		printf("An error occured while setting up the client's private key\n");
	}
	
	/*Make sure the key and certificate file match*/
	if (SSL_CTX_check_private_key(sslctx) == 0) {
	   printf("Private key does not match the certificate public key\n");
	   exit(0);
	}
	
	/* Set the list of trusted CAs based on the file and/or directory provided*/
	if(SSL_CTX_load_verify_locations(sslctx,CERT_FILE,NULL)<1) {
	   printf("Error setting verify location\n");
	   exit(0);
	}
	
	/* Set for server verification*/
	SSL_CTX_set_verify(sslctx,SSL_VERIFY_PEER,NULL);
	
    Cominhos c;
	
	char temp[256];
	
	strncpy(temp, (char*) generate(), sizeof(temp));
	strncpy(c.key, temp, sizeof(temp));
	
    time(&c.timestamp);

	cSSL = SSL_new(sslctx); //Creates the SSL socket with a specific context structure
	SSL_set_fd(cSSL, sock); //Connects the SSL object with a file descriptor
	
	int ssl_err = SSL_connect(cSSL); //Initiates SSL handshake
	
	if(ssl_err < 1){
		printf("An error occured while performing the SSL handshake\n");
		return 0;
	}
	
	ssl_err = SSL_write(cSSL,&c,sizeof(c));
	
	if(ssl_err <= 0){
		printf("An error occured while reading the code sent from the server\n");
		ssl_err = SSL_get_error(cSSL,ssl_err);
		if(ssl_err == 6){
			SSL_shutdown(cSSL);
			SSL_free(cSSL);
			close(sock);
			SSL_CTX_free(sslctx);
		}
		return 0;
	}

	int statusCode = 0;
	
	ssl_err = SSL_read(cSSL,&statusCode,sizeof(statusCode));
	
	if(ssl_err <= 0){
		printf("An error occured while reading the code sent from the server\n");
		ssl_err = SSL_get_error(cSSL,ssl_err);
		if(ssl_err == 6){
			SSL_shutdown(cSSL);
			SSL_free(cSSL);
			close(sock);
			SSL_CTX_free(sslctx);
		}
		return 0;
	}
	
	if(statusCode==FAILURE_CODE){
		printf("The device is not allowed to send reviews\n");
		close(sock);
		return 0;
	}
	
	Review review;
	time_t t;
	
	srand(time(&t)); //generate seed
	int idx = rand() % 9;	
	
	BYTE randomProduct[20];
	strcpy((char*)randomProduct, produtos[idx]); //fetch random product
	int id = getpid();
	int randomValor = rand() % 6;
	
	WORD key_schedule[60];
	BYTE iv[1][16] = {
		{0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f}
	};
	
	BYTE key[1][32];
	strncpy((char*)key, c.key, 32); //Gets first 32 bytes of authentication key for encryption key
	//Prepares key for encryption
	aes_key_setup(key[0], key_schedule, 256);
	//Encrypt the information
	aes_encrypt_ctr((BYTE*)randomProduct,20, (BYTE*)review.product_name, key_schedule, 256, iv[0]);
	aes_encrypt_ctr((BYTE*)&id,4, (BYTE*)&review.id, key_schedule, 256, iv[0]);
	aes_encrypt_ctr((BYTE*)&randomValor,4, (BYTE *)&review.valor, key_schedule, 256, iv[0]);
	
	ssl_err = SSL_write(cSSL,&review,sizeof(Review)); //Writes the review to the socket
	if(ssl_err <= 0){
		printf("An error occured while reading the code sent from the server\n");
		ssl_err = SSL_get_error(cSSL,ssl_err);
		if(ssl_err == 6){
			SSL_shutdown(cSSL);
			SSL_free(cSSL);
			close(sock);
			SSL_CTX_free(sslctx);
		}
		return 0;
	}
	
	printf("\nProduct %s", randomProduct);
	printf("\nPID %d", id);
	printf("\nValue %u\n", randomValor);
	
	ssl_err=SSL_shutdown(cSSL);
	int count = 1;

	while(ssl_err != 1) {
		ssl_err=SSL_shutdown(cSSL);
		
		if(ssl_err != 1){
			count++;
		}
		
		if (count == 5){
			break;
		}
		sleep(1);
	}

	if(ssl_err<0){
		printf("Error in shutdown\n");
	}else if(ssl_err==1){
		printf("Client exited gracefully\n");
	}
	
	SSL_free(cSSL);
	close(sock);
	//SSL_CTX_free(sslctx); //this is commented out temporarily since this was causing segmentation fault
	
    return 0;
}
