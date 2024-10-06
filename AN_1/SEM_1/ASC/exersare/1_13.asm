;Sa se citeasca de la tastatura doua numere a si b (in baza 10) si sa se calculeze: (a+b) * (a-b). Rezultatul inmultirii se va salva in memorie in variabila "rezultat" (definita in segmentul de date).

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resd 1
b resd 1
rezultat resq 1
mesaj1 db "a= ", 0
mesaj2 db "b= ", 0
format1 db "%d", 0
format2 db "rezultat= %d", 0

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
    
    mov eax,[a]
    add eax,[b]
    mov ebx,[a]
    sub ebx,[b]
    imul ebx
    
    mov [rezultat],eax
    mov [rezultat+4],edx    
        
    push dword [rezultat+4]
    push dword [rezultat]
    push dword format2
    call [printf]
    add esp,4*3
    
push dword 0
call [exit]