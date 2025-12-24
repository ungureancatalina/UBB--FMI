%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int lineNumber = 1;

FILE *fip;
FILE *ts;
int tsIndex = 1;

typedef struct {
    char lexem[256];
    int index;
} TS_Entry;

TS_Entry tabela[1024];
int tsCount = 0;

int adaugaTS(char *lexem) {
    for (int i = 0; i < tsCount; i++) {
        if (strcmp(tabela[i].lexem, lexem) == 0)
            return tabela[i].index;
    }
    strcpy(tabela[tsCount].lexem, lexem);
    tabela[tsCount].index = tsIndex++;
    tsCount++;
    return tabela[tsCount - 1].index;
}

%}

%option noyywrap

ALFANUM [A-Za-z0-9_]
ID [0-9]{1}[A-Za-z0-9_]{0,199}
CONST_NUM [+-]?[0-9]+(\.[0-9]+)?
CONST_SIR \"([^\\\"]|\\.)*\"

%%

"start"                { fprintf(fip, "31\t0\n"); }
"end"                  { fprintf(fip, "32\t0\n"); }
"int"                  { fprintf(fip, "3\t0\n"); }
"double"               { fprintf(fip, "4\t0\n"); }
"siruri"               { fprintf(fip, "5\t0\n"); }
"daca"                 { fprintf(fip, "6\t0\n"); }
"altfel_daca"          { fprintf(fip, "7\t0\n"); }
"altfel"               { fprintf(fip, "8\t0\n"); }
"atata_timp_cat"       { fprintf(fip, "9\t0\n"); }
"continua_pentru"      { fprintf(fip, "10\t0\n"); }
"citire"               { fprintf(fip, "11\t0\n"); }
"afisare"              { fprintf(fip, "12\t0\n"); }

"("                    { fprintf(fip, "13\t0\n"); }
")"                    { fprintf(fip, "14\t0\n"); }
"{"                    { fprintf(fip, "15\t0\n"); }
"}"                    { fprintf(fip, "16\t0\n"); }
";"                    { fprintf(fip, "17\t0\n"); }
","                    { fprintf(fip, "18\t0\n"); }
"="                    { fprintf(fip, "19\t0\n"); }
"+"                    { fprintf(fip, "20\t0\n"); }
"-"                    { fprintf(fip, "21\t0\n"); }
"*"                    { fprintf(fip, "22\t0\n"); }
"/"                    { fprintf(fip, "23\t0\n"); }
"%"                    { fprintf(fip, "24\t0\n"); }
"=="                   { fprintf(fip, "25\t0\n"); }
"!="                   { fprintf(fip, "26\t0\n"); }
"<"                    { fprintf(fip, "27\t0\n"); }
">"                    { fprintf(fip, "28\t0\n"); }
"<="                   { fprintf(fip, "29\t0\n"); }
">="                   { fprintf(fip, "30\t0\n"); }

"repeta"               { fprintf(fip, "33\t0\n"); }
"panacand"             { fprintf(fip, "34\t0\n"); }
"sfrepeta"             { fprintf(fip, "35\t0\n"); }


{ID}                   {
                           int idx = adaugaTS(yytext);
                           fprintf(fip, "0\t%d\n", idx);
                       }
{CONST_NUM}            {
                           int idx = adaugaTS(yytext);
                           fprintf(fip, "1\t%d\n", idx);
                       }
{CONST_SIR}            {
                           int idx = adaugaTS(yytext);
                           fprintf(fip, "2\t%d\n", idx);
                       }

[ \t]+                 { /* ignoră spațiile */ }
"//".*                 { /* comentariu, ignoră */ }
\n                     { lineNumber++; }
.                      { printf("Eroare lexica la linia %d: necunoscut '%s'\n", lineNumber, yytext); }

%%

int main(int argc, char **argv)
{
    yyin = fopen("program.txt", "r");
    if (!yyin) {
        printf("Eroare: nu s-a gasit fisierul 'program1.txt'!\n");
        return 1;
    }

    fip = fopen("fip.txt", "w");
    ts = fopen("ts.txt", "w");

    fprintf(ts, "Idx\tLexem\n");
    yylex();

    for (int i = 0; i < tsCount; i++)
        fprintf(ts, "%d\t%s\n", tabela[i].index, tabela[i].lexem);

    fclose(fip);
    fclose(ts);
    fclose(yyin);

    printf("Analiza lexica finalizata cu succes.\n");
    printf("TS: %d simboluri\n", tsCount);
    return 0;
}
