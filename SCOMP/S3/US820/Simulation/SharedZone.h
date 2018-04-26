#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <sys/stat.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/mman.h>
#include <fcntl.h> 
/*Constant that represents the Shared Memory Name*/
#define SHARED_NAME "/thezone"