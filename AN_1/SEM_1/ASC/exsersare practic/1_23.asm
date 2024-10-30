;Sa se citeasca de la tastatura un numar hexazecimal format din 2 cifre. Sa se afiseze pe ecran acest numar in baza 10, interpretat atat ca numar fara semn cat si ca numar cu semn (pe 8 biti).

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resd 1
mesaj1 db "a= ", 0
format1 db "%x", 0
format2 db "%d", 13,10,0
format3 db "%u", 0

segment code use32 class=code

start:
        
    push dword mesaj1
    call [printf]
    add esp,4*1   
        
    push dword a
    push dword format1
    call [scanf]
    add esp,4*2
    
    push dword [a]
    push dword format2
    call [printf]
    add esp,4*2
    
    push dword [a]
    push dword format3
    call [printf]
    add esp,4*2
    
push dword 0
call [exit]