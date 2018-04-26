/*Include Header File for Semaphores*/
#include <semaphore.h>

/*Constant that represents the semaphore name that alows the child to send a review*/
#define PARENT_SEMAPHORE_NAME "/looksonalegend"
/*Constant that represents the semaphore name that alows the parent to receive*/
#define CHILD_SEMAPHORE_NAME "/lookdadisawthelegend"
/*Constant that represents the semaphore name that alows the the child to print the operation success*/
#define PRINT_SEMAPHORE_NAME "/icanfinallyprint"

/*Constant that represents the initial value of the Parent Semaphore*/
#define SEMAPHORE_PARENT_INTIAL_VALUE 0
/*Constant that represents the initial value of the Child Semaphore*/
#define SEMAPHORE_CHILD_INTIAL_VALUE 0
/*Constant that represents the initial value of the Print Semaphore*/
#define SEMAPHORE_PRINT_INTIAL_VALUE 0