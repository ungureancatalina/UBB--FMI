;Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, apoi sa se citeasca de la tastatura numere si sa se scrie din valorile citite in fisier numerele divizibile cu 7, pana cand se citeste de la tastatura valoarea 0.

bits 32
global start
extern exit,fclose,fprintf,fopen,scanf
import exit msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

nume_fisier db "17.txt", 0
mod_acces db "w", 0
descriptor dd -1
len equ 101
text times len dd 0
text2 times len dd 0
format db "%d",0

segment code use32 class=code

diviz:             ;daca se imparte cl=0
    mov edx,0
    mov cl,0
    mov ebx,7
    div ebx
    cmp edx,0
        je afara
    mov cl,1
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
    
    mov ecx,len
    mov esi,text
    mov edi,text2
    repeta: 
        push ecx
    
        push dword text
        push dword format
        call [scanf]
        add esp,4*2
        
        lodsd
        mov ecx,0
        cmp eax,0
            je gata
        call diviz
        cmp cl,0
            jne urm
        stosd
        mov eax,' '
        stosd
        urm:
        
        pop ecx
    loop repeta
    gata:
    
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