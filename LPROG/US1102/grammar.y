/*Includes and declarations*/
%{
    #include <stdio.h>
    #define YYSTYPE char*
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
%token OP_LOGICO //"AND", "OR"

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


%token LIXO //"Qualquer merda"

//%token operador //Operator Token
//%token op_logico_em_esc //Numeric Scale / Multiple Choice logical operator
//%token op_logico_sn //Binary logical operator Token

/*Conditional Tokens*/
//%token condicao_em_esc //Numeric Scale / Multiple Choice Condition Token
//%token condicao_sn //Binary Condition Token

/*Misc Tokens*/
//%token em_esc //Multiple Choice / Numeric Scale Token
//%token opcao //Option Token
//%token DIGITS //Number Token
//%token tipo_questao //Question Type Token
//%token alternativa //Alternative Token

%start START //Start token

%%
START: TIPO_QUESTAO{printf("%s\n","xD");}

TIPO_QUESTAO : 						ESC TEXTO_ESC | EM TEXTO_EM{printf("%s\n","EM");}  | SN TEXTO_SN

TEXTO_ESC: 							TEXTO PARAMETROS_ESC INICIO_BLOCO_CONDICAO| TEXTO PARAMETROS_ESC

TEXTO_EM : 							TEXTO{printf("%s\n","EM1");} PARAMETROS_EM {printf("%s\n","EM2");} ALTERNATIVA  | TEXTO {printf("%s\n","EM3");}PARAMETROS_EM {printf("%s\n","EM4");}INICIO_BLOCO_CONDICAO{printf("%s\n","EM5");} ALTERNATIVA 

ALTERNATIVA:							AL TEXTO ALTERNATIVA  | AL  TIPO_QUESTAO ALTERNATIVA | AL ELSE TX TEXTO ENDEM  | ENDEM | AL ELSE TX TEXTO ENDEM INICIO_BLOCO_CONDICAO {printf("%s\n","ALFINAL");}

TEXTO_SN : 							TEXTO | TEXTO INICIO_BLOCO_CONDICAO_BINARIO

PARAMETROS_ESC : 						MIN OP_MAT NUMERO NUM_TEXTO TEXTO PONTUACAO MAX OP_MAT NUMERO NUM_TEXTO TEXTO

PARAMETROS_EM :						N OP_MAT NUMERO

INICIO_BLOCO_CONDICAO_BINARIO:			IF CONDICAO_BINARIO | IF CONDICAO_BINARIO OR CONDICAO_BINARIO

CONDICAO_BINARIO:						S TIPO_QUESTAO FIM_BLOCO_CONDICAO | N TIPO_QUESTAO FIM_BLOCO_CONDICAO

INICIO_BLOCO_CONDICAO :					IF CONDICAO

CONDICAO : 						OP_MAT NUMERO AND  OP_MAT NUMERO {printf("%s\n","ESTOU");}TIPO_QUESTAO  FIM_BLOCO_CONDICAO 

FIM_BLOCO_CONDICAO : 					ENDIF | ENDIF TIPO_QUESTAO | ELSE  TIPO_QUESTAO ENDIF 

%%

/*Main function*/
int main(){
    yyparse();
    return 0;
}

int yyerror(){
    return 0;
}
