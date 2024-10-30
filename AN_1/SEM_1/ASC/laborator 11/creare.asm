bits 32 

global creare

;definire procedura
creare: 
    ;in al va rezulta numarul corespunzator reprezentarii binare
    mov eax,[esp+4]
    mov bl, '0'
    sub al,bl
    
    ret
