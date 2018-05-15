#include <time.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "Sockets.h"
#include "AuthGenerator/AuthKeyGenerator.h"
#include "review.h"
#include "Cominhos.h"

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
	
    c.key=(char*)generate();
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
	strcpy(review.product_name, produtos[idx]); //fetch random product
	
	review.id = getpid();
	review.valor = rand() % 6;
	
	write(sock, &review, sizeof(Review));
	
	printf("\nProduct %s", review.product_name);
	printf("\nPID %d", review.id);
	printf("\nValue %d", review.valor);
	
    close(sock); //Fecha a ligação com o socket    
    return 0;
}
