;Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de caractere speciale si sa se afiseze aceasta valoare. Numele fisierului text este definit in segmentul de date.

bits 32
global start
extern exit,printf,fclose,fread,fopen
import exit msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import fopen msvcrt.dll

segment data use32 class=data

nume_fisier db "5.txt", 0
mod_acces db "r", 0
descriptor db -1
len equ 100
text times len db 0 

nr dd 0
format db "nr de carcatere speciale= %d", 0

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
        cmp al,'9'
        jg urm1
            cmp al,'0'
            jge afara
                add dword [nr],1
                jmp afara
        urm1:
        cmp al,'Z'
        jg urm2
            cmp al,'A'
            jge afara
                add dword [nr],1
                jmp afara    
        urm2:
        cmp al,'z'
        jg urm3
            cmp al,'a'
            jge afara
                add dword [nr],1
                jmp afara    
        urm3:
        add dword [nr],1
        afara:
    loop repeta
    
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