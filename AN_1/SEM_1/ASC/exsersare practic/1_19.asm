;Sa se citeasca de la tastatura un octet si un cuvant. Sa se afiseze pe ecran daca bitii octetului citit se regasesc consecutiv printre bitii cuvantului. Exemplu: a = 10 = 0000 1010b b = 256 = 0000 0001 0000 0000b Pe ecran se va afisa NU. a = 0Ah = 0000 1010b b = 6151h = 0110 0001 0101 0001b Pe ecran se va afisa DA (bitii se regasesc pe pozitiile 5-12).

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resb 1
b resw 1
mesaj1 db "a= ", 0
mesaj2 db "b= ", 0
format1 db "%x", 0
mesaj3 db "da", 0
mesaj4 db "nu", 0

segment code use32 class=code

start:
        
    push dword mesaj1
    call [printf]
    add esp,4*1   
        
    push dword a
    push dword format1
    call [scanf]
    add esp,4*2
    
    push dword mesaj2
    call [printf]
    add esp,4*1   
        
    push dword b
    push dword format1
    call [scanf]
    add esp,4*2
    
    mov al,[a]
    mov bx,[b]
    
    mov ecx,8
    repeta:
        cmp al,bl
        jne dupa 
            push dword mesaj3
            call [printf]
            add esp,4*2
            jmp afara
        dupa:
        shr bx,1
    loop repeta
    
    push dword mesaj4
    call [printf]
    add esp,4*2
   
    afara:
    
push dword 0
call [exit]