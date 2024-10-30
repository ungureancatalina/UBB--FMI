;Sa se citeasca de la tastatura un numar in baza 10 si un numar in baza 16. Sa se afiseze in baza 10 numarul de biti 1 ai sumei celor doua numere citite. Exemplu: a = 32 = 0010 0000b b = 1Ah = 0001 1010b 32 + 1Ah = 0011 1010b Se va afisa pe ecran valoarea 4.

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resd 1
b resd 1
suma dd 0
mesaj1 db "a= ", 0
mesaj2 db "b= ", 0
format1 db "%d", 0
format2 db "%x", 0
format3 db "suma= %d", 0

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
    push dword format2
    call [scanf]
    add esp,4*2
    
    mov eax,[a]
    add eax,[b]
    
    mov ecx,32
    repeta:
        shr eax,1
        jnc dupa 
            inc dword [suma]
        dupa:
    loop repeta
    
    push dword [suma]
    push dword format3
    call [printf]
    add esp,4*2
    
push dword 0
call [exit]