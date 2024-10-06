;Se da un numar natural a (a: dword, definit in segmentul de date). Sa se citeasca un numar natural b si sa se calculeze: a + a/b. Sa se afiseze rezultatul operatiei. Valorile vor fi afisate in format decimal (baza 10) cu semn.

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a dd 23
b resd 1
rez resq 1
mesaj db "b= ", 0
format db "%d", 0
mesaj2 db "rez= %d", 0

segment code use32 class=code

start:

    push dword mesaj
    call [printf]
    add esp,4*1
    
    push dword b
    push dword format
    call [scanf]
    add esp,4*2
    
    mov ebx,0
    mov eax,[a]
    mov edx, 0
    idiv dword [b]
    add eax, [a]
    adc ebx, 0
    
    mov [rez],eax
    mov [rez+4],ebx
    
    push dword [rez+4]
    push dword [rez]
    push dword mesaj2
    call [printf]
    add esp, 4*3
    
push dword 0
call [exit]