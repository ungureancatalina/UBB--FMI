;Se da un fisier text. Sa se obtina un alt fisier cu textul din primul fisier, dar in care caracterul de pe pozitia i se va interschimba cu caracterul de pe pozitia N/2+i, unde N este dimensiunea primului fisier in octeti.

bits 32
global start

extern exit,fprintf,fopen,fclose,fread
import exit msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fread msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data

len equ 101
descriptor1 dd -1
nume_fisier1 db "fis1.txt", 0
mod_acces1 db "r", 0
text1 times len db 0

descriptor2 dd -1
nume_fisier2 db "fis2.txt", 0
mod_acces2 db "w", 0
text2 times len db 0
format db "%s", 0

n dd 0   ;lungimea textului
i dd 2   ;poz i    
nr dd 0  ;poz n/2+i
elem_i db 0     ;aici salvez elem de pe poz i
elem_nr db 0    ;aici salvez elem de pe poz n/2+i

segment code use32 class=code

start:

    ;primul fisier

    ;se deschide fis1
    push dword mod_acces1
    push dword nume_fisier1
    call [fopen]
    add esp,4*2
    
    mov [descriptor1],eax

    ;daca nu se deschide bine iese din program
    cmp eax,0
        je final
        
    ;se citeste text1
    push dword [descriptor1]
    push dword len
    push dword 1
    push dword text1
    call [fread]
    add esp,4*4

    ;in eax se va afla lungimea textului1
    ;calculez si salvez in nr n/2+i
    mov [n],eax
    pushad
    mov eax,[n]
    mov edx,0
    mov ebx,2
    idiv ebx
    add eax,[i]
    mov [nr],eax
    
    ;daca i sau nr sunt mai mari decat lungimea textului
    mov eax,[i]
    cmp eax,[n]
        jge afara
    mov eax,[nr]
    cmp eax,[n]
        jge afara
    popad
    
    ;parcurg text1 pt a afla cele 2 elemente
    ;in ebx se salveaza pozitia curenta
    mov ecx,eax
    mov esi,text1
    mov ebx,0       
    repeta:
        lodsb
        cmp ebx,[i]
            jne urm
        mov [elem_i],al 
        urm: 
        cmp ebx,[nr]
            jne urm2
        mov [elem_nr],al
        urm2:   
        inc ebx
    loop repeta
    
    ;pargur iar pt a interschimba
    ;in ebx se salveaza pozitia curenta
    mov ecx,[n]
    mov esi,text1
    mov edi,text2
    mov ebx,0       
    repeta2:
        lodsb
        cmp ebx,[i]
            jne urmm
        mov al,[elem_nr]
        urmm: 
        cmp ebx,[nr]
            jne urmm2
        mov al,[elem_i]
        urmm2:   
        stosb
        inc ebx
    loop repeta2
        
    ;se inchide fis1
    push dword [descriptor1]
    call [fclose]
    add esp,4*1
    
    
    ;al doilea fisier
    ;se deschide fis2
    push dword mod_acces2
    push dword nume_fisier2
    call [fopen]
    add esp,4*2
    mov [descriptor2],eax

    ;daca nu se deschide bine iese din program
    cmp eax,0
        je final
    
    ;se scrie in fis2 textul2 schimbat
    push dword text2
    push dword format
    push dword [descriptor2]
    call [fprintf]
    add esp,4*3
        
    ;se inchide fis2
    push dword [descriptor2]
    call [fclose]
    add esp,4*1
    
    jmp final
    
    afara:
    ;se deschide fis2
    push dword mod_acces2
    push dword nume_fisier2
    call [fopen]
    add esp,4*2
    mov [descriptor2],eax

    ;daca nu se deschide bine iese din program
    cmp eax,0
        je final
    
    ;se scrie in fis2 textul1 neschimbat deoarece o pozitie nu poate fi accesata
    push dword text1
    push dword format
    push dword [descriptor2]
    call [fprintf]
    add esp,4*3
        
    ;se inchide fis2
    push dword [descriptor2]
    call [fclose]
    add esp,4*1
        
    final:
  
push dword 0
call [exit]