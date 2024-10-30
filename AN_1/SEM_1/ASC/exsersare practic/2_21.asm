;Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, cifre si spatii. Sa se inlocuiasca toate cifrele de pe pozitii impare cu caracterul ‘X’. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.

bits 32
global start
extern exit,fclose,fprintf,fopen
import exit msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll

segment data use32 class=data

nume_fisier db "21.txt", 0
mod_acces db "w", 0
descriptor dd -1
text db "23 fkgr 456", 0
len equ $-text-1
text2 times len db 0

segment code use32 class=code

impar:
    mov dl,bl
    clc
    shr dl,1
        jnc dupa
    mov al,'X'
    dupa:
ret

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
    mov bx,0
    repeta:
        lodsb
        cmp al,' '
            je urm1
        cmp al,'9'
            jg urm1
        cmp al,'0'
            jl urm1
        call impar
        urm1:
        inc bl
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