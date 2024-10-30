#8. Pentru un număr natural n dat găsiți numărul natural maxim m format cu aceleași cifre. Ex. n=3658, m=8653
           
n=input('Numarul n este ')
n=int(n)
m=0
nrcifre=0
v=[0]*200
while n!=0:
    v[nrcifre]=n%10
    n=n//10
    nrcifre+=1
for i in range(nrcifre-1):
    for j in range(i+1,nrcifre):
        if v[i]<v[j]:
            ok=v[i]
            v[i]=v[j]
            v[j]=ok
for i in range(nrcifre):
    m=m*10+v[i]
print('Numarul m este',m)