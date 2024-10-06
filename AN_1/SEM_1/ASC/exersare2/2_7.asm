;Se da un fisier text. Sa se citeasca continutul fisierului, sa se determine litera mica (lowercase) cu cea mai mare frecventa si sa se afiseze acea litera, impreuna cu frecventa acesteia. Numele fisierului text este definit in segmentul de date.

bits 32
global start
extern exit,printf,fclose,fread,fopen
import exit msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import fopen msvcrt.dll

segment data use32 class=data

nume_fisier db "7.txt", 0
mod_acces db "r", 0
descriptor db -1
len equ 100
text times len db 0 

max dd 0
lit dd 0

c dd 0   ;lit din repetare
nr dd 0  ;frecv lit
l dd 0  ;lung text

format db "litera= %c, frecventa= %d", 0

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
    
    mov ecx,'z'
    repetare:
        mov [c],ecx
        pushad
        mov esi,text
        mov ecx,[l]
        repeta:
            mov eax,0
            lodsb
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
            mov dword [lit],ebx
        afara:
        mov dword [nr],0
        
        mov ebx,'a'
        cmp ebx,ecx
        je iesi
    loop repetare
    iesi:
    
    push dword [max]
    push dword [lit]
    push dword format
    call [printf]
    add esp,4*3
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
    
push dword 0
call [exit]