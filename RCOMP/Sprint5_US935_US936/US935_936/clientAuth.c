#include <time.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "Sockets.h"
#include "AuthGenerator/AuthKeyGenerator.h"
#include "review.h"
#include "Cominhos.h"
#include "aes.h"

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
/*The Server IP needs to be passed as argument*/
int main(int argc,char *argv[]){
    if(argc!=2){
        printf("You need to pass the Server IP as argument\n");
        return 0;
    }
    
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
        printf("An error ocured while connecting to the TCP Connection\n");
        return 0;
    }
	
    Cominhos c;
	
	char temp[256];
	
	strncpy(temp, (char*) generate(), sizeof(temp));
	strncpy(c.key, temp, sizeof(temp));
	
    time(&c.timestamp);

    write(sock,&c,sizeof(c)); //Writes to Socket Stream the Authentication Key and the Timestamp

    int statusCode=0;
    read(sock,&statusCode,sizeof(statusCode));	//Reads from Sockect Stream the Authentication Key recieved from the server
	
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
	
	write(sock, &review, sizeof(Review));
	
	printf("\nProduct %s", randomProduct);
	printf("\nPID %d", id);
	printf("\nValue %u\n", randomValor);
	
    close(sock); //Closes the connection with the socket    
    return 0;
}
