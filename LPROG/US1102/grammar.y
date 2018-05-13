/*Includes and declarations*/
%{
    #include <stdio.h>
    #include <stdlib.h>
    #include "QuestionTypes.h"
    #define BINARY "SN"
    #define SCALE "ESC"
    #define MULTIPLE "EM"
    #define YYSTYPE char*
    int currentLine=1;
    char* currentNumber;
    int currentMultipleChoiceStackSize=10;
    int currentMultipleChoiceStackIndex=0;
    int* multipleChoiceStack;
    int currentScaleMinValue=0;
    int currentScaleMaxValue=0;
    int leftScaleChoice=0;
    int rightScaleChoice=0;
    int maxAlternatives=0;
    int currentAlternative=0;
%}

/*Grammar tokens*/

/*Question Types Tokens*/
%token EM //Multiple Choice Token
%token ESC //Numeric Scale Token
%token SN //Binary Token
%token TX
%token TEXTO //Text Token
%token ENDEM //Multiple Choice Terminal Token

/*Logical Operators Tokens*/
%token AND //And Token
%token OR //Or Token

/*Question Options Tokens*/
%token S //Binary True Token
%token N //Binary False Token

/*Conditional Tokens*/
%token IF //"IF" Token
%token ENDIF //"ENDIF" Token
%token ELSE //"ELSE" Token
%token AL //"AL" Token
%token ALELSE //"ALESE" Token


/*Operator Tokens*/
%token OP_MAT //Mathematical Operator Token
%token NUM_TEXTO //":"
%token PONTUACAO
%token NUMERO //Digits Token
%token MIN //"Min"
%token MAX //"Max"


%start START //Start token

%%
START: TIPO_QUESTAO{printf("%s\n","Inquérito Válido");}|

TIPO_QUESTAO : 						ESC TEXTO_ESC
                                    | EM TEXTO_EM
                                    | SN TEXTO_SN

TEXTO_ESC: 							TEXTO PARAMETROS_ESC INICIO_BLOCO_CONDICAO
                                    | TEXTO PARAMETROS_ESC

TEXTO_EM : 							TEXTO PARAMETROS_EM ALTERNATIVA

ALTERNATIVA:							AL{validateMultipleChoiceAlternative();} TEXTO ALTERNATIVA
                                        | AL{validateMultipleChoiceAlternative();} TIPO_QUESTAO ALTERNATIVA
                                        | ALELSE{validateMultipleChoiceAlternative();} TX TEXTO ENDEM{currentAlternative=0;removeFromMultipleChoiceStack();} INICIO_BLOCO_CONDICAO
                                        | ALELSE{validateMultipleChoiceAlternative();} TX TEXTO ENDEM{currentAlternative=0;removeFromMultipleChoiceStack();}
                                        | ENDEM{currentAlternative=0;removeFromMultipleChoiceStack();}

TEXTO_SN : 							 TEXTO
                                        | TEXTO INICIO_BLOCO_CONDICAO_BINARIO

PARAMETROS_ESC : 						MIN OP_MAT NUMERO{currentScaleMinValue=atoi(currentNumber);} NUM_TEXTO TEXTO PONTUACAO MAX OP_MAT NUMERO{currentScaleMaxValue=atoi(currentNumber);} NUM_TEXTO TEXTO{validateScaleQuestion();}

PARAMETROS_EM :						N OP_MAT NUMERO{maxAlternatives=atoi(currentNumber);validateMultipleChoiceQuestion();addToMultipleChoiceStack(maxAlternatives);}

INICIO_BLOCO_CONDICAO_BINARIO:			IF CONDICAO_BINARIO
                                        | IF CONDICAO_BINARIO OR CONDICAO_BINARIO

CONDICAO_BINARIO:						S TIPO_QUESTAO FIM_BLOCO_CONDICAO
                                            | N TIPO_QUESTAO FIM_BLOCO_CONDICAO

INICIO_BLOCO_CONDICAO :					IF CONDICAO

CONDICAO : 						OP_MAT NUMERO{leftScaleChoice=atoi(currentNumber);} LOGICO OP_MAT NUMERO{rightScaleChoice=atoi(currentNumber);validateScaleChoice();} TIPO_QUESTAO  FIM_BLOCO_CONDICAO
                                    | OP_MAT NUMERO{leftScaleChoice=atoi(currentNumber);validateScaleChoice();} TIPO_QUESTAO FIM_BLOCO_CONDICAO

FIM_BLOCO_CONDICAO : 					ENDIF
                                            | ENDIF TIPO_QUESTAO
                                                | ELSE  TIPO_QUESTAO ENDIF

LOGICO: AND
            | OR;

%%

/*Main function*/
int main(){
  multipleChoiceStack=malloc(currentMultipleChoiceStackSize*sizeof(int));
    yyparse();
    return 0;
}

int yyerror(){
    printf("%s\n","Inquérito Inválido");
    return 0;
}
void validateScaleQuestion(){
    if(currentScaleMinValue<0 || currentScaleMaxValue<0 || currentScaleMinValue>=currentScaleMaxValue){
        printf("Parametros inválidos respetivamente a uma questão de escolha multipla!\nLinha %d\n",currentLine);
        exit(5);
    }
}
void validateScaleChoice(){
    if(leftScaleChoice<0 || rightScaleChoice<0 || leftScaleChoice<currentScaleMinValue
        || leftScaleChoice>currentScaleMaxValue || rightScaleChoice<currentScaleMinValue
        || rightScaleChoice>currentScaleMaxValue){
            printf("Parametros inválidos relativamente à decisao da escolha multipla!\nLinha %d\n",currentLine);
            exit(5);
        }
}
void validateMultipleChoiceQuestion(){
    if(maxAlternatives<=0){
        printf("Parametros inválidos relativamente ao numero de alternativas na escolha multipla!\nLinha %d\n",currentLine);
        exit(5);
    }
}
void validateMultipleChoiceAlternative(){
    currentAlternative++;
    (*multipleChoiceStack)--;
    if(currentAlternative>maxAlternatives){
        printf("O número de alternativas excedeu o limite!\nLinha %d\n",currentLine);
        exit(5);
    }
}
void addToMultipleChoiceStack(int currentNumber){
  if(currentMultipleChoiceStackIndex==currentMultipleChoiceStackSize){
    currentMultipleChoiceStackSize+=10;
    multipleChoiceStack=realloc(multipleChoiceStack,currentMultipleChoiceStackSize*sizeof(int));
  }
  multipleChoiceStack++;
  currentMultipleChoiceStackIndex++;
  *(multipleChoiceStack)=currentNumber;
}
void removeFromMultipleChoiceStack(){
  if(*multipleChoiceStack==0){
    currentMultipleChoiceStackIndex--;
    multipleChoiceStack--;
  }else{
    printf("Inquérito inválido! A escolha multipla não acabou as suas alternativas! \nLinha %d\n",currentLine);
    exit(5);
  }
}
