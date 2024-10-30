;Se citesc de la tastatura doua numere a si b. Sa se calculeze valoarea expresiei (a/b)*k, k fiind o constanta definita in segmentul de date. Afisati valoarea expresiei (in baza 2).

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resd 1
b resd 1
k dd 2
rez resq 1
mesaj1 db "a= ", 0
mesaj2 db "b= ", 0
format1 db "%d", 0
format2 db "rez= %d", 0

segment code use32 class=code

start:
        
    push dword mesaj1
    call [printf]
    add esp,4*1   
        
    push dword a
    push dword format1
    call [scanf]
    add esp,4*2
    
    push dword mesaj2
    call [printf]
    add esp,4*1   
        
    push dword b
    push dword format1
    call [scanf]
    add esp,4*2
    
    mov eax,[a]
    mov edx,0
    idiv dword [b]
    imul dword [k]
    
    mov [rez],eax
    mov [rez+4],edx    
        
    push dword [rez+4]    
    push dword [rez]
    push dword format2
    call [printf]
    add esp,4*3
    
push dword 0
call [exit]