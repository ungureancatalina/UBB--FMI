;Sa se citeasca de la tastatura un nume de fisier si un text. Sa se creeze un fisier cu numele dat in directorul curent si sa se scrie textul in acel fisier. Observatii: Numele de fisier este de maxim 30 de caractere. Textul este de maxim 120 de caractere.

bits 32
global start
extern exit,fclose,fprintf,fopen,scanf,printf
import exit msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

len_nume equ 31
nume_fisier times len_nume db 0
mod_acces db "w", 0
descriptor dd -1
len equ 121
text times len db 0 
format1 db "Numele fisierului: ", 0
format2 db "Textul: ", 0
format3 db "%s", 0

segment code use32 class=code

start:

    push dword format1
    call [printf]
    add esp,4*1
    
    push dword nume_fisier
    push dword format3
    call [scanf]
    add esp,4*2
    
    push dword format2
    call [printf]
    add esp,4*1
    
    push dword text
    push dword format3
    call [scanf]
    add esp,4*2
        
    push dword mod_acces
    push dword nume_fisier
    call [fopen]
    add esp,4*2
    mov [descriptor],eax
    
    cmp eax,0
    je final

    push dword text
    push dword [descriptor]
    call [fprintf]
    add esp,4*2
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
push dword 0
call [exit]