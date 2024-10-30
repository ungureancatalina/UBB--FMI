;Sa se citeasca de la tastatura doua numere a si b (in baza 10) şi să se determine relaţia de ordine dintre ele (a < b, a = b sau a > b). Afisati rezultatul în următorul format: "<a> < <b>, <a> = <b> sau <a> > <b>".

bits 32
global start
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data

a resd 1
b resd 1
mesaj1 db "a= ", 0
mesaj2 db "b= ", 0
format1 db "%d", 0
format2 db "%d = %d", 0
format3 db "%d > %d", 0
format4 db "%d < %d", 0

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
    
    mov eax,[a]
    mov ebx,[b]
    cmp eax,ebx  
    je egal
    jl maimic
        push dword [b]
        push dword [a]
        push dword format3
        call [printf]
        add esp,4*3
        jmp afara
    
    maimic:
    push dword [b]
    push dword [a]
    push dword format4
    call [printf]
    add esp,4*3
    jmp afara
    
    egal:    
    push dword [b]
    push dword [a]
    push dword format2
    call [printf]
    add esp,4*3
    jmp afara
    
    afara:
    
push dword 0
call [exit]