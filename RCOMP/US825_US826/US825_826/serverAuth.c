#define _GNU_SOURCE
#include <time.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "Sockets.h"
#include "Utils/FileReader.h"
#include "Utils/StringUtils.h"
#include "AuthGenerator/AuthKeyGenerator.h"
#include "review.h"
#include "sharedmemory.h"
#include <semaphore.h>
#include <sys/wait.h>
#include <signal.h>

/*Constant that represents the valid key code sent to machines that are not allowed to send reviews*/
#define VALID_KEY_CODE 200
/*Constant that represents the invalid key code sent to machines that are not allowed to send reviews*/
#define INVALID_KEY_CODE 400

/*Function that checks if a certain authentication key is valid*/
/*Returns 1 if authentication key is valid, false if not*/
int isAuthKeyAllowed(char** authenticationKeys,char* authenticationKey,int authenticationKeysTotal){
    int i;
    for(i=0;i<authenticationKeysTotal;i++)
        if(startsWith(authenticationKey,authenticationKeys[i]))return 1;
    return 0;
}

typedef struct{
	int counter;
	int increment;
	int size;
}Counter;

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
	int authenticationKeysTotal=0;
    char** authenticationKeys=readAllLines(argv[1],&authenticationKeysTotal);
    struct addrinfo req, *list;
    struct sockaddr_storage from;
    char clientIP[BUF_SIZE];
    char clientPort[BUF_SIZE];
    char receivedCode[BUF_SIZE];
    bzero((char *)&req, sizeof(req)); //Limpa o lixo da estrutura
    req.ai_family = AF_UNSPEC; //Define a familia da ligação
    req.ai_socktype = SOCK_STREAM; //Define o tipo de sockets a ser usado
    req.ai_flags = AI_PASSIVE; // local address
	
	
	sem_t *mutex_semaphore;
	mutex_semaphore = sem_open("/sprint4semaphore", O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, 1); //mutual exclusion semaphore
	
	
	int fd_counter = shm_open("/sprint4counter", O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
	ftruncate(fd_counter, sizeof(Counter));
	Counter *review_counter_ptr;
	review_counter_ptr = (Counter *) mmap(NULL, sizeof(int), PROT_READ | PROT_WRITE, MAP_SHARED, fd_counter, 0);
	review_counter_ptr->counter = 0;		//number of currently loaded reviews
	review_counter_ptr->increment = 10;		//value defined for icrementing shared memory area
	review_counter_ptr->size = 5;			//shared memory's initial size
	
	int fd_review = shm_open("/sprint4reviews", O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
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
            read(newSock,receivedCode,BUF_SIZE);
            int code= isAuthKeyAllowed(authenticationKeys,receivedCode,authenticationKeysTotal) ? VALID_KEY_CODE : INVALID_KEY_CODE;
            write(newSock,&code,sizeof(code));
			
			Review client_review;
			read(newSock, &client_review, sizeof(Review));
			
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
			
			close(newSock);
            exit(5);
        }
		
        close(newSock);
    }
	
    return 0;
}