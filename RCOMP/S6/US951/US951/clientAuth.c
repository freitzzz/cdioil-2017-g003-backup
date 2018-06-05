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
#include "clientSharedZone.h"
#include "utils/FileWriter.h"
#include "utils/FileReader.h"
#include "utils/StringUtils.h"

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

#define QUEUE_NAME "queueReviews.txt"
#define REVIEW_LABEL "# Review"
#define PRODUCT_NAME_LABEL "PRODUCT_NAME="
#define ID_LABEL "ID="
#define VALOR_LABEL "VALOR="
typedef struct{
		Review* review;
}ReviewQueue;


int queueReviewsSize=0;

Review* initialize_review_shm_queue(){
	int lines=0;
	char** queue=readAllLines(QUEUE_NAME,&lines);
	if(lines==0)return NULL;
	int i;
	int reviews=0;
	for(i=0;i<lines;i++){
		if(startsWith(REVIEW_LABEL,queue[i]))reviews++;
	}
	Review* review=malloc((reviews+1)*sizeof(Review));
	int currentReview=0;
	for(i=0;i<lines;i++){
		if(startsWith(REVIEW_LABEL,queue[i])){
			strcpy(review[currentReview].product_name,getIdentifierName(queue[++i],PRODUCT_NAME_LABEL));
			review[currentReview].id=*(getIdentifierValue(queue[++i],ID_LABEL));
			review[currentReview].valor=*(getIdentifierValue(queue[++i],VALOR_LABEL));
			//printf("Product Name read: %s\nProduct ID read: %d\nProduct Value read: %d\n",review[currentReview].product_name,review[currentReview].id,review[currentReview].valor);
			currentReview++;
		}
	}
	queueReviewsSize=currentReview;
	return review;
}

void persist_queue_reviews(Review* queueReviews,Review reviewToPersist){
	if(!queueReviewsSize){
			queueReviewsSize=0;
			queueReviews=malloc(sizeof(Review));
			strcpy(queueReviews[queueReviewsSize].product_name,reviewToPersist.product_name);
			queueReviews[queueReviewsSize].id=reviewToPersist.id;
			queueReviews[queueReviewsSize].valor=reviewToPersist.valor;
			queueReviewsSize++;
	}
	int i,j;
	int reviewSize=((strlen(REVIEW_LABEL)+strlen(PRODUCT_NAME_LABEL)+strlen(ID_LABEL)+strlen(VALOR_LABEL)+5)+sizeof(Review));
	char queueContent[reviewSize][queueReviewsSize];
	for(i=0;i<queueReviewsSize;i++){
			snprintf(queueContent[i],reviewSize,"%s\n%s%s\n%s%d\n%s%d\n",REVIEW_LABEL,PRODUCT_NAME_LABEL,queueReviews[i].product_name,ID_LABEL,queueReviews[i].id,VALOR_LABEL,queueReviews[i].valor);
	}
	int queueContentSize=(reviewSize*queueReviewsSize);
	int currentSize=0;
	//char* content=malloc(500);
	// for(i=0;i<queueReviewsSize;i++)
	// 	for(j=0;j<reviewSize;j++)
	// 		content[currentSize]=queueContent[i][j];
	// printf("->>>>>>>>>>>>>> %s\n",content);
	write_all_lines(QUEUE_NAME,(char*)queueContent);
}


/*Runs the Machine*/
/*The Server IP needs to be passed as argument*/
int main(int argc,char *argv[]){
    if(argc!=2){
        printf("You need to pass the Server IP as argument\n");
        return 0;
    }

	SSL_CTX *sslctx; //SSL context structure
	SSL *cSSL; //SSL socket

	initializeSSL(); //Initializes SSL libraries



    struct addrinfo req, *list;
    bzero((char *)&req, sizeof(req)); //Clears the structure
    req.ai_family = AF_UNSPEC; //Defines the family of the connection
    req.ai_socktype = SOCK_STREAM; //Defines the type of the socket

    int failure=getaddrinfo(argv[1], SERVER_PORT, &req, &list); //Fills the structure with the needed information
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

	Review* queueReviews=initialize_review_shm_queue();


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
	persist_queue_reviews(queueReviews,review);
	if(ssl_err <= 0){
		persist_queue_reviews(queueReviews,review);
		printf("An error occured while sending the review to the server\n");
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

	close(sock);
	SSL_free(cSSL);
	SSL_CTX_free(sslctx);

    return 0;
}
