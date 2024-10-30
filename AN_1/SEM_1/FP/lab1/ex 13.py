# 13. Determinați al n-lea element al șirului 1,2,3,2,2,5,2,2,3,3,3,7,2,2,3,3,3,...obținut din șirul numerelor naturale prin înlocuirea numerelor compuse prin divizorii lor primi, ficere divizor prim d repetându-se de d ori, fără a retine termenii șirului!

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
contor=1
numar=2
rezultat=1

while contor<n:
        if Nrprim(numar)==1:
                contor+=1
                rezultat=numar
                numar+=1
        else:
                for i in range(2,numar-1):
                        if Nrprim(i)==1 and numar%i==0 and contor<n:
                                rezultat=i
                                contor=contor+i
                numar+=1
print('Al n-lea termen este ',rezultat)