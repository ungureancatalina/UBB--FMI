bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data
;calculati (a+b)/2+(10-a/c)+b/4 cu a,b,c - byte, d - word

a db 4
b db 5
c db 2
d dw 9

segment code use32 class=code
start:

;a+b
mov ax, 0
mov al, [a]
add al, [b]


;(a+b)/2
mov dl, 2
div byte dl
mov cl,al

;a/c
mov ax, 0
mov al, [a]
div byte [c]


;10-a/c
mov bl,10
sub bl,al

;(a+b)/2+(10-a/c)
add cl,al

;b/4
mov ax, 0
mov al, [b]
mov dl, 4
div byte dl


;(a+b)/2+(10-a/c)+b/4
add cl,al
    
push dword 0 
call [exit] 
