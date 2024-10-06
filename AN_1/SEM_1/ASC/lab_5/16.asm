; Se dau doua siruri de caractere S1 si S2. Sa se construiasca sirul D prin concatenarea elementelor de pe pozitiile impare din S2 cu elementele de pe pozitiile pare din S1.
; Exemplu:
; S1: 'a', 'b', 'c', 'b', 'e', 'f'
; S2: '1', '2', '3', '4', '5'
; D: '1', '3', '5', 'b', 'b', 'f'

bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data

s1 db 'a','b','c','d','e','f'
s2 db '1','2','3','4','5'
lung equ $-s2
d times lung db 0
  
segment code use32 class=code
start:

    mov ecx,lung
    mov esi,0
    mov ebx,2
    jecxz sfarsit
    repeta:
        mov eax,0
        mov edx,0
        mov eax, esi
        div dword ebx
        cmp eax,0
            ja impar
            mov bl,[s2+esi]
            mov [d+esi],bl
            jmp final
        impar:
            mov bl,[s1+esi]
            mov [d+esi],bl
        final:
        inc esi
    loop repeta
    sfarsit:
push dword 0 
call [exit] 
