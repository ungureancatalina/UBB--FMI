nasm -fobj main.asm
nasm -fobj creare.asm
alink main.obj creare.obj -oPE -subsys console -entry start

