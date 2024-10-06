#9. Palindromul unui număr este numărul obținut prin scrierea cifrelor in ordine inversa (Ex. palindrom(237) = 732). Pentru un n dat calculați palindromul sau.
           
n=input('Numarul n este ')
n=int(n)
m=0
while n!=0:
    m=n%10+m*10
    n=n//10
print('Numarul palindrom este',m)
