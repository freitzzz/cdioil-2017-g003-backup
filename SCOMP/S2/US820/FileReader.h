/*String Header files*/
#include <string.h>

/*Open Header files*/
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>

/*Read Header files*/
#include <unistd.h>

/*Malloc and Realloc Header files*/
#include <stdlib.h>

/*Constant that defines the initial buffer size of the array that contains all lines of a file*/
#define INITIAL_BUFFER_SIZE 1024
/*Constant that defines the resizing factor of the buffer size of the array that contains all lines of a file*/
#define RESIZE_FACTOR 1.5
/*Constant that defines the identifier of a new line*/
#define NEW_LINE '\n'


/*Reads all lines contained on a certain file*/
char** readAllLines(char *fileName,int* linesRead);