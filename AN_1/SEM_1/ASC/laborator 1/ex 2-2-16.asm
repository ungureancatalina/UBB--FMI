bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data
;calculati (a+b+b)+(c-d) cu a,b,c,d - word

a dw 13
b dw 15
c dw 10
d dw 11

segment code use32 class=code
start:

;a+b+b
mov ax, [a]
add ax, [b]
add ax, [b]

;c-d
mov bx, [c]
sub bx, [d]

;(a+b+b)+(c-d)
add ax, bx
    
push dword 0 
call [exit] 
