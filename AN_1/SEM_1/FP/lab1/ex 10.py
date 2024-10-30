#10. Pentru un număr natural n dat găsiți numărul natural minim m format cu aceleași cifre. Ex. n=3658, m=3568.
          
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
        if v[i]>v[j] and (v[i]!=0 or v[j]!=0):
            ok=v[i]
            v[i]=v[j]
            v[j]=ok
for i in range(nrcifre):
    m=m*10+v[i]
print('Numarul m este',m)