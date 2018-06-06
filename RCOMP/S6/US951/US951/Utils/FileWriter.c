#include "FileWriter.h"
#include <sys/wait.h>

/*Writes all lines from a certain content to a file*/
int write_all_lines(char* file_name,char* content){
  //printf("->>>>>>> %s\n",content);
  int file_descriptor=open(file_name,O_CREAT|O_RDWR,S_IRUSR);
  if(file_descriptor<0)return 0;
  write(file_descriptor,content,strlen(content));
  close(file_descriptor);
  return 1;
}
