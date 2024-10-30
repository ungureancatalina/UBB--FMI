;Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, litere mari, cifre si caractere speciale. Sa se inlocuiasca toate caracterele speciale din textul dat cu caracterul 'X'. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.

bits 32
global start
extern exit,fclose,fprintf,fopen
import exit msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll

segment data use32 class=data

nume_fisier db "15.txt", 0
mod_acces db "w", 0
descriptor dd -1
text db "gABgd84?#", 0
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
    mov bl,'X'
    mov edi,text2
    mov esi,text
    repeta:
        lodsb
        cmp al,'9'
        jg urm1
            cmp al,'0'
            jge afara
                mov al,bl
                jmp afara
        urm1:
        cmp al,'Z'
        jg urm2
            cmp al,'A'
            jge afara
                mov al,bl
                jmp afara    
        urm2:
        cmp al,'z'
        jg urm3
            cmp al,'a'
            jge afara
                mov al,bl
                jmp afara    
        urm3:
        mov al,bl
        dupa:
        afara:
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