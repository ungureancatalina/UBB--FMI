; Se dau un sir care contine n reprezentari binare pe 8 biti date ca sir de caractere.
; Sa se obtina un sir care contine numerele corespunzatoare reprezentarilor binare.
; Exemplu:
; Se da: '10100111b', '01100011b', '110b', '101011b'
; Se stocheaza: 10100111b, 01100011b, 110b, 101011b

bits 32 
global start
extern creare
extern exit,printf
import exit msvcrt.dll  
import printf msvcrt.dll  

segment data use32 class=data
    a db "10100111b","01100011b","110b","101011b", "2"
    n dd 4
    numar dd 0

    format db "Sirul este:",10, 13, 0
    format2 db "%d ",0
  
segment code use32 class=code

start:
    ;in ecx se pune numarul n de reprezentari binare
    mov ecx,[n]
    mov esi, a
    
    ;se afiseaza primul mesaj
    push dword format
    call [printf]
    add esp,4
    
    ;se repeta pana cand ecx e 0
    repeta:
        jecxz final
        
        ;se pune ecx pe stiva pentru a nu se modifica si se initializeaza cu 8(nr de biti)
        push ecx
        mov ecx, 8
        repeta2:
            
            shl dword [numar], 1
            
            ;apeleaza tranforma pentru a pune in edi cifra corespunzatoare caracterului din esi
            mov eax, 0
            lodsb
            push eax
            call creare
            add esp,4
            
            ;pune in numarul numar cifra
            cmp al, 1
            jne dupa
                add dword [numar], 1
            dupa:
           
            ;daca caracterul este b iese din loop pt a citi urmatorul sir
            cmp byte [esi],'b'
            je afara
            
        loop repeta2
        afara:
        
        ;sare peste caracterul b
        inc esi
        
        ;se afiseaza numarul creat
            push dword [numar]
            push format2
            call [printf]
            add esp,4*2
        
        ;se scoate de pe stiva ecx
        pop ecx
        
        ;reinitializam numar cu 0
        mov dword [numar], 0
        
        ; daca s-a ajuns la caracterul 2 iese din loop
        cmp byte [esi],'2'
            je final
        
    loop repeta
    final:

push dword 0 
call [exit] 
    
