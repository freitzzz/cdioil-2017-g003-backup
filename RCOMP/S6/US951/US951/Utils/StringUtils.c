#include "StringUtils.h"
#include <stdio.h>
#include <stdlib.h>

/*Checks if a certain String starts with a certain sequence of characters*/
/*Returns 1 if the String to compare starts with the from String, 0 if not*/
int startsWith(char* stringToCompare,char* from){
    int i=0;
    int fromLength=strlen(stringToCompare);
    for(i=0;i<fromLength;i++){
        char nextByte=from[i];
        char compareByte=stringToCompare[i];
        if(nextByte!=compareByte)return 0;
        if(compareByte==TERMINATOR)return 0;
    }
    return 1;
}

/*Gets the value that a certain identifier holds*/
/*Returns Pointed Integer with the value of the identifier, NULL if no value was found*/
int* getIdentifierValue(char *line,char* indentifier){
    int i=strlen(indentifier)-1;
    int catched=0;
    int value=0;
    int* pointedValue=&value;
    char nextByte=0;
    while((nextByte=line[i++])!=TERMINATOR){
        if(nextByte>=ASCII_MIN_DIGIT&&nextByte<=ASCII_MAX_DIGIT){
            value=(value*10)+(nextByte-ASCII_MIN_DIGIT);
            ++catched;
        }else{
            if(catched>0)return pointedValue;
        }
    }
    return catched!=0 ? pointedValue : NULL;
}
/*Gets the name that a certain identifier holds*/
/*Returns Pointed Char with the name of the identifier, NULL if no identifier/name was found*/
char* getIdentifierName(char *line,char* identifier){
  int lineLength=strlen(line)-1;
  int identifierLength=strlen(identifier);
  int identifierSize=lineLength-identifierLength;
  if(identifierSize<=0)return NULL;
  int i;
  char* identifierName=malloc(identifierSize*sizeof(char));
  int currentNameIndex=0;
  for(i=identifierLength;i<lineLength;i++){
    identifierName[currentNameIndex++]=line[i];
  }
  identifierName[identifierSize]=0;
  printf("wow ->>>>>>>>> %s\n",identifierName);
  return identifierName;
}
