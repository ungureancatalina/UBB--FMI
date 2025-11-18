%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

extern int yylineno;

#include "sintactic.tab.h"   /* tokenii generați de BISON */
%}

%option noyywrap
%option nounput
%option noinput

ID          [0-9][A-Za-z0-9_]*
CONST_NUM   [+-]?[0-9]+(\.[0-9]+)?
CONST_SIR   \"([^\\\"]|\\.)*\"

%%

"start"                { return START; }
"end"                  { return END; }
"int"                  { return INT; }
"double"               { return DOUBLE; }
"siruri"               { return SIRURI; }

"daca"                 { return DACA; }
"altfel_daca"          { return ALT_DACA; }
"altfel"               { return ALT; }
"atata_timp_cat"       { return ATATA_TIMP; }
"continua_pentru"      { return CONTINUA_PENTRU; }

"citire"               { return CITIRE; }
"afisare"              { return AFISARE; }

"("                    { return PAR_L; }
")"                    { return PAR_R; }
"{"                    { return ACOLADA_L; }
"}"                    { return ACOLADA_R; }
";"                    { return PUNCT_SI_VIRGULA; }
","                    { return VIRGULA; }
"="                    { return EGAL; }
"+"                    { return PLUS; }
"-"                    { return MINUS; }
"*"                    { return ORI; }
"/"                    { return IMPARTIRE; }
"%"                    { return MOD; }

"=="                   { return EGAL_EGAL; }
"!="                   { return DIFERIT; }
"<"                    { return MAI_MIC; }
">"                    { return MAI_MARE; }
"<="                   { return MAI_MIC_EGAL; }
">="                   { return MAI_MARE_EGAL; }

{ID}                   { yylval = 0; return ID; }
{CONST_NUM}            { yylval = 0; return CONST_NUM; }
{CONST_SIR}            { yylval = 0; return CONST_SIR; }

[ \t]+                 { }
"//".*                 { }
\n                     { yylineno++; }
.                      { printf("Eroare lexica la linia %d: %s\n", yylineno, yytext); }

%%

