;Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de cifre pare si sa se afiseze aceasta valoare. Numele fisierului text este definit in segmentul de date.

bits 32
global start
extern exit,printf,fclose,fread,fopen
import exit msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import fopen msvcrt.dll

segment data use32 class=data

nume_fisier db "3.txt", 0
mod_acces db "r", 0
descriptor db -1
len equ 100
text times len db 0 

nr dd 0
format db "nr pare= %d", 0

segment code use32 class=code

par:
    clc
    shr al,1
    jc afara
        add dword [nr],1
    afara:
ret

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
        
        cmp al,'0'
        jl iesire
        cmp al,'9'
        jg iesire
        
        sub al,'0'
        call par
        iesire:
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