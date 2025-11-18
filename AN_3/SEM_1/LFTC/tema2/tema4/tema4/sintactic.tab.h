/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2021 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

#ifndef YY_YY_SINTACTIC_TAB_H_INCLUDED
# define YY_YY_SINTACTIC_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token kinds.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    YYEMPTY = -2,
    YYEOF = 0,                     /* "end of file"  */
    YYerror = 256,                 /* error  */
    YYUNDEF = 257,                 /* "invalid token"  */
    ID = 258,                      /* ID  */
    CONST_NUM = 259,               /* CONST_NUM  */
    CONST_SIR = 260,               /* CONST_SIR  */
    INT = 261,                     /* INT  */
    DOUBLE = 262,                  /* DOUBLE  */
    SIRURI = 263,                  /* SIRURI  */
    DACA = 264,                    /* DACA  */
    ALT_DACA = 265,                /* ALT_DACA  */
    ALT = 266,                     /* ALT  */
    ATATA_TIMP = 267,              /* ATATA_TIMP  */
    CAT = 268,                     /* CAT  */
    CONTINUA_PENTRU = 269,         /* CONTINUA_PENTRU  */
    CITIRE = 270,                  /* CITIRE  */
    AFISARE = 271,                 /* AFISARE  */
    START = 272,                   /* START  */
    END = 273,                     /* END  */
    PAR_L = 274,                   /* PAR_L  */
    PAR_R = 275,                   /* PAR_R  */
    ACOLADA_L = 276,               /* ACOLADA_L  */
    ACOLADA_R = 277,               /* ACOLADA_R  */
    PUNCT_SI_VIRGULA = 278,        /* PUNCT_SI_VIRGULA  */
    VIRGULA = 279,                 /* VIRGULA  */
    EGAL = 280,                    /* EGAL  */
    PLUS = 281,                    /* PLUS  */
    MINUS = 282,                   /* MINUS  */
    ORI = 283,                     /* ORI  */
    IMPARTIRE = 284,               /* IMPARTIRE  */
    MOD = 285,                     /* MOD  */
    EGAL_EGAL = 286,               /* EGAL_EGAL  */
    DIFERIT = 287,                 /* DIFERIT  */
    MAI_MIC = 288,                 /* MAI_MIC  */
    MAI_MARE = 289,                /* MAI_MARE  */
    MAI_MIC_EGAL = 290,            /* MAI_MIC_EGAL  */
    MAI_MARE_EGAL = 291            /* MAI_MARE_EGAL  */
  };
  typedef enum yytokentype yytoken_kind_t;
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;


int yyparse (void);


#endif /* !YY_YY_SINTACTIC_TAB_H_INCLUDED  */
