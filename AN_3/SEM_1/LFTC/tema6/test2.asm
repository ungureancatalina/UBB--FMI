bits 32
global start

extern exit
import exit msvcrt.dll
extern scanf
import scanf msvcrt.dll
extern printf
import printf msvcrt.dll

segment data use32 class=data
    a dd 0
    b dd 0
    c dd 0
    format db "%d", 0
    newline db 10, 0

segment code use32 class=code
start:
    mov eax, 2
    push eax
    mov eax, 3
    push eax
    mov eax, 4
    push eax
    pop ebx
    imul eax, ebx
    pop ebx
    add eax, ebx
    mov [a], eax
    mov eax, [a]
    push eax
    mov eax, 5
    push eax
    pop ebx
    imul eax, ebx
    mov [b], eax
    mov eax, [b]
    push eax
    mov eax, [a]
    push eax
    pop ebx
    add eax, ebx
    mov [c], eax
    push dword [c]
    push dword format
    call [printf]
    add esp, 8
    push dword 0
    call [exit]
