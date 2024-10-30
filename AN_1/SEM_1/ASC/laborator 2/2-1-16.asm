bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data
;calculati x/2+100*(a+b)-3/(c+d)+e*e; a,c-word, b,d-byte, e-doubleword, x-qword - Interpretare fara semn

a dw 10
b db 2
c dw 7
d db 5
e dd 21
x dq 12

segment code use32 class=code
start:

;x/2
mov eax, [x]
mov edx, [x+4]
mov ecx,0
mov cl, 2
div ecx
mov ecx,eax
    
;a+b
mov bx, 0
mov bl, [b]
add bx, [a]

;100*(a+b)
mov ax, bx
mov dx, 0
mov dl, 100
mul dx

;x/2+100*(a+b)
add ecx,eax

;(c+d)
mov bx,0
mov bl, [d]
add bx, [c]
 
  
;3/(c+d)
mov ax, 3
mov dx,0
div bx
  
;x/2+100*(a+b)-3/(c+d)
mov edx, 0
mov dx,ax
sub ecx,edx

;e*e
mov eax, [e]
mul dword [e]

;x/2+100*(a+b)-3/(c+d)+e*e
add eax,ecx
adc edx, 0
  
push dword 0 
call [exit] 
