/*Include Header for String*/

#include <string.h>

/*Constant that represents the String terminator value*/
#define TERMINATOR 0
/*Constant that represents the String new line value*/
#define NEW_LINE '\n'
/*Constant that represents the ASCII value for the min digit (0)*/
#define ASCII_MIN_DIGIT '0'
/*Constant that represents the ASCII value for the max digit (9)*/
#define ASCII_MAX_DIGIT '9'


/*Checks if a certain String starts with a certain sequence of characters*/
/*Returns 1 if the String to compare starts with the from String, 0 if not*/
int startsWith(char* stringToCompare,char* from);

/*Gets the value that a certain identifier holds*/
/*Returns Pointed Integer with the value of the identifier, NULL if no value was found*/
int* getIdentifierValue(char *line,char* indentifier);

/*Gets the name that a certain identifier holds*/
/*Returns Pointed Char with the name of the identifier, NULL if no identifier/name was found*/
char* getIdentifierName(char *line,char* identifier);
