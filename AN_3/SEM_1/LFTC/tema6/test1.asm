bits 32
global start

extern exit
import exit msvcrt.dll
extern scanf
import scanf msvcrt.dll
extern printf
import printf msvcrt.dll

segment data use32 class=data
    x dd 0
    y dd 0
    format db "%d", 0
    newline db 10, 0

segment code use32 class=code
start:
    push dword x
    push dword format
    call [scanf]
    add esp, 8
    mov eax, [x]
    push eax
    mov eax, 2
    push eax
    pop ebx
    imul eax, ebx
    mov eax, 3
    push eax
    pop ebx
    add eax, ebx
    mov [y], eax
    push dword [y]
    push dword format
    call [printf]
    add esp, 8
    push dword 0
    call [exit]
