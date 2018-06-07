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

  #define QUEUE_NAME "ReviewDatabase.bin"
  #define READ_RIGHTS "rb"
  #define WRITE_RIGHTS "wb"

  int currentReviews=0;
  int currentReviewsSize=1;
  int currentReviewsIndex=0;

  Review* initialize_review_shm_queue() {
    currentReviews=0;
    currentReviewsSize=100;
      FILE* fileStream=fopen(QUEUE_NAME,READ_RIGHTS);
      Review* reviews=malloc(currentReviewsSize*sizeof(Review));
      if(fileStream!=NULL){
        while(!feof(fileStream)){
          if(currentReviews==currentReviewsSize){
            currentReviewsSize+=5;
            reviews=realloc(reviews,currentReviewsSize);
          }
          Review reviewX;
          fread(&reviewX,sizeof(Review),1,fileStream);
          strcpy(reviews[currentReviews].product_name,reviewX.product_name);
          reviews[currentReviews].id=reviewX.id;
          reviews[currentReviews].valor=reviewX.valor;
          currentReviews++;
        }
      }else{
        currentReviews++;
      }
      return reviews;
  }


void persist_queue_reviews(Review* queueReviews) {
      FILE* fileStream=fopen(QUEUE_NAME,WRITE_RIGHTS);
      if(fileStream==NULL)return;
      int i;
      for(i=0; i<currentReviews; i++) {
          if(queueReviews[i].id!=-1) {
              fwrite(&queueReviews[i],sizeof(Review),1,fileStream);
          }
      }
      fclose(fileStream);
  }

  Review* get_reviews_to_persist(Cominhos c){
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
    Review* reviewsX=initialize_review_shm_queue();
    strcpy(reviewsX[0].product_name,review.product_name);
    reviewsX[0].id=review.id;
    reviewsX[0].valor=review.valor;
    return reviewsX;
  }


int send_reviews(char* ip,Cominhos c,Review* reviews,int currentIndex){
  SSL_CTX *sslctx; //SSL context structure
	SSL *cSSL; //SSL socket

	initializeSSL(); //Initializes SSL libraries


    struct addrinfo req, *list;
    bzero((char *)&req, sizeof(req)); //Clears the structure
    req.ai_family = AF_UNSPEC; //Defines the family of the connection
    req.ai_socktype = SOCK_STREAM; //Defines the type of the socket
    int failure=getaddrinfo(ip, SERVER_PORT, &req, &list); //Fills the structure with the needed information
    if(failure){
        printf("An error ocured while retrieving server info\n");
        persist_queue_reviews(reviews);
        return 0;
    }
    int sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol); //Creates a socket to use in the TCP connection
    if(sock==-1){
        printf("An error ocured while creating the TCP Connection Socket\n");
        persist_queue_reviews(reviews);
        return 0;
    }

    int connectionStatus=connect(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen); //Creates the TCP connection
    if(connectionStatus==-1){
        printf("An error occured while connecting to the TCP Connection\n");
        persist_queue_reviews(reviews);
        return 0;
    }

	/*Context structure for server and client with SSLv2_method*/
	sslctx = SSL_CTX_new(SSLv2_method());

	/*Load CA Certificate*/
	int load_cert = SSL_CTX_load_verify_locations(sslctx, NULL, CERT_FILE);
	SSL_CTX_set_options(sslctx, SSL_OP_SINGLE_DH_USE);
	if(load_cert <= 0){
		printf("An error occured while loading the server's certificate\n");
    persist_queue_reviews(reviews);
		return 0;
	}

	/*Certificate and key set up*/
	int use_cert = SSL_CTX_use_certificate_file(sslctx, CERT_FILE , SSL_FILETYPE_PEM);
	if(use_cert <= 0){
		printf("An error occured while setting up the client's certificate file\n");
    persist_queue_reviews(reviews);
		return 0;
	}

	/*Load the password for the Private Key*/
	SSL_CTX_set_default_passwd_cb_userdata(sslctx,KEY_PASSWD);

	int use_prv = SSL_CTX_use_PrivateKey_file(sslctx, PRIVATE_KEY_FILE, SSL_FILETYPE_PEM);
	if(use_prv <= 0){
		printf("An error occured while setting up the client's private key\n");
    persist_queue_reviews(reviews);
    return 0;
	}

	/*Make sure the key and certificate file match*/
	if (SSL_CTX_check_private_key(sslctx) == 0) {
	   printf("Private key does not match the certificate public key\n");
     persist_queue_reviews(reviews);
	   return 0;
	}

	/* Set the list of trusted CAs based on the file and/or directory provided*/
	if(SSL_CTX_load_verify_locations(sslctx,CERT_FILE,NULL)<1) {
	   printf("Error setting verify location\n");
     persist_queue_reviews(reviews);
	   return 0;
	}

	/* Set for server verification*/
	SSL_CTX_set_verify(sslctx,SSL_VERIFY_PEER,NULL);



	cSSL = SSL_new(sslctx); //Creates the SSL socket with a specific context structure
	SSL_set_fd(cSSL, sock); //Connects the SSL object with a file descriptor

	int ssl_err = SSL_connect(cSSL); //Initiates SSL handshake

	if(ssl_err < 1){
		printf("An error occured while performing the SSL handshake\n");
    persist_queue_reviews(reviews);
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
    persist_queue_reviews(reviews);
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
    persist_queue_reviews(reviews);
		return 0;
	}
	if(statusCode==FAILURE_CODE){
		printf("The device is not allowed to send reviews\n");
		close(sock);
    persist_queue_reviews(reviews);
		return 0;
	}



	ssl_err = SSL_write(cSSL,&reviews[currentIndex],sizeof(Review)); //Writes the review to the socket
  reviews[currentIndex].id=-1;
  if(ssl_err <= 0){
		printf("An error occured while reading the code sent from the server\n");
		ssl_err = SSL_get_error(cSSL,ssl_err);
		if(ssl_err == 6){
			SSL_shutdown(cSSL);
			SSL_free(cSSL);
			close(sock);
			SSL_CTX_free(sslctx);
		}
    persist_queue_reviews(reviews);
		return 0;
	}


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
    persist_queue_reviews(reviews);
    return 0;
	}else if(ssl_err==1){
		printf("Client exited gracefully\n");
	}

	close(sock);
	SSL_free(cSSL);
	SSL_CTX_free(sslctx);
  return 0;
}

Cominhos start_cominhos(char* authenticationKey){
  Cominhos c;

  char temp[256];

  strncpy(temp,authenticationKey, sizeof(temp));
  strncpy(c.key, temp, sizeof(temp));

  time(&c.timestamp);
  return c;
}

/*Runs the Machine*/
/*The Server IP needs to be passed as argument*/
int main(int argc,char *argv[]){
    if(argc!=3){
        printf("You need to pass the Server IP as argument & the client authentication key\n");
        return 0;
    }
    while(1){
      Cominhos c=start_cominhos(argv[2]);
      Review* reviews_to_persist=get_reviews_to_persist(c);
      if(currentReviews!=1){
        int nextReview=0;
        while(currentReviews!=nextReview){
          send_reviews(argv[1],c,reviews_to_persist,nextReview++);
          c=start_cominhos(argv[2]);
        }
        persist_queue_reviews(reviews_to_persist);
      }else{
        send_reviews(argv[1],c,reviews_to_persist,0);
      }
			sleep(5);
		}
    return 0;
}
