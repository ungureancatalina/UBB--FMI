;Sa se citeasca de la tastatura doua numere a si b de tip word. Sa se afiseze in baza 16 numarul c de tip dword pentru care partea low este suma celor doua numere, iar partea high este diferenta celor doua numere. Exemplu: a = 574, b = 136 c = 01B602C6h

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resw 1
b resw 1
c dd 0
mesaj1 db "a= ", 0
mesaj2 db "b= ", 0
format1 db "%d", 0
format2 db "c= %x", 0

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
    
    mov ax,[a]
    add ax,[b]
    mov bx,[a]
    sub bx,[b]
    
    add [c],ax
    adc [c+2],bx   
        
    push dword [c]
    push dword format2
    call [printf]
    add esp,4*2
    
push dword 0
call [exit]