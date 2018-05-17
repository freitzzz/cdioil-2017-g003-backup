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
#include "AuthGenerator/AuthKeyGenerator.h"
#include "review.h"
#include "sharedmemory.h"
#include "Cominhos.h"

/* Structure that represents a counter */
typedef struct {
  int counter;
  int increment;
  int size;
}
Counter;


typedef struct {
  struct sockaddr_storage from;
  Cominhos receivedCode;
  Counter* review_counter_ptr;
  unsigned int adl;
  int authenticationKeysTotal;
  int newSock;
  char* authenticationKeys;
  char clientIP[BUF_SIZE];
  char clientPort[BUF_SIZE];
}Parameters;


/*Constant that represents the valid key code sent to machines that are not allowed to send reviews*/
#define VALID_KEY_CODE 200
/*Constant that represents the invalid key code sent to machines that are not allowed to send reviews*/
# define INVALID_KEY_CODE 400
/*Constant that represents the expiration time for a device's key*/
# define EXPIRATION_TIME 2

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


/* Unlinks the shared memory zones and the semaphore */
void sighandler() {
  shm_unlink("/sprint4counter");
  shm_unlink("/sprint4reviews");
  sem_unlink("/sprint4semaphore");
  exit(0);
}

void * handle_connection(void * arg) {
  Parameters* parameters=(Parameters*)arg;
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

      //mutex semaphore for incrementing variable
      sem_wait(mutex_semaphore);

      /*This memory mapping instruction is contained within the critical execution area,
      since it might no longer be valid if another process changes the mapping*/
      shared_review = (Review *) mmap(NULL, shm_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd_review, 0);

      if (parameters->review_counter_ptr->counter == parameters->review_counter_ptr->size) {
        int old_size = parameters->review_counter_ptr->size * sizeof(Review);
        parameters->review_counter_ptr->size+=parameters->review_counter_ptr->increment;
        int new_size = parameters->review_counter_ptr->size * sizeof(Review);
        ftruncate(fd_review, new_size);
        shared_review = (Review * ) mremap(shared_review, old_size, new_size, MREMAP_MAYMOVE);
        printf("\n \t \t MEMORY REALLOCATED\n");
      }

      int counter = parameters->review_counter_ptr->counter;

      shared_review += counter;

      strcpy(shared_review->product_name, client_review.product_name);
      shared_review->valor = client_review.valor;
      shared_review->id = client_review.id;

      parameters->review_counter_ptr->counter++;

      sem_post(mutex_semaphore);

      printf("\nProduct %s", shared_review->product_name);
      printf("\nPID %d", shared_review->id);
      printf("\nValue %d", shared_review->valor);
      printf("\nCounter %d", parameters->review_counter_ptr->counter);
      printf("\n %p", shared_review);
    }
  } else {
    int invalid_value = INVALID_KEY_CODE;
    write(parameters->newSock,&invalid_value, sizeof(invalid_value));
  }
  close(parameters->newSock);
  exit(5);
  return NULL;

}


/*Runs the Server*/
/*Recieves through parameter the name of the file with the machines authentication keys*/
int main(int argc, char* argv[]) {

  signal(SIGINT, sighandler);

  if (argc != 2) {
    printf("The server needs an configuration file with the machines authentication keys\nPlease run the server with the file name passed through parameters\n");
    return 0;
  }
  int authenticationKeysTotal = 0;
  char** authenticationKeys = readAllLines(argv[1],&authenticationKeysTotal);
  struct addrinfo req, * list;
  struct sockaddr_storage from;
  char clientIP[BUF_SIZE];
  char clientPort[BUF_SIZE];
  Cominhos receivedCode;
  bzero((char* )&req, sizeof(req)); //Clears the structure
  req.ai_family = AF_UNSPEC; //Defines the family of the connection
  req.ai_socktype = SOCK_STREAM; //Defines the type of socket to use
  req.ai_flags = AI_PASSIVE; // local address

  Counter* review_counter_ptr=(Counter*)malloc(sizeof(Counter));
  review_counter_ptr->counter = 0; //number of currently loaded reviews
  review_counter_ptr->increment = 10; //value defined for icrementing shared memory area
  review_counter_ptr->size = 5; //shared memory's initial size
  Review* shared_review=(Review*)malloc(review_counter_ptr->size*sizeof(Review));

  int failure = getaddrinfo(NULL, SERVER_PORT,&req,&list); //Fills struct with necessary info
  if (failure) return 0;

  int sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol); //Creates a socket for listening to new connections
  if (sock == -1) return 0;

  int binded = bind(sock, (struct sockaddr * ) list->ai_addr, list->ai_addrlen);
  if (binded == -1) return 0;

  freeaddrinfo(list);
  listen(sock, SOMAXCONN);

  unsigned int adl = sizeof(from);
  short catched = 0;

  while (!catched) {
    int newSock = accept(sock, (struct sockaddr * )&from,&adl);
    if (!fork()) {
      close(sock);
      getnameinfo((struct sockaddr * )&from, adl, clientIP, BUF_SIZE,
        clientPort, BUF_SIZE, NI_NUMERICHOST | NI_NUMERICSERV);
      read(newSock,&receivedCode, BUF_SIZE);

      int timestamp_flag = checkTimeStamp(receivedCode.timestamp);
      if (timestamp_flag) {
        int code = isAuthKeyAllowed(authenticationKeys, receivedCode.key, authenticationKeysTotal) ? VALID_KEY_CODE : INVALID_KEY_CODE;

        write(newSock,&code, sizeof(code));

        if (code != INVALID_KEY_CODE) {

          Review client_review;
          read(newSock,&client_review, sizeof(Review));

          //mutex semaphore for incrementing variable
          sem_wait(mutex_semaphore);

          /*This memory mapping instruction is contained within the critical execution area,
          since it might no longer be valid if another process changes the mapping*/
          shared_review = (Review * ) mmap(NULL, shm_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd_review, 0);

          if (review_counter_ptr->counter == review_counter_ptr->size) {
            int old_size = review_counter_ptr->size * sizeof(Review);
            review_counter_ptr->size += review_counter_ptr->increment;
            int new_size = review_counter_ptr->size * sizeof(Review);
            ftruncate(fd_review, new_size);
            shared_review = (Review * ) mremap(shared_review, old_size, new_size, MREMAP_MAYMOVE);
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
        write(newSock,&invalid_value, sizeof(invalid_value));
      }
      close(newSock);
      exit(5);
    }

    close(newSock);
  }

  return 0;
}
