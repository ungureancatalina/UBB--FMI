#5. Determina numerele prime p1 si p2 gemene imediat superioare numărului natural nenul n dat. Doua numere prime p si q sunt gemene daca q-p = 2.

def Nrprim(nr):
        ok=0
        for i in range(2,nr):
                if nr%i==0:
                        return 0
        return 1
        
            
n=input('Numarul n este ')
n=int(n)
p=n+1
q=n+3
ok=0
while ok==0:
        if Nrprim(p)==1 and Nrprim(q)==1:
                ok=1
        else:
                p+=1
                q+=1
print('Numere sunt ',p,' si ',q)
    