# 14. Generați cel mai mic număr perfect mai mare decât un număr dat. Un număr este perfect daca este egal cu suma divizorilor proprii. Ex. 6 este un număr perfect (6=1+2+3).

def perfect(nr):
        suma=1
        nrcopie=nr
        div=2
        while nrcopie!=1:
                if nrcopie%div==0:
                        while nrcopie%div==0:
                                nrcopie=nrcopie//div
                        suma+=div
                div+=1
        if suma==nr:
                return 1
        else:
                return 0
                
        
n=input('Numarul dat este ')
n=int(n)
numar=n+1
ok=0
while ok==0:
        if perfect(numar)==1:
                ok=1
        else:
                numar+=1
print('Cel mai mic numar perfect mai mare decat numarul n este ',numar)


