#include <time.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <signal.h>
#include <time.h>
#include "Sockets.h"
#include "Utils/FileReader.h"
#include "Utils/StringUtils.h"
#include "AuthGenerator/AuthKeyGenerator.h"
#include "review.h"
#include "sharedmemory.h"
#include "Cominhos.h"
#include <pthread.h>


/*Constant that represents the valid key code sent to machines that are not allowed to send reviews*/
#define VALID_KEY_CODE 200
/*Constant that represents the invalid key code sent to machines that are not allowed to send reviews*/
# define INVALID_KEY_CODE 400
/*Constant that represents the expiration time for a device's key*/
# define EXPIRATION_TIME 2


/*Global Variables*/
Review* shared_review;	//pointer used for heap memory block
pthread_mutex_t mutex;	//mutex used for writing into the heap memory block and for printing review data
pthread_cond_t cond;	//condition used for waking thread responsible for printing review data
/*Variables responsible for reallocating memory block*/
int last_written_review_index = 0, written_review_counter = 0, review_block_size = 5, review_block_increment = 10;



/*Structure that represents parameters being passed to the threads responsible for client connections*/
typedef struct {
	struct sockaddr_storage from;
	Cominhos receivedCode;
	unsigned int adl;
	int authenticationKeysTotal;
	int newSock;
	char* authenticationKeys;
	char clientIP[BUF_SIZE];
	char clientPort[BUF_SIZE];
}ConnectionParameters;

/*Function that checks if a certain authentication key is valid*/
/*Returns 1 (true) if the authentication key is valid, 0 (false) if not*/
int isAuthKeyAllowed(char** authenticationKeys, char* authenticationKey, int authenticationKeysTotal) {
	int i;
	for (i = 0; i< authenticationKeysTotal; i++)
	if (startsWith(authenticationKey, authenticationKeys[i])) return 1;
	return 0;
}

/* Function that checks if a certain timestamp is valid (hasn't expired) */
/* Returns 1 (true) if the timestamp is valid. Otherwise, returns 0 (false) */
int checkTimeStamp(time_t timestamp) {
	time_t actual_time;
	time(&actual_time);

	double difference = difftime(actual_time, timestamp);

	return difference< EXPIRATION_TIME;
}

/*Function that prints reviews that have been written into the shared review heap memory block.
Used by passively waiting thread.*/
void* print_incoming_reviews(void* arg){
	while(1){
		pthread_mutex_lock(&mutex);
		while(last_written_review_index == written_review_counter){
			pthread_cond_wait(&cond,&mutex);
		}
		int i;
		for(i = last_written_review_index; i < written_review_counter; i++){
			printf("\n\nProduct %s",shared_review[i].product_name);
			printf("\nPID %d", shared_review[i].id);
			printf("\nValue %d\n",shared_review[i].valor);
		}
		last_written_review_index = i;
		pthread_mutex_unlock(&mutex);
	}
	return NULL;
}

/*Function that handles a new socket connection and writes incoming data to a heap memory block.*/
void* handle_connection(void * arg) {
	ConnectionParameters* parameters=(ConnectionParameters*)arg;
	getnameinfo((struct sockaddr * )&parameters->from, parameters->adl, parameters->clientIP, BUF_SIZE,
	parameters->clientPort, BUF_SIZE, NI_NUMERICHOST | NI_NUMERICSERV);
	read(parameters->newSock,&parameters->receivedCode, BUF_SIZE);

	int timestamp_flag = checkTimeStamp(parameters->receivedCode.timestamp);
	if (timestamp_flag) {
		int code = isAuthKeyAllowed((char**)parameters->authenticationKeys, parameters->receivedCode.key, parameters->authenticationKeysTotal) ? VALID_KEY_CODE : INVALID_KEY_CODE;

		write(parameters->newSock,&code, sizeof(code));

		if (code != INVALID_KEY_CODE) {

			Review client_review;
			read(parameters->newSock,&client_review, sizeof(Review));

			//BEGIN MUTEX BLOCK
			pthread_mutex_lock(&mutex);
			
			//check if the number of written reviews matches or exceeds the allocated block size 
			if (written_review_counter >= review_block_size) {
				//int old_size = parameters->review_counter_ptr->size * sizeof(Review);
				printf("\nOld ->>>>>>> %d\n",review_block_size);
				printf("Increment is %d\n",review_block_increment);
				
				review_block_size += review_block_increment;
				
				int new_size = review_block_size * sizeof(Review);
				printf("New ->>>>>>> %d\n", review_block_size);
				
				//use temporary pointer, since memory reallocation might not be possible
				Review* reallocated_review_block=(Review*)realloc(shared_review,new_size);
				printf("->>>>>>>>>> %p\n",reallocated_review_block);
				if(reallocated_review_block==NULL){
					pthread_mutex_unlock(&mutex);
					perror("Heap size exceeded. Unable to store any more reviews.\n");
					return NULL;
				}
				if(reallocated_review_block!=shared_review)shared_review=reallocated_review_block;
				printf("\n \t \t MEMORY BLOCK REALLOCATED\n");
			}	
			
			strcpy(shared_review[written_review_counter].product_name, client_review.product_name);
			shared_review[written_review_counter].valor = client_review.valor;
			shared_review[written_review_counter].id = client_review.id;
			
			written_review_counter++;		//increment the number of total written reviews
			
			pthread_cond_broadcast(&cond);	//signal thread responsible for printing
			
			pthread_mutex_unlock(&mutex);
			//END MUTEX BLOCK
		}
	} else {
		int invalid_value = INVALID_KEY_CODE;
		write(parameters->newSock,&invalid_value, sizeof(invalid_value));
	}
	close(parameters->newSock);
	return NULL;
}



/*Runs the Server*/
/*Receives through parameter the name of the file with the machines authentication keys*/
int main(int argc, char* argv[]) {

	if (argc != 2) {
		printf("The server needs an configuration file with the machines authentication keys\nPlease run the server with the file name passed through parameters\n");
		return 0;
	}
	int authenticationKeysTotal = 0;
	char** authenticationKeys = readAllLines(argv[1],&authenticationKeysTotal);
	struct addrinfo req, * list;
	struct sockaddr_storage from;
	Cominhos receivedCode;
	bzero((char* )&req, sizeof(req)); 	//Clears the structure
	req.ai_family = AF_UNSPEC; 			//Defines the family of the connection
	req.ai_socktype = SOCK_STREAM; 		//Defines the type of socket to use
	req.ai_flags = AI_PASSIVE; 			// local address

	int failure = getaddrinfo(NULL, SERVER_PORT,&req,&list); //Fills struct with necessary info
	if (failure) return 0;

	int sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol); //Creates a socket for listening to new connections
	if (sock == -1) return 0;

	int binded = bind(sock, (struct sockaddr * ) list->ai_addr, list->ai_addrlen);
	if (binded == -1) return 0;

	freeaddrinfo(list);
	listen(sock, SOMAXCONN);

	shared_review=(Review*)malloc(review_block_size*sizeof(Review));	//heap memory block initial allocation

	unsigned int adl = sizeof(from);
	short catched = 0;

	//instantiate mutex and cond
	pthread_mutex_init(&mutex,NULL);
	pthread_cond_init(&cond, NULL);
	
	//instantiate thread responsible for printing review data
	pthread_t thread_id;
	pthread_create(&thread_id, NULL,print_incoming_reviews,NULL);

	while (!catched) {
		int newSock = accept(sock, (struct sockaddr * )&from,&adl);
		ConnectionParameters parameters;
		parameters.receivedCode=receivedCode;
		parameters.authenticationKeysTotal=authenticationKeysTotal;
		parameters.authenticationKeys=(char*)authenticationKeys;
		parameters.adl=adl;
		parameters.from=from;
		parameters.newSock=newSock;
		pthread_t threadID;
		pthread_create(&threadID,NULL,handle_connection,&parameters);
		pthread_detach(threadID);
	}
	return 0;
}
