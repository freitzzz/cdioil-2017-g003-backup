%{
    #include "grammar.tab.h"    
%}

%%
[ ]*[eE][mM][ ]*  {strdup(yytext);return EM;}
[ ]*[eE][sS][cC][ ]* {strdup(yytext);return ESC;}
[ ]*[sS][nN][ ]* {strdup(yytext);return SN;}
[ ]*[tT][xX][ ]* {strdup(yytext);return TX;} 
[ ]*\"[a-zA-Z ?]*\"[ ]*  {strdup(yytext);return TEXTO;}
[ ]*[eE][nN][dD][eE][mM][ ]*  {strdup(yytext);return ENDEM;}

[ ]*[aA][nN][dD][ ]*  {strdup(yytext);return AND;}
[ ]*[oO][rR][ ]*  {strdup(yytext);return OR;}
[ ]*(([aA][nN][dD])|([oO][rR]))[ ]*  {strdup(yytext);return OP_LOGICO;}
   
[ ]*[sS][ ]*  {strdup(yytext);return S;}
[ ]*[nN][ ]*  {strdup(yytext);return N;}

[ ]*[iI][fF][ ]*  {strdup(yytext);return IF;}
[ ]*[eE][nN][dD][iI][fF][ ]*  {strdup(yytext);return ENDIF;}
[ ]*[eE][lL][sS][eE][ ]*  {strdup(yytext);return ELSE;}
[ ]*[aA][lL][ ]*  {strdup(yytext);return AL;}
[ ]*[aA][lL][eE][lL][sS][eE][ ]*  {strdup(yytext);return ALELSE;}
[ ]*([<]|[>]|[<][=]|[>][=]|[=]|[!=])[ ]*  {strdup(yytext);return OP_MAT;}

[ ]*[0-9]+[ ]*  {strdup(yytext);return NUMERO;}
[ ]*[mM][iI][nN][ ]*  {strdup(yytext);return MIN;}
[ ]*[mM][aA][xX][ ]*  {strdup(yytext);return MAX;}
[ ]*[:][ ]*  {strdup(yytext);return NUM_TEXTO;}
[ ]*[;|,][ ]*  {strdup(yytext);return PONTUACAO;}
[ ]*[;]+[ ]*  {strdup(yytext);return LIXO;}
[ \t\n\r]*;
%%