;Se da un numar natural negativ a (a: dword). Sa se afiseze valoarea lui in baza 10 si in baza 16, in urmatorul format: "a = <base_10> (baza 10), a = <base_16> (baza 16)"

bits 32
global start
extern exit,printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data

a dd -20
format1 db "a= %d", 13,10, 0
format2 db "a= %x", 0

segment code use32 class=code

start:
        
    push dword [a]
    push dword format1
    call [printf]
    add esp,4*2
    
    push dword [a]
    push dword format2
    call [printf]
    add esp,4*2
    
push dword 0
call [exit]