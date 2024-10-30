;Se da un fisier text. Fisierul contine numere (in baza 10) separate prin spatii. Sa se citeasca continutul acestui fisier, sa se determine minimul numerelor citite si sa se scrie rezultatul la sfarsitul fisierului.

bits 32
global start
extern exit,fclose,fread,fopen,fprintf
import exit msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll


segment data use32 class=data

nume_fisier db "27.txt", 0
mod_acces db "r", 0
descriptor dd -1
len equ 101
text times len db 0
min dd 10000

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
    
    mov ecx,eax
    repeta:
        lodsb
        
    loop repeta
    
    push dword [descriptor]
    call [fclose]
    add esp,4*1
    
    final:
push dword 0
call [exit]