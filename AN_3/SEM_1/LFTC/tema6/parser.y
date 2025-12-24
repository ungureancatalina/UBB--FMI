%{
#include <stdio.h>
#include <stdlib.h>

int yylex();
void yyerror(const char *s);
%}

%union {
    int ival;
    char* sval;
}

%token START END INT CITIRE AFISARE
%token ASSIGN PLUS MINUS MUL
%token SEMI LPAREN RPAREN
%token <ival> NUM
%token <sval> ID

%start program

%%

program
    : START decls stmts END
      {
        printf("Program ACCEPTAT sintactic.\n");
      }
    ;

decls
    : decls decl
    | /* empty */
    ;

decl
    : INT ID SEMI
    ;

stmts
    : stmts stmt
    | /* empty */
    ;

stmt
    : assign SEMI
    | read SEMI
    | write SEMI
    ;

assign
    : ID ASSIGN expr
    ;

read
    : CITIRE LPAREN ID RPAREN
    ;

write
    : AFISARE LPAREN expr RPAREN
    ;

expr
    : expr PLUS term
    | expr MINUS term
    | term
    ;

term
    : term MUL factor
    | factor
    ;

factor
    : NUM
    | ID
    | LPAREN expr RPAREN
    ;

%%

void yyerror(const char *s)
{
    fprintf(stderr, "Eroare sintactica: %s\n", s);
}

int main()
{
    return yyparse();
}
