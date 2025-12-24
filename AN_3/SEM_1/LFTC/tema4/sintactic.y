%{
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
extern int yylineno;

void yyerror(const char *s);
%}

%start program

%token ID CONST_NUM CONST_SIR
%token INT DOUBLE SIRURI
%token DACA ALT_DACA ALT
%token ATATA_TIMP CAT CONTINUA_PENTRU
%token CITIRE AFISARE
%token START END

%token PAR_L PAR_R
%token ACOLADA_L ACOLADA_R
%token PUNCT_SI_VIRGULA VIRGULA
%token EGAL PLUS MINUS ORI IMPARTIRE MOD
%token EGAL_EGAL DIFERIT MAI_MIC MAI_MARE MAI_MIC_EGAL MAI_MARE_EGAL

%left PLUS MINUS
%left ORI IMPARTIRE MOD

%%

program:
    START instructiuni END
;

instructiuni:
      instructiuni instructiune
    | instructiune
;

instructiune:
      declaratie PUNCT_SI_VIRGULA
    | atribuire PUNCT_SI_VIRGULA
    | apel_functie PUNCT_SI_VIRGULA
;

declaratie:
      INT ID
    | DOUBLE ID
    | SIRURI ID
    | INT ID EGAL expresie
    | DOUBLE ID EGAL expresie
    | SIRURI ID EGAL expresie
;

atribuire:
    ID EGAL expresie
;

apel_functie:
      AFISARE PAR_L expresie PAR_R
    | CITIRE   PAR_L ID       PAR_R
;

expresie:
      ID
    | CONST_NUM
    | expresie PLUS expresie
    | expresie MINUS expresie
    | expresie ORI expresie
    | expresie IMPARTIRE expresie
    | expresie MOD expresie
;

%%

void yyerror(const char *s)
{
    printf("Eroare sintactica la linia %d: %s\n", yylineno, s);
}

int main()
{
    yyparse();
    printf("Analiza sintactica finalizata cu succes.\n");
    return 0;
}
