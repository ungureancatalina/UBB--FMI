%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int yylex();
void yyerror(const char *s);

char vars_seg[10000] = "";
char code_seg[20000] = "";

char* vars[1024];
int var_count = 0;

int add_var(const char* s) {
    for (int i = 0; i < var_count; i++)
        if (strcmp(vars[i], s) == 0) return i;
    vars[var_count++] = strdup(s);
    return var_count - 1;
}

int find_var(const char* s) {
    for (int i = 0; i < var_count; i++)
        if (strcmp(vars[i], s) == 0) return i;
    return -1;
}
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
    ;

decls
    : decls decl
    | /* empty */
    ;

decl
    : INT ID SEMI
      {
        add_var($2);
        char tmp[128];
        sprintf(tmp, "    %s dd 0\n", $2);
        strcat(vars_seg, tmp);
      }
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

read
    : CITIRE LPAREN ID RPAREN
      {
        if (find_var($3) < 0) yyerror("Variabila nedeclarata");

        char tmp[256];
        sprintf(tmp,
            "    push dword %s\n"
            "    push dword format\n"
            "    call [scanf]\n"
            "    add esp, 8\n", $3);
        strcat(code_seg, tmp);
      }
    ;

write
    : AFISARE LPAREN ID RPAREN
      {
        if (find_var($3) < 0) yyerror("Variabila nedeclarata");

        char tmp[256];
        sprintf(tmp,
            "    push dword [%s]\n"
            "    push dword format\n"
            "    call [printf]\n"
            "    add esp, 8\n", $3);
        strcat(code_seg, tmp);
      }
    ;

assign
    : ID ASSIGN expr
      {
        if (find_var($1) < 0) yyerror("Variabila nedeclarata");

        char tmp[128];
        sprintf(tmp, "    mov [%s], eax\n", $1);
        strcat(code_seg, tmp);
      }
    ;

expr
    : expr PLUS term
      {
        strcat(code_seg,
            "    pop ebx\n"
            "    add eax, ebx\n");
      }
    | expr MINUS term
      {
        strcat(code_seg,
            "    pop ebx\n"
            "    sub eax, ebx\n");
      }
    | term
    ;


term
    : term MUL factor
      {
        strcat(code_seg,
            "    pop ebx\n"
            "    imul eax, ebx\n");
      }
    | factor
    ;

factor
    : NUM
      {
        char tmp[128];
        sprintf(tmp,
            "    mov eax, %d\n"
            "    push eax\n", $1);
        strcat(code_seg, tmp);
      }
    | ID
      {
        if (find_var($1) < 0) yyerror("Variabila nedeclarata");

        char tmp[128];
        sprintf(tmp,
            "    mov eax, [%s]\n"
            "    push eax\n", $1);
        strcat(code_seg, tmp);
      }
    | LPAREN expr RPAREN
    ;

%%

void yyerror(const char *s)
{
    fprintf(stderr, "Eroare: %s\n", s);
    exit(1);
}

int main()
{
    yyparse();

    printf("bits 32\n");
    printf("global start\n\n");

    printf("extern exit\n");
    printf("import exit msvcrt.dll\n");
    printf("extern scanf\n");
    printf("import scanf msvcrt.dll\n");
    printf("extern printf\n");
    printf("import printf msvcrt.dll\n\n");

    printf("segment data use32 class=data\n");
    printf("%s", vars_seg);
    printf("    format db \"%%d\", 0\n");
    printf("    newline db 10, 0\n\n");

    printf("segment code use32 class=code\n");
    printf("start:\n");
    printf("%s", code_seg);

    printf("    push dword 0\n");
    printf("    call [exit]\n");

    return 0;
}
