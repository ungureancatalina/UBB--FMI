bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data
;calculati 4*4

a db 4
b db 4

segment code use32 class=code
start:

;4*4
mov al, [a]
mul byte [b]
    
push dword 0 
call [exit] 
