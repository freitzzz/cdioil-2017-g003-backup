#include "Sockets.h"
#include "AuthGenerator/AuthKeyGenerator.h"
#include "review.h"
#include <time.h>
#include <stdlib.h>
#include <string.h>
/*Constant that represents the failure code*/
#define FAILURE_CODE 400

/*
 * Matriz de produtos.
 */
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
    bzero((char *)&req, sizeof(req)); //Limpa o lixo da estrutura
    req.ai_family = AF_UNSPEC; //Define a familia da ligação
    req.ai_socktype = SOCK_STREAM; //Define o tipo de sockets a ser usado
    
    int failure=getaddrinfo(argv[1], SERVER_PORT, &req, &list); //Preence a estrutura com a informação necessar
    if(failure){
        printf("An error ocured while retrieving server info\n");
        return 0;
    }

    int sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);// Cria um socket respectivo a ser usado na ligacao
    if(sock==-1){
        printf("An error ocured while creating the TCP Connection Socket\n");
        return 0;
    }

    int connectionStatus=connect(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen); //Cria uma ligação TCP a ser lido num certo IP previamento definido
    if(connectionStatus==-1){
        printf("An error ocured while connecting to the TCP Connection\n");
        return 0;
    }
	
	
    char* authenticationKey=(char*)generate();
    write(sock,authenticationKey,strlen(authenticationKey)); //Writes to Socket Stream the Authentication Key
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