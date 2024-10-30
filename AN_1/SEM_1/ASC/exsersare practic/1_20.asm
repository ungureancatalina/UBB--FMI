;Sa se citeasca de la tastatura in baza 16 doua numere a si b de tip dword si sa se afiseze suma partilor low si diferenta partilor high. Exemplu: a = 00101A35h, b = 00023219h suma = 4C4Eh diferenta = Eh

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resd 1
b resd 1
suma dw 0
dif dw 0
mesaj1 db "a= ", 0
mesaj2 db "b= ", 0
format1 db "%x", 0
format2 db "suma= %x, diferenta= %x", 0

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
    
    mov eax,0
    mov ebx,0
    mov ax,[a]
    add ax,[b]
    mov bx,[a+2]
    sub bx,[b+2]
    
    mov [suma],eax
    mov [dif],ebx    
        
    push dword ebx
    push dword eax
    push dword format2
    call [printf]
    add esp,4*3
    
push dword 0
call [exit]