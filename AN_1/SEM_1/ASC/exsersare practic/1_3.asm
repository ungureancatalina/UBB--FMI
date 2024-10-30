;Se dau doua numere naturale a si b (a, b: dword, definite in segmentul de date). Sa se calculeze suma lor si sa se afiseze in urmatorul format: "<a> + <b> = <result>" Exemplu: "1 + 2 = 3" Valorile vor fi afisate in format decimal (baza 10) cu semn.

bits 32
global start
extern exit,printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data

a dd 10
b dd 13
rezultat resq 1
mesaj db "%d + %d = %d", 0

segment code use32 class=code

start:
    mov edx,0
    mov eax,[a]
    add eax,[b]
    adc edx, 0
    mov [rezultat],eax
    mov [rezultat+4],edx
    
    push dword [rezultat+4]
    push dword [rezultat]
    push dword [b]
    push dword [a]
    push dword mesaj
    call [printf]
    add esp,4*5
    
push dword 0
call [exit]