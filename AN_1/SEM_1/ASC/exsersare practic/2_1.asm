;Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de vocale si sa se afiseze aceasta valoare. Numele fisierului text este definit in segmentul de date.

bits 32
global start
extern exit,printf,fclose,fread,fopen
import exit msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import fopen msvcrt.dll

segment data use32 class=data

nume_fisier db "1.txt", 0
mod_acces db "r", 0
descriptor dd -1
len equ 100
text times len db 0 

nr dd 0
format db "nr de vocale= %d", 0

segment code use32 class=code

    vocale:
        mov bl,'a'
        cmp al,bl
        jne dupa1
            add dword [nr],1
            jmp afara
        dupa1:
        mov bl,'e'
        cmp al,bl
        jne dupa2
            add dword [nr],1
            jmp afara
        dupa2:
        mov bl,'i'
        cmp al,bl
        jne dupa3
            add dword [nr],1
            jmp afara
        dupa3:
        mov bl,'o'
        cmp al,bl
        jne dupa4
            add dword [nr],1
            jmp afara
        dupa4:
        mov bl,'u'
        cmp al,bl
        jne dupa5
            add dword [nr],1
            jmp afara
        dupa5:
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
        call vocale
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