# 3. Determina o data calendaristica (sub forma an, luna, zi) pornind de la doua numere întregi care reprezintă anul si numărul de ordine al zilei in anul respectiv.

an=input('Anul dat este ')
an=int(an)
nr_zi=input('Numarul de ordine al zilei este ')
nr_zi=int(nr_zi)
luna=[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
bisect=0
suma_zile=0
luna_crt=0
zi=0
if an%4==0:
    bisect=1
if bisect==1:
    luna[1]+=1
for i in range(12):
    if suma_zile+luna[i]<nr_zi:
        suma_zile+=luna[i]
        luna_crt+=1
    else:
        zi=nr_zi-suma_zile-1
print('Data este ',an,'/',luna_crt,'/',zi)
    
    
