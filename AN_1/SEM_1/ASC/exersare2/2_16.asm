;Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de litere 'y' si 'z' si sa se afiseze aceaste valori. Numele fisierului text este definit in segmentul de date.

bits 32
global start
extern exit,fclose,printf,fopen,fread
import exit msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import fopen msvcrt.dll
import fread msvcrt.dll

segment data use32 class=data

nume_fisier db "16.txt", 0
mod_acces db "r", 0
descriptor dd -1
len equ 100
text times len db 0
y dd 0
z dd 0
mesaj db "y: %d, z: %d", 0

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
    
    mov ecx,eax
    mov esi,text
    repeta:
        lodsb
        cmp al,'y'
        jne urm
            inc dword [y]
        urm:
        cmp al,'z'
        jne urm2
            inc dword [z]
        urm2:
    loop repeta
    
    push dword [z]
    push dword [y]
    push dword mesaj
    call [printf]
    add esp,4*3
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
push dword 0
call [exit]