;Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, litere mari, cifre si caractere speciale. Sa se transforme toate literele mari din textul dat in litere mici. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.

bits 32
global start
extern exit,fclose,fprintf,fopen,printf,scanf
import exit msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

nume_fisier db "14.txt", 0
mod_acces db "w", 0
descriptor dd -1
text db "fABgd84?#", 0
len equ $-text-1
text2 times len db 0

segment code use32 class=code

start:
        
    push dword mod_acces
    push dword nume_fisier
    call [fopen]
    add esp,4*2
    mov [descriptor],eax
    
    cmp eax,0
    je final

    mov ecx,len
    mov edi,text2
    mov esi,text
    repeta:
        lodsb
        cmp al,'A'
            jl urm
        cmp al,'Z'
            jg urm
        mov bl,'A'-'a'
        sub al,bl
        urm:
        stosb
    loop repeta
    
    push dword text2
    push dword [descriptor]
    call [fprintf]
    add esp,4*2
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
push dword 0
call [exit]