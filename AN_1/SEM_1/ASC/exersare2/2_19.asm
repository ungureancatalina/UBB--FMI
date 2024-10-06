;Se dau in segmentul de date un nume de fisier si un text (poate contine orice tip de caracter). Sa se calculeze suma cifrelor din text. Sa se creeze un fisier cu numele dat si sa se scrie suma obtinuta in fisier.

bits 32
global start
extern exit,fclose,fprintf,fopen
import exit msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll

segment data use32 class=data

nume_fisier db "19.txt", 0
mod_acces db "w", 0
descriptor dd -1
text db "ab94hdeIJ62", 0
len equ $-text-1
suma dd 0
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
        
    mov esi,text
    mov ecx,len
    repeta:
        mov eax,0
        lodsb
        cmp al,'0'
            jl urm1
        cmp al,'9'
            jg urm1
        sub eax,'0'
        add [suma],eax
        urm1:
    loop repeta
    
    push dword [suma]
    push dword format
    push dword [descriptor]
    call [fprintf]
    add esp,4*3
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
push dword 0
call [exit]