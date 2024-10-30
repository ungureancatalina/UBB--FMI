;Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, apoi sa se citeasca de la tastatura cuvinte pana cand se citeste de la tastatura caracterul '$'. Sa se scrie in fisier doar cuvintele care contin cel putin o litera mica (lowercase).

bits 32
global start
extern exit,fclose,scanf,fopen,fprintf
import exit msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll


segment data use32 class=data

nume_fisier db "28.txt", 0
mod_acces db "w", 0
descriptor dd -1
len equ 101
text times len db 0
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
   
    mov ecx,len
    repeta:
        push ecx
        
        push dword text
        push dword format
        call [scanf]
        add esp,4*2
        
        mov esi,text
        lodsb
        cmp al,'$'
            je afara
            
        dec esi
        mov ecx,len

        repeta2:
            lodsb
            cmp al,'a'
                jl dupa
            cmp al,'z'
                jg dupa
            
            push dword text
            push dword format
            push dword [descriptor]
            call [fprintf]
            add esp,4*3
            jmp urm_cuv
            
            dupa:
        loop repeta2
        pop ecx
        urm_cuv:
    loop repeta
    afara:
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
push dword 0
call [exit]