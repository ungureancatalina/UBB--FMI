;Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, apoi sa se citeasca de la tastatura numere si sa se scrie valorile citite in fisier pana cand se citeste de la tastatura valoarea 0.

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

nume_fisier db "12.txt", 0
mod_acces db "w", 0
len equ 101
descriptor dd -1
cuv times len db 0
cuv2 times len db 0
mesaj db "textul este: ", 0
format db "%s", 0

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
        mov edi,cuv2
        lodsb
        cmp al,'0'
            je afara
            
        dec esi    
        push ecx
        mov ecx,len
        repetare:
            lodsb
            cmp al,'0'
                je afara
            cmp al,'.'
                je dupa
            stosb
        loop repetare
        dupa:
        mov al,' '
        stosb

        push dword cuv2
        push dword [descriptor]
        call [fprintf]
        add esp,4*2
        
        mov ecx,len
        rp:
        mov byte [cuv2],0
        loop rp
        
        pop ecx
    loop repeta
    afara:
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
push dword 0
call [exit]