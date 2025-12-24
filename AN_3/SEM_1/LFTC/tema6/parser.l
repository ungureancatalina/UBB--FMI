%option noyywrap yylineno

%{
#include "parser.tab.h"
#include <stdlib.h>
#include <string.h>
%}

DIGIT   [0-9]
ID      [a-zA-Z_][a-zA-Z0-9_]*

%%

"start"         { return START; }
"end"           { return END; }
"int"           { return INT; }
"citire"        { return CITIRE; }
"afisare"       { return AFISARE; }

"<-"            { return ASSIGN; }

"+"             { return PLUS; }
"-"             { return MINUS; }
"*"             { return MUL; }

";"             { return SEMI; }
"("             { return LPAREN; }
")"             { return RPAREN; }

{DIGIT}+        { yylval.ival = atoi(yytext); return NUM; }
{ID}            { yylval.sval = strdup(yytext); return ID; }

[ \t\r\n]+      { /* ignor spatii */ }

.               { fprintf(stderr, "Caracter invalid la linia %d: %s\n", yylineno, yytext); }

%%
