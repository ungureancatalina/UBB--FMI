bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data
;calculati a+13-c+d-7+b cu a,b,c,d - byte

a db 10
b db 4
c db 3
d db 7

segment code use32 class=code
start:

;a+13
mov al, [a]
add al, 13

;a+13-c
sub al, [c]

;a+13-c+d
add al, [d]

;a+13-c+d-7
sub al, 7

;a+13-c+d-7+b
add al, [b]
    
push dword 0 
call [exit] 


