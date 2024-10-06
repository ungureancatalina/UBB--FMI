#  Fie n un număr natural dat. Calculați produsul p al tuturor factorilor proprii ai lui n

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
prod=1
for i in range(2,n):
    if Nrprim(i)==1 and n%i==0:
        prod*=i
if prod==1:
    print('Nu exista')
else:
    print('Produsul p este ',prod)