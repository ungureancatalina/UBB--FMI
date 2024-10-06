;Sa se citeasca de la tastatura un numar in baza 16 si sa se afiseze valoarea acelui numar in baza 10. Exemplu: Se citeste: 1D; se afiseaza: 29

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resd 1
format db "%x", 0
mesaj db "a= ", 0
format2 db "a= %d", 0

segment code use32 class=code

start:

    push dword mesaj
    call [printf]
    add esp,4*1
    
    push dword a
    push dword format
    call [scanf]
    add esp,4*2
        
    push dword [a]
    push dword format2
    call [printf]
    add esp,4*2
    
push dword 0
call [exit]