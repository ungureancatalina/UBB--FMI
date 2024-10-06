;Se dau doua numere naturale a si b (a, b: word, definite in segmentul de date). Sa se calculeze produsul lor si sa se afiseze in urmatorul format: "<a> * <b> = <result>" Exemplu: "2 * 4 = 8" Valorile vor fi afisate in format decimal (baza 10) cu semn.

bits 32
global start
extern exit,printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data

a dw 10
b dw 13
rezultat resd 1
mesaj db "%d * %d = %d", 0

segment code use32 class=code

start:

    mov ax,[a]
    imul word [b]
    mov [rezultat],ax
    mov [rezultat+2],dx
    
    mov eax,0
    mov ax,[a]
    mov ebx,0
    mov bx,[b]
    
    push dword [rezultat]
    push dword ebx
    push dword eax
    push dword mesaj
    call [printf]
    add esp,4*4
    
push dword 0
call [exit]