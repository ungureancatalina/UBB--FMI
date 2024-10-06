;Se dau doua numere naturale a si b (a, b: word, definite in segmentul de date). Sa se calculeze a/b si sa se afiseze catul si restul impartirii in urmatorul format: "Cat = <cat>, rest = <rest>" Exemplu: pentru a=23 si b=10 se va afisa: "Cat = 2, rest = 3" Valorile vor fi afisate in format decimal (baza 10) cu semn.

bits 32
global start
extern exit,printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data

a dw 150
b dw 13
restul resw 1
cat resw 1
mesaj db "cat= %d, rest= %d", 0

segment code use32 class=code

start:
    mov eax,0
    mov edx,0
    mov ax,[a]
    idiv word [b]
    mov [cat],ax
    mov [restul],dx
    
    push dword edx
    push dword eax
    push dword mesaj
    call [printf]
    add esp,4*3
    
push dword 0
call [exit]