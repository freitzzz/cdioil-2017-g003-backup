#include "StringUtils.h"

/*Checks if a certain String starts with a certain sequence of characters*/
/*Returns 1 if the String to compare starts with the from String, 0 if not*/
int startsWith(char* stringToCompare,char* from){
    int i=0;
    int fromLength=strlen(from);
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
