%{
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
extern int total_cuvinte;
extern int total_formal;

void yyerror(const char* s) { }
%}

%union {
    char* strval;
}

%token <strval> WORD_TOKEN

%%

program:
    lista
    {
        printf("Numar total de cuvinte: %d\n", total_cuvinte);
        printf("Numar cuvinte din declinarea adjectivului 'formal': %d\n", total_formal);
    }
;

lista:
      /* empty */
    | lista WORD_TOKEN
;


%%

int main() {
    yyparse();
    return 0;
}
