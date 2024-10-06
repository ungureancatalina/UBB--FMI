;Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, litere mari, cifre si caractere speciale. Sa se inlocuiasca toate CIFRELE din textul dat cu caracterul 'C'. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut prin inlocuire in fisier.

bits 32
global start
extern exit,fclose,fprintf,fopen
import exit msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll

segment data use32 class=data

nume_fisier db "24.txt", 0
mod_acces db "w", 0
descriptor dd -1
text db "nfo]59.sAD", 0
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
    mov esi,text
    mov edi,text2
    repeta:
        lodsb
        cmp al,'0'
            jl dupa
        cmp al,'9'
            jg dupa
        mov al,'C'
        dupa:
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