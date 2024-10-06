#11. . Numerele n1 si n2 au proprietatea P daca scrierile lor in baza 10 conțin aceleași cifre (ex. 2113 si 323121). Determinați daca doua numere naturale au proprietatea P.
 
n1=input('Numarul n1 este ')
n1=int(n1)
n2=input('Numarul n1 este ')
n2=int(n2)
cifre1= [0]*200
cifre2= [0]*200
ok=1
while n1!=0:
    cifre1[n1%10]+=1
    n1=n1//10
while n2!=0:
    cifre2[n2%10]+=1
    n2=n2//10
for i in range(10):
    if (cifre1[i]==0 and cifre2[i]!=0) or (cifre2[i]==0 and cifre1[i]!=0):
            ok=0
if ok==0:
    print('Nu au proprietatea P')
else:
    print('Au proprietatea P')
    