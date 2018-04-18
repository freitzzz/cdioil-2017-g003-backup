#include "FileReader.h"
#include <stdio.h>


/*Reads all lines contained on a certain file*/
char** readAllLines(char *fileName,int* linesRead){
    int fileDescription=open(fileName,O_RDONLY,S_IRUSR);
    if(fileDescription<0)return NULL;
    int currentBufferUsage=0;
    int maxBufferSize=INITIAL_BUFFER_SIZE;
    char *fileContent=malloc(INITIAL_BUFFER_SIZE);
    char *readBytes=malloc(INITIAL_BUFFER_SIZE);
    int nextBytes=0;
    while((nextBytes=read(fileDescription,readBytes,INITIAL_BUFFER_SIZE))){
        if(nextBytes<=0){
            //Dont do anything
        }else{
            currentBufferUsage+=nextBytes;
            if(currentBufferUsage>maxBufferSize){
                maxBufferSize*=RESIZE_FACTOR;
                fileContent=realloc(fileContent,maxBufferSize);
            }
            readBytes[nextBytes]=0;
            strcat(fileContent,readBytes);
        }
    }
    char *asd[]={"LOL"};
    char **allLines;
    allLines=(char**)asd;
    int i;
    int nextLine=0;
    int lineLength=0;
    for(i=0;i<currentBufferUsage;i++){
        lineLength++;
        if((int)fileContent[i]==(int)NEW_LINE){
            allLines[nextLine++]=realloc(allLines[nextLine],lineLength);
            lineLength=0;
        }
    }
    nextLine=0;
    lineLength=0;
    for(i=0;i<currentBufferUsage;i++){
        allLines[nextLine][lineLength++]=fileContent[i];
        if((int)fileContent[i]==(int)NEW_LINE){
            ++nextLine;
            lineLength=0;
        }
    }
    free(fileContent);
    //*linesRead=nextLine+1;
    return allLines;
}