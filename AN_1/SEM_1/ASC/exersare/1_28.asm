;Se citesc de la tastatura numere (in baza 10) pana cand se introduce cifra 0. Determinaţi şi afişaţi cel mai mare număr dintre cele citite.

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

nr dd 0
max dd 0
format1 db "%d", 0
format2 db "nr max= %d", 0

segment code use32 class=code

start:
    
    mov ecx, 2
    mov ebx,0
    
    repeta:
        push dword nr
        push dword format1
        call [scanf]
        add esp,4*2
        
        mov eax,[nr]
        mov edx,[max]
        
        cmp eax, ebx
        je afara
        
        cmp eax,edx
        jl iesire
            mov [max],eax
        iesire:
        mov ecx,2
    loop repeta
    afara:

    push dword [max]
    push dword format2
    call [printf]
    add esp,4*2
    
push dword 0
call [exit]