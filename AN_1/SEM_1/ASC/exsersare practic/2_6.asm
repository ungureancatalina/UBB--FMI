;Se da un fisier text. Sa se citeasca continutul fisierului, sa se determine cifra cu cea mai mare frecventa si sa se afiseze acea cifra impreuna cu frecventa acesteia. Numele fisierului text este definit in segmentul de date.

bits 32
global start
extern exit,printf,fclose,fread,fopen
import exit msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import fopen msvcrt.dll

segment data use32 class=data

nume_fisier db "6.txt", 0
mod_acces db "r", 0
descriptor db -1
len equ 100
text times len db 0 

max dd 0
cif dd 0
c dd 0
nr dd 0
l dd 0
format db "cifra= %d, frecventa= %d", 0

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
    
    mov [l],eax
    
    mov ecx,10
    repetare:
        mov [c],ecx
        sub dword [c],1
        pushad
        mov esi,text
        mov ecx,[l]
        repeta:
            mov eax,0
            lodsb
            sub al,'0'
            cmp eax,[c]
            jne dupa
                inc dword [nr]
            dupa:
        loop repeta    
        popad
        mov edx,[nr]
        mov ebx,[max]
        cmp edx,ebx
        jle afara
            mov dword [max],edx
            mov ebx, [c]
            mov dword [cif],ebx
        afara:
        mov dword [nr],0
    loop repetare
    
    push dword [max]
    push dword [cif]
    push dword format
    call [printf]
    add esp,4*3
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
    
push dword 0
call [exit]