;Se citesc de la tastatura trei numere a, m si n (a: word, 0 <= m, n <= 15, m > n). Sa se izoleze bitii de la m-n ai lui a si sa se afiseze numarul intreg reprezentat de acesti bitii in baza 10.

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resw 1
m resd 1
n resd 1
mesaj1 db "a= ", 0
mesaj2 db "m= ", 0
mesaj3 db "n= ", 0
format1 db "%d", 0
format2 db "nr final= %d", 0

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
        
    push dword m
    push dword format1
    call [scanf]
    add esp,4*2
    
    push dword mesaj3
    call [printf]
    add esp,4*1   
        
    push dword n
    push dword format1
    call [scanf]
    add esp,4*2
    
    mov ebx,0
    mov ecx,[m]
    sub ecx,[n]
    mov dx, 1111111111111111
    repeta:
        clc
        shr dx,1
    loop repeta
    
    mov bx,[a]
    and bx,dx
        
    push dword ebx
    push dword format2
    call [printf]
    add esp,4*2
    
push dword 0
call [exit]