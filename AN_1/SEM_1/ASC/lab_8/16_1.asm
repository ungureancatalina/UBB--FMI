;Sa se citeasca de la tastatura doua numere a si b (in baza 10). Sa se calculeze si sa se afiseze media lor aritmetica in baza 16.

bits 32 
global start

extern exit, printf, scanf          
import exit msvcrt.dll     
import printf msvcrt.dll     
import scanf msvcrt.dll   

segment data use32 class=data

a dd 0  
b dd 0     
message1 db "Numarul citit este a= ", 0  
message2 db "Numarul citit este b= ", 0  
format db "%d", 0 
format2 db "Media aritmetica a lui a si b este: %x", 0
  
segment code use32 class=code
start:
    ;se afiseaza primul mesaj pt citirea lui a
    push dword message1 
    call [printf]
    add  esp,4*1
    ;se citeste de la tastatura a 
    push dword a
    push dword format
    call [scanf]
    add esp,4*2
    
    ;se afiseaza al doilea mesaj pt citirea lui b
    push  dword message2 
    call  [printf]
    add  esp,4*1
    ;se citeste de la tastatura b
    push dword b
    push dword format
    call [scanf]
    add esp,4*2
    
    ;se calculeaza media aritmetica in eax
    mov eax,[a]
    add eax,[b]
    sar eax,1
    
    ;se afiseaza media aritmetica
    push dword eax
    push dword format2
    call [printf]
    add esp,4*2
    
push dword 0 
call [exit] 
