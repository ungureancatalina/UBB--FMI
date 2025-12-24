%{
#include <stdio.h>
#include <string.h>
#include "formal.tab.h"

int total_cuvinte = 0;
int total_formal = 0;
%}

%option noyywrap

DELIM       [ \t\n]+
WORD        [a-z]+
INVALID     [A-ZăâîșțĂÂÎȘȚ-]

%%

{DELIM}      { /* ignora spatii si newline-uri */ }

{WORD}       {
                total_cuvinte++;

                if ( strcmp(yytext, "formal") == 0 ||
                     strcmp(yytext, "formala") == 0 ||
                     strcmp(yytext, "formali") == 0 ||
                     strcmp(yytext, "formale") == 0 ||
                     strcmp(yytext, "formalul") == 0 ||
                     strcmp(yytext, "formalei") == 0 ||
                     strcmp(yytext, "formalii") == 0 ||
                     strcmp(yytext, "formalelor") == 0 ||
                     strcmp(yytext, "formalului") == 0 )
                {
                    total_formal++;
                }

                yylval.strval = strdup(yytext);
                return WORD_TOKEN;
             }

{INVALID}    {
                printf("Eroare: caracter nepermis '%s' în text.\n", yytext);
             }

.            {
                printf("Eroare: caracter necunoscut '%s'.\n", yytext);
             }

%%
