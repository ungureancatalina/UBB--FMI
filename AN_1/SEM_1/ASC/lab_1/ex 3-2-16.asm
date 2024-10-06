bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data
;calculati a*a-(e+f) cu a,b,c,d-byte, e,f,g,h-word

a db 8
e dw 5
f dw 2

segment code use32 class=code
start:

;a*a
mov al,[a]
mul byte [a]

;e+f
mov bx,[e]
add bx,[f]

;a*a-(e+f)
sub ax,bx
    
push dword 0 
call [exit] 
