bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data

;Se dau octetul A si cuvantul B. Sa se obtina dublucuvantul C:
;bitii 0-7 ai lui C au valoarea 1
;bitii 8-11 ai lui C coincid cu bitii 4-7 ai lui A
;bitii 12-19 coincid cu bitii 2-9 ai lui B
;bitii 20-23 coincid cu bitii 0-3 ai lui A
;bitii 24-31 coincid cu octetului high din cuvantul B

a db 00101110b
b dw 0110001111010010b
c resw 2 
  
segment code use32 class=code
start:

;calculam in ebx dublucuvantul c
mov ebx, 0

;bitii 0-7 ai lui C au valoarea 1
or bl, 11111111b

;bitii 8-11 ai lui C coincid cu bitii 4-7 ai lui A
mov ax, 0
mov al, [a]
and al, 11110000b
rol ax, 4
or bx, ax

;bitii 12-19 coincid cu bitii 2-9 ai lui B
mov eax, 0
mov ax, [b]
and ax, 0000001111111100b
rol eax,10
or ebx, eax

;bitii 20-23 coincid cu bitii 0-3 ai lui A
mov eax, 0
mov al, [a]
and al, 00001111b
rol eax, 20
or ebx, eax

;bitii 24-31 coincid cu octetului high din cuvantul B
mov eax, 0
mov ax, [b]
and ax,1111111100000000b
rol eax,16
or ebx,eax
mov [c],ebx
  
push dword 0 
call [exit] 
