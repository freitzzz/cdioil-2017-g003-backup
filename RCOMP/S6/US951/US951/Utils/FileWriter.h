/*String Header files*/
#include <string.h>
#include <stdio.h>

/*Open Header files*/
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>

/*Read Header files*/
#include <unistd.h>

/*Malloc and Realloc Header files*/
#include <stdlib.h>

/*Constant that defines the initial buffer size of the array that contains all lines of a file*/
#define INITIAL_BUFFER_SIZE 9024
/*Constant that defines the resizing factor of the buffer size of the array that contains all lines of a file*/
#define RESIZE_FACTOR 1.5
/*Constant that defines the identifier of a new line*/
#define NEW_LINE '\n'
/*Writes all lines from a certain content to a file*/
int write_all_lines(char* file_name,char* content);
