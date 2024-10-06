;Se dau doua numere natural a si b (a: dword, b: word, definite in segmentul de date). Sa se calculeze a/b si sa se afiseze restul impartirii in urmatorul format: "<a> mod <b> = <rest>" Exemplu: pentru a = 23 si b = 5 se va afisa: "23 mod 5 = 3" Valorile vor fi afisate in format decimal (baza 10) cu semn.

bits 32
global start
extern exit,printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data

a dd 23
b dw 5
res resw 1
mesaj db "%d mod %d = %d", 0

segment code use32 class=code

start:
    mov eax,0
    mov edx, 0
    mov ax,[a]
    mov dx,[a+2]
    idiv word [b]
    mov [res],dx
     
    mov ebx, 0
    mov bx,[b]
    
    push dword edx
    push dword ebx
    push dword [a]
    push dword mesaj
    call [printf]
    add esp,4*3
    
push dword 0
call [exit]