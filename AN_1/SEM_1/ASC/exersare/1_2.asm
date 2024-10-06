;Sa se citeasca de la tastatura doua numere a si b (in baza 10) si sa se calculeze a/b. Catul impartirii se va salva in memorie in variabila "rezultat" (definita in segmentul de date). Valorile se considera cu semn.

bits 32
global start
extern exit,scanf,printf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resd 1
b resd 1
rezultat resd 1
mesaj1 db "a=", 0
mesaj2 db "b=", 0
mesaj3 db "rezultat= %d", 0
format db "%d", 0

segment code use32 class=code

start:
    push dword mesaj1
    call [printf]
    add esp,4*1

    push dword a
    push dword format
    call [scanf]
    add esp,4*2
    
    push dword mesaj2
    call [printf]
    add esp,4*1

    push dword b
    push dword format
    call [scanf]
    add esp,4*2
    
    mov eax,[a]
    div dword [b]
    mov dword [rezultat], 0
    add [rezultat],ax
    
    push dword [rezultat]
    push dword mesaj3
    call [printf]
    add esp,4*2

push dword 0
call [exit]