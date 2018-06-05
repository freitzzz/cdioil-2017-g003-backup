#include "FileWriter.h"
#include <sys/wait.h>

/*Writes all lines from a certain content to a file*/
int write_all_lines(char* file_name,char* content){
  int file_descriptor=open(file_name,O_CREAT|O_RDWR,S_IRUSR);
  if(file_descriptor<0)return 0;
  if(!fork()){
    dup2(file_descriptor,1);
    printf("%s\n",content);
    exit(5);
  }else{
    wait(NULL);
  }
  return 1;
}
