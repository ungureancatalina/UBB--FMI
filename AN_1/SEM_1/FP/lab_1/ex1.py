# 1. Găsiți primul număr prim mai mare decât un număr dat.

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
gasit=0
while gasit==0:
    n+=1
    if Nrprim(n)==1:
        print('Numarul prim mai mare decat n este ',n)
        gasit=1



