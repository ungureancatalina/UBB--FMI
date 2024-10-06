bits 32 
global _ex

segment data use32 class=data
  
segment code use32 class=code

_ex:
    ;creare cadru de stiva pentru programul apelat
    push ebp
    mov ebp,esp
    
    mov eax,[ebp+8]
    mov ebx,'0'
    sub eax, ebx

    ;refacem cadrul de stiva pentru programul apelant
    mov esp, ebp
    pop ebp
    
    ret
