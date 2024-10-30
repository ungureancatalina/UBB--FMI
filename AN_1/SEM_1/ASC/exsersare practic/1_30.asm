;Se citesc de la tastatura numere (in baza 10) pana cand se introduce cifra 0. Determinaţi şi afişaţi cel mai mic număr dintre cele citite.

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

nr dd 0
min dd 100000
format1 db "%d", 0
format2 db "nr min= %d", 0

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
        mov edx,[min]
        
        cmp eax, ebx
        je afara
        
        cmp eax,edx
        jg iesire
            mov [min],eax
        iesire:
        mov ecx,2
    loop repeta
    afara:

    push dword [min]
    push dword format2
    call [printf]
    add esp,4*2
    
push dword 0
call [exit]