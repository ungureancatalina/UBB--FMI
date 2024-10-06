#  Găsește cel mai mic număr m din șirul lui Fibonacci definit de f[0]=f[1]=1, f[n]=f[n-1]+f[n-2], pentru n>2, mai mare decât numărul natural n dat, deci exista k astfel ca f[k]=m si m>n.

n=input('Numarul n este ')
n=int(n)
m=0
nr1=1
nr2=1
ok=0
while nr1!=n:
    ok=nr1+nr2
    nr1=nr2
    nr2=ok
m=nr2
print('Numerul m este ',m)
    