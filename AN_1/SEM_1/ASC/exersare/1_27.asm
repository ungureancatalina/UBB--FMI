;Se dă un sir de caractere (definit in segmentul de date). Să se citească de la tastatură un caracter, să se determine numărul de apariţii al acelui caracter în şirul dat şi să se afişeze acel caracter împreună cu numărul de apariţii al acestuia.

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

sir dd 'a','b','c','a','b','c','a','b','a'
len equ $-sir
nr dd 0
c resd 1
format1 db "%c", 0
mesaj1 db "caracterul este ", 0
format2 db "aparitii= %d, caracter= %c", 0

segment code use32 class=code

start:
        
    push dword mesaj1
    call [printf]
    add esp,4*1   
        
    push dword c
    push dword format1
    call [scanf]
    add esp,4*2
    
    mov esi,sir
    mov ebx,[c]
    mov ecx,len
    repeta:
        mov eax,0
        lodsb
        cmp eax,ebx
        jne dupa
            inc dword [nr]
        dupa:
    loop repeta
        
    push dword [c]
    push dword [nr]
    push dword format2
    call [printf]
    add esp,4*3
    
push dword 0
call [exit]