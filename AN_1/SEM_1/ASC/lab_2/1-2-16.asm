bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data
;calculati (d-a)-(a-c)-d cu a - byte, b - word, c - double word, d - qword - Interpretare cu semn

a db 4
c dd -12
d dq 19

segment code use32 class=code
start:

;(d-a)
mov ebx, [d]
mov ecx, [d+4]
mov al, [a]
cbw
cwde
cdq
sub ebx,eax
sbb ecx,edx

;(a-c)
mov al,[a]
cbw
cwde
sub eax,[c]
cdq

;(d-a)-(a-c)
sub ebx,eax
sbb ecx,edx

;(d-a)-(a-c)-d
mov eax,[d]
mov edx, [d+4]
sub ebx,eax
sbb ecx,edx

push dword 0 
call [exit] 
