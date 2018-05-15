compileAll: bison flex
	gcc grammar.tab.c lex.yy.c -lfl

flex:
	flex survey.flex

bison:
	bison -d grammar.y

run: compileAll