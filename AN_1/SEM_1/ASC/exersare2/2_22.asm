;Se da numele unui fisier si un numar pe cuvant scris in memorie. Se considera numarul in reprezentarea fara semn. Sa se scrie cifrele zecimale ale acestui numar ca text in fisier, fiecare cifra pe o linie separata.

bits 32
global start
extern exit,fclose,fprintf,fopen
import exit msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll

segment data use32 class=data

nume_fisier db "22.txt", 0
mod_acces db "w", 0
descriptor dd -1
nr dw 12345
format db "%c",10, 0

segment code use32 class=code

start:
        
    push dword mod_acces
    push dword nume_fisier
    call [fopen]
    add esp,4*2
    mov [descriptor],eax
    
    cmp eax,0
    je final

    mov ebx,0
    mov bx,[nr]
    repeta:
        mov ecx,0
        mov edx,0
        mov eax,0
        mov cx,10
        mov ax,bx
        mov dx,0
        div cx
        add dl,'0'
        mov bx,ax
        
        push edx
        push dword format 
        push dword [descriptor]
        call [fprintf]
        add esp,4*3
        
        test bx,bx
    jnz repeta
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
push dword 0
call [exit]