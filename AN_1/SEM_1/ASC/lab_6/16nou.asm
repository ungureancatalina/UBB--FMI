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
lung1 equ lung-lung2
s3 times lung db 0
  
segment code use32 class=code
start:

mov ecx,lung
mov esi,0                 ;se pune in esi s1
mov ebx,0                 ;se pune in ebx s2
mov edi,s3                ;se pune in edi s3
jecxz sfarsit
    repeta:
        cmp esi,lung1
            je ramas_s2   ;daca s1 are toate elementele deja in s3, se completeaza doar cu s2
        cmp ebx,lung2
            je ramas_s1   ;daca s2 are toate elementele deja in s3, se completeaza doar cu s1
            
        mov eax,0
        mov edx,0
        mov al, [s1+esi]  ;al primeste element din s1
        mov dl, [s2+ebx]  ;dl primeste element din s2
        
        cmp al,dl
            ja mai_mare   
            stosb         ;daca al<=dl in edi se pune elementul din al
            inc esi
            jmp repeta
            
        mai_mare:         
        mov [edi],dl      ;daca al>dl in edi se pune elementul din dl
        inc ebx
        inc edi
        jmp repeta        ;se repeta pana cand s1 sau s2 este gata
        
        ramas_s2:
            mov ecx,lung2 ;ce a ramas din s2 se adauga
            sub ecx,ebx
            repeta2:
                mov eax,0
                mov al, [s2+ebx]
                stosb
                inc ebx
            loop repeta2
            jmp sfarsit
            
        ramas_s1:
            mov ecx,lung1  ;ce a ramas din s1 se adauga
            sub ecx,esi
            repeta3:
                mov eax,0
                mov al, [s1+esi]
                stosb
                inc esi
            loop repeta3  
            
        jmp sfarsit
    loop repeta
sfarsit:

push dword 0 
call [exit] 
