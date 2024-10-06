;Se da un fisier text care contine litere, spatii si puncte. Sa se citeasca continutul fisierului, sa se determine numarul de cuvinte si sa se afiseze pe ecran aceasta valoare. (Se considera cuvant orice secventa de litere separate prin spatiu sau punct)


bits 32
global start
extern exit,fclose,printf,fopen,fread
import exit msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import fopen msvcrt.dll
import fread msvcrt.dll


segment data use32 class=data

nume_fisier db "18.txt", 0
mod_acces db "r", 0
descriptor dd -1
len equ 101
text times len dd 0
nr dd 0
format db "%d", 0

segment code use32 class=code

start:
        
    push dword mod_acces
    push dword nume_fisier
    call [fopen]
    add esp,4*2
    mov [descriptor],eax
    
    cmp eax,0
        je final
    
    push dword [descriptor]
    push dword len
    push dword 1
    push dword text
    call [fread]
    add esp,4*4
    
    mov esi,text
    mov ecx,eax
    repeta:
        lodsb
        cmp al,' '
            jne urm1
        add dword [nr],1
        urm1:
        cmp al,'.'
            je urm2
    loop repeta
    urm2:
    add dword [nr],1

    
    push dword [nr]
    push dword format
    call [printf]
    add esp,4*2
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
push dword 0
call [exit]