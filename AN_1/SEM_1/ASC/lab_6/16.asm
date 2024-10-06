; Se dau doua siruri de caractere ordonate alfabetic s1 si s2. Sa se construiasca prin interclasare sirul ordonat s3 care sa contina toate elementele din s1 si s2.

bits 32 
global start
extern exit 
import exit msvcrt.dll 
segment data use32 class=data

s1 db 'a','d','f','m','p','z'
s2 db 'b','c','g','l','n','o'
lung equ $-s1
lung2 equ $-s2
s3 times lung db 0
  
segment code use32 class=code
start:

    mov ecx,lung-1
    mov esi,0
    mov ebx,0
    mov edi,s3
    jecxz sfarsit
    repeta:
        mov eax,0
        mov edx,0
        mov al, [s1+esi]
        mov dl, [s2+ebx]
        cmp al,dl
            ja mai_mare
            stosb
            inc esi
            jmp urmatorul
        mai_mare:
        mov [edi],dl
        inc ebx
        inc edi
        urmatorul:
    loop repeta
    mov ecx,lung2
    cmp esi,ecx
        jle egal
            mov [edi],dl
        egal:
        stosb
    sfarsit:
push dword 0 
call [exit] 
