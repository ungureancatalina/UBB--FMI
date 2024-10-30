# 1. Dându-se numărul natural n, determina numerele prime p1 si p2 astfel ca n = p1 + p2 (verificarea ipotezei lui Goldbach). Pentru ce fel de n exista soluție?

def Nrprim(nr):
        ok=0
        for i in range(2,nr):
                if nr%i==0:
                        ok+=1
        if ok==0:
                return 1
        else:
                return 0
            
n=input('Numarul n este ')
n=int(n)
p1=0
p2=0
if n%2==0 and n>2:
    for i in range(2,n//2+1):
        if Nrprim(i)==1 and Nrprim(n-i)==1:
            p1=i
            p2=n-i
else:
    if n%2==1:
        if Nrprim(n-2)==1:
            p1=2
            p2=n-2
if p1==0 or p2==0:
    print('Nu exista p1 si p2') 
else:
    print('Numerele sunt ',p1, ' si ', p2)            


