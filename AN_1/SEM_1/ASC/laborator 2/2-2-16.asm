bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data
;calculati x/2+100*(a+b)-3/(c+d)+e*e; a,c-word, b,d-byte, e-doubleword, x-qword - Interpretare cu semn

a dw -10
b db 2
c dw 7
d db -5
e dd 21
x dq 12

segment code use32 class=code
start:


;x/2
mov ebx, [x]
mov ecx, [x+4]
mov al, 2
cbw
cwde
div ecx
mov ecx,eax
    
;a+b
mov bl, [b]
cbw
add bx, [a]

;100*(a+b)
mov ax, bx
mov dl, 100
cbw
mul dx

;x/2+100*(a+b)
add ecx,eax

;(c+d)
mov bl, [d]
cbw
add bx, [c]
 
  
;3/(c+d)
mov ax, 3
div bx
  
;x/2+100*(a+b)-3/(c+d)
mov dx,ax
cwde
sub ecx,edx

;e*e
mov eax, [e]
mul dword [e]

;x/2+100*(a+b)-3/(c+d)+e*e
add eax,ecx
adc edx, 0
  
push dword 0 
call [exit] 
