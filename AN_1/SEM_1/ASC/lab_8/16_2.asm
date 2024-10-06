;Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de litere 'y' si 'z' si sa se afiseze aceaste valori. Numele fisierului text este definit in segmentul de date.

bits 32 
global start

extern exit, fopen, fread, fclose, printf
import exit msvcrt.dll  
import fopen msvcrt.dll  
import fread msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data

nume_fisier db "fisier.txt", 0    ;numele fisierului
mod_acces db "r", 0               ;modul de deschidere a fisierului
descriptor dd -1                  ;variabila unde se salveaza descriptorul fisierului
len equ 100                       ;nr maxim de elemente citite din fisier
text times (len+1) db 0           ;sirul citit din fisier

format db "S-a citit cu succes sirul din fisier. Textul este %s", 10, 13, 0
format1 db "Numarul de litere y este: %d", 10, 13, 0
format2 db "Numarul de litere z este: %d", 0
y db "y"
z db "z"
nr_y dd 0
nr_z dd 0
  
segment code use32 class=code
start:

    ;se apeleaza functia fopen
    push dword mod_acces     
    push dword nume_fisier
    call [fopen]
    add esp, 4*2 
    
    ;se salveaza valoarea returnata de fopen
    mov [descriptor],eax
   
    ;verificam daca totul a mers bine, daca eax != 0
    cmp eax,0
        je final
    
    ;se citeste textul din fisier, in eax este nr de caractere citite
    push dword [descriptor]
    push dword len
    push dword 1
    push dword text
    call [fread]
    add esp,4*4
    
    push eax
    
    ;se afiseaza textul citit
    push dword text
    push dword format
    call [printf]
    add esp, 4*2
    
    pop eax
    
    ;se contorizeaza nr de y si z din text
    mov bl,[y]                          ;bl=codul ascii al lui y
    mov dl,[z]                          ;dl=codul ascii al lui z
    mov ecx, eax                        ;ecx=lungimea textului
    cld
    mov esi,text                    ;esi=adresa de inceput a textului
    repeta2:
        lodsb                       ;al= elementul de la adresa esi
        cmp al,'y'                  
            jne urmatorul
            inc dword [nr_y]        ;creste nr_y daca in al se afla codul ascii al lui y
        urmatorul:
        cmp al,dl
            jne urmatorul2
            inc dword [nr_z]        ;creste nr_z daca in al se afla codul ascii al lui z
        urmatorul2:
    loop repeta2
    
    ;se afiseaza nr de litere y
    push dword [nr_y]
    push dword format1
    call [printf]
    add esp,4*2
    
    ;se afiseaza nr de litere z
    push dword [nr_z]
    push dword format2
    call [printf]
    add esp,4*2
    
    ;se apeleaza functia fclose
    push dword [descriptor]
    call [fclose]
    add esp, 4
    
    final:
push dword 0 
call [exit] 
