;Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, apoi sa se citeasca de la tastatura cuvinte si sa se scrie in fisier cuvintele citite pana cand se citeste de la tastatura caracterul '$'.

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

nume_fisier db "11.txt", 0
mod_acces db "w", 0
len equ 101
descriptor dd -1
cuv times len db 0
mesaj db "textul este: ", 0
format db "%s", 0
car dd ' '
format2 db "%c", 0

segment code use32 class=code

start:
        
    push dword mod_acces
    push dword nume_fisier
    call [fopen]
    add esp,4*2
    mov [descriptor],eax
    
    cmp eax,0
    je final

    push dword mesaj
    call [printf]
    add esp,4*1
    
    mov ecx,len
    repeta:
        push dword cuv
        push dword format
        call [scanf]
        add esp,4*2
        
        mov esi,cuv
        lodsb
        cmp al,'$'
            je afara
            
        push dword cuv
        push dword format
        push dword [descriptor]
        call [fprintf]
        add esp,4*3
        
        push dword [car]
        push dword format2
        push dword [descriptor]
        call [fprintf]
        add esp,4*3

    loop repeta
    afara:
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
push dword 0
call [exit]