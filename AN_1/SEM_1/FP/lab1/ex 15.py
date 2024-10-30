# 15.Găsiți cel mai mare număr prim mai mic decât un număr dat. Daca nu exista un astfel de număr, tipăriți un mesaj.

def Nrprim(nr):
        ok=0
        for i in range(2,nr):
                if nr%i==0:
                        ok+=1
        if ok==0:
                return 1
        else:
                return 0

        
n=input('Numarul dat este ')
n=int(n)
numar=n-1
ok=0
while ok