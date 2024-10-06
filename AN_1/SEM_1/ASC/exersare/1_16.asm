;Sa se citeasca de la tastatura doua numere a si b (in baza 10). Sa se calculeze si sa se afiseze media lor aritmetica in baza 16.

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resd 1
b resd 1
rezultat resd 1
mesaj1 db "a= ", 0
mesaj2 db "b= ", 0
format1 db "%d", 0
format2 db "rezultat= %x", 0

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
    
    mov edx,0
    mov eax,[a]
    add eax,[b]
    adc edx,0
    mov ebx, 2
    idiv ebx
    
    mov [rezultat],eax  
        
    push dword [rezultat]
    push dword format2
    call [printf]
    add esp,4*2
    
push dword 0
call [exit]