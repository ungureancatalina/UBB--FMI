bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data
;calculati c-a-(b+a)+c  a - byte, b - word, c - double word, d - qword - Interpretare fara semn

a db 8
b dw 5
c dd 10

segment code use32 class=code
start:

;b+a
mov ax, 0
mov al, [a]
add ax, [b]
    
;c-a
mov ebx, [c]
mov ecx, 0
mov cl, [a]
sub ebx, ecx

;c-a-(b+a)
mov edx, 0 
mov dx, ax
sub edx,ebx

;c-a-(b+a)+c
add edx,ecx  
  
push dword 0 
call [exit] 
