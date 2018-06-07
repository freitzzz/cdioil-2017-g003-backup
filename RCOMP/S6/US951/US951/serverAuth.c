#define _GNU_SOURCE
#include <time.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <semaphore.h>
#include <sys/wait.h>
#include <signal.h>
#include <time.h>
#include "Sockets.h"
#include "Utils/FileReader.h"
#include "Utils/StringUtils.h"
#include "review.h"
#include "sharedmemory.h"
#include "Cominhos.h"
#include "aes.h"
#include "SSL_Files.h"

/*Constant that represents the valid key code sent to machines that are not allowed to send reviews*/
#define VALID_KEY_CODE 200
/*Constant that represents the invalid key code sent to machines that are not allowed to send reviews*/
#define INVALID_KEY_CODE 400
/*Constant that represents the expiration time for a device's key*/
#define EXPIRATION_TIME 2

/*Function that checks if a certain authentication key is valid*/
/*Returns 1 (true) if the authentication key is valid, 0 (false) if not*/
int isAuthKeyAllowed(char** authenticationKeys,char* authenticationKey,int authenticationKeysTotal){
    int i;
    for(i=0;i<authenticationKeysTotal;i++)
        if(startsWith(authenticationKey,authenticationKeys[i])){
			return 1;
		}
    return 0;
}

/* Function that checks if a certain timestamp is valid (hasn't expired) */
/* Returns 1 (true) if the timestamp is valid. Otherwise, returns 0 (false) */
int checkTimeStamp(time_t timestamp){
	time_t actual_time;
	time(&actual_time);

	double difference = difftime(actual_time, timestamp);

	return difference < EXPIRATION_TIME;
}

/* Structure that represents a counter */
typedef struct{
	int counter;
	int increment;
	int size;
} Counter;

/* Unlinks the shared memory zones and the semaphore */
void sighandler(){
	shm_unlink("/sprint4counter");
	shm_unlink("/sprint4reviews");
	sem_unlink("/sprint4semaphore");
	exit(0);
}

/*Runs the Server*/
/*Recieves through parameter the name of the file with the machines authentication keys*/
int main(int argc,char *argv[]){

	signal(SIGINT, sighandler);

    if(argc!=2){
        printf("The server needs an configuration file with the machines authentication keys\nPlease run the server with the file name passed through parameters\n");
        return 0;
    }

	SSL_CTX *sslctx; //SSL context structure
	SSL *cSSL; //SSL socket

	initializeSSL(); //Initializes SSL libraries

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
		printf("An error occured while setting up the server's certificate file\n");
		return 0;
	}

	/*Load the password for the Private Key*/
	SSL_CTX_set_default_passwd_cb_userdata(sslctx,KEY_PASSWD);

	int use_prv = SSL_CTX_use_PrivateKey_file(sslctx, PRIVATE_KEY_FILE, SSL_FILETYPE_PEM);
	if(use_prv <= 0){
		printf("An error occured while setting up the server's private key\n");
	}

	/*Make sure the key and certificate file match*/
	if (SSL_CTX_check_private_key(sslctx) == 0) {
	   printf("Private key does not match the certificate public key\n");
	   exit(0);
	}

	/*Used only if client authentication will be used*/
	SSL_CTX_set_verify(sslctx,SSL_VERIFY_PEER|SSL_VERIFY_FAIL_IF_NO_PEER_CERT,NULL);

	/* Set the list of trusted CAs based on the file and/or directory provided*/
	if(SSL_CTX_load_verify_locations(sslctx,CERT_FILE,NULL)<1) {
	   printf("Error setting verify location\n");
	   exit(0);
	}

	/* Set CA list used for client authentication. */
	if(SSL_load_client_CA_file(CERT_FILE) == NULL) {
		printf("Error setting CA list.\n");
		exit(0);
	}

	cSSL = SSL_new(sslctx); //Creates SSL object

	int authenticationKeysTotal=0;
    char** authenticationKeys=readAllLines(argv[1],&authenticationKeysTotal);
    struct addrinfo req, *list;
    struct sockaddr_storage from;
    char clientIP[BUF_SIZE];
    char clientPort[BUF_SIZE];
    Cominhos receivedCode;
    bzero((char *)&req, sizeof(req)); //Clears the structure
    req.ai_family = AF_UNSPEC; //Defines the family of the connection
    req.ai_socktype = SOCK_STREAM; //Defines the type of socket to use
    req.ai_flags = AI_PASSIVE; // local address


	sem_t *mutex_semaphore;
	mutex_semaphore = sem_open("/sprint6semaphore", O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, 1); //mutual exclusion semaphore


	int fd_counter = shm_open("/sprint6counter", O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
	ftruncate(fd_counter, sizeof(Counter));
	Counter *review_counter_ptr;
	review_counter_ptr = (Counter *) mmap(NULL, sizeof(int), PROT_READ | PROT_WRITE, MAP_SHARED, fd_counter, 0);
	review_counter_ptr->counter = 0;		//number of currently loaded reviews
	review_counter_ptr->increment = 10;		//value defined for icrementing shared memory area
	review_counter_ptr->size = 5;			//shared memory's initial size

	int fd_review = shm_open("/sprint6reviews", O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
	int shm_size = review_counter_ptr->size * sizeof(Review);
	ftruncate(fd_review, shm_size); 		//create shared memory area with size of 5 reviews
	Review *shared_review;
	shared_review = (Review *) mmap(NULL, shm_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd_review, 0);

    int failure=getaddrinfo(NULL, SERVER_PORT, &req, &list); //Fills struct with necessary info
    if(failure)return 0;

    int sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);	//Creates a socket for listening to new connections
    if(sock==-1)return 0;

    int binded=bind(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen);
    if(binded==-1)return 0;

    freeaddrinfo(list);
    listen(sock, SOMAXCONN);

    unsigned int adl=sizeof(from);
    short catched=0;

    while(!catched){
        int newSock = accept(sock, (struct sockaddr *)&from, &adl);
        if(!fork()){
            close(sock);
            getnameinfo((struct sockaddr *)&from, adl, clientIP, BUF_SIZE,
                            clientPort, BUF_SIZE, NI_NUMERICHOST | NI_NUMERICSERV);
			/* Bind the ssl object with the socket*/
			SSL_set_fd(cSSL,newSock);

			/*Do the SSL Handshake*/
			int ssl_err=SSL_accept(cSSL);
			if(ssl_err < 1){
				printf("An error occured while doing the SSL handshake\n");
				return 0;
			}

			/* Check for Client authentication error */
			if (SSL_get_verify_result(cSSL) != X509_V_OK) {
				printf("SSL Client Authentication error\n");
				SSL_free(cSSL);
				close(newSock);
				SSL_CTX_free(sslctx);
				return 0;
			}

			ssl_err = SSL_read(cSSL, &receivedCode, BUF_SIZE);

			if(ssl_err<1) {
				ssl_err=SSL_get_error(cSSL,ssl_err);
				printf("Error #%d in read,program terminated\n",ssl_err);
				if(ssl_err==6){
					SSL_shutdown(cSSL);
				}
				SSL_free(cSSL);
				close(newSock);
				SSL_CTX_free(sslctx);
				return 0;
			 }

			int timestamp_flag = checkTimeStamp(receivedCode.timestamp);
			if(timestamp_flag){

            int code= isAuthKeyAllowed(authenticationKeys,receivedCode.key,authenticationKeysTotal) ? VALID_KEY_CODE : INVALID_KEY_CODE;

			ssl_err = SSL_write(cSSL,&code,sizeof(code));

			/*Check for error in write.*/
			if(ssl_err < 1) {
				ssl_err=SSL_get_error(cSSL,ssl_err);
				printf("Error #%d in write,program terminated\n",ssl_err);
				if(ssl_err==6){
					SSL_shutdown(cSSL);
				}
				SSL_free(cSSL);
				close(newSock);
				SSL_CTX_free(sslctx);
				return 0;
			  }
        printf("->>> %d\n",code);
            if(code != INVALID_KEY_CODE){

				Review client_review;

				int ssl_err2 = SSL_read(cSSL, &client_review, sizeof(Review));

				if(ssl_err2<1) {
					ssl_err2=SSL_get_error(cSSL,ssl_err2);
					printf("Error #%d in read,program terminated\n",ssl_err2);
					if(ssl_err2==6){
						SSL_shutdown(cSSL);
					}
					SSL_free(cSSL);
					close(newSock);
					SSL_CTX_free(sslctx);
					return 0;
				 }

				BYTE randomProduct[20];
				int randomValor;
				int id;

				WORD key_schedule[60];
				BYTE iv[1][16] = {
					{0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f}
				};
				BYTE key[1][32];
				strncpy((char*)key, receivedCode.key, 32); //Gets first 32 bytes of authentication key for encryption key
				//Prepares key for encryption
				aes_key_setup(key[0], key_schedule, 256);
				//Dencrypt the information
				aes_decrypt_ctr((BYTE*)client_review.product_name, 20,randomProduct,key_schedule, 256, iv[0]);
				aes_decrypt_ctr((unsigned char *)&client_review.valor, 4,(unsigned char*)&randomValor, key_schedule, 256, iv[0]);
				aes_decrypt_ctr((unsigned char *)&client_review.id, 4,(unsigned char*)&id, key_schedule, 256, iv[0]);
				strcpy(client_review.product_name,(char*)randomProduct);
				client_review.valor = randomValor;
				client_review.id = id;

				//mutex semaphore for incrementing variable
				sem_wait(mutex_semaphore);

				/*This memory mapping instruction is contained within the critical execution area,
				since it might no longer be valid if another process changes the mapping*/
				shared_review = (Review *) mmap(NULL, shm_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd_review, 0);

				if(review_counter_ptr->counter == review_counter_ptr->size){
					int old_size = review_counter_ptr->size * sizeof(Review);
					review_counter_ptr->size += review_counter_ptr->increment;
					int new_size = review_counter_ptr->size * sizeof(Review);
					ftruncate(fd_review, new_size);
					shared_review = (Review *) mremap(shared_review, old_size, new_size, MREMAP_MAYMOVE);
					printf("\n \t \t MEMORY REALLOCATED\n");
				}

				int counter = review_counter_ptr->counter;

				shared_review += counter;

				strcpy(shared_review->product_name, client_review.product_name);
				shared_review->valor = client_review.valor;
				shared_review->id = client_review.id;

				review_counter_ptr->counter++;

				sem_post(mutex_semaphore);

				printf("\nProduct %s", shared_review->product_name);
				printf("\nPID %d", shared_review->id);
				printf("\nValue %d", shared_review->valor);
				printf("\nCounter %d", review_counter_ptr->counter);
				printf("\n %p", shared_review);
			}
			} else {
				int invalid_value = INVALID_KEY_CODE;

				int ssl_err3 = SSL_write(cSSL,&invalid_value,sizeof(invalid_value));

				/*Check for error in write.*/
				if(ssl_err3 < 1) {
					ssl_err3=SSL_get_error(cSSL,ssl_err3);
					printf("Error #%d in write,program terminated\n",ssl_err3);
					if(ssl_err3==6){
						SSL_shutdown(cSSL);
					}
					SSL_free(cSSL);
					close(newSock);
					SSL_CTX_free(sslctx);
					return 0;
				  }
			}
			  int err= SSL_shutdown(cSSL);
			  int count = 1;
			  while(err != 1) {
				err=SSL_shutdown(cSSL);
				if(err != 1){
					count++;
				}
				if(count == 5){
					break;
				}
				sleep(1);
			  }
			  if(err<0){
				printf("Error in shutdown\n");
			  }else if(err==1){
				printf("\nServer exited gracefully\n");
			  }
			  SSL_free(cSSL);
			  close(newSock);
			  SSL_CTX_free(sslctx);
			  exit(5);
        }
    }
    return 0;
}
