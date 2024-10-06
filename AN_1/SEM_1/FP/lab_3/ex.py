# 2. Contine cel mult trei valori distincte
# 4. Contine doar numere prime.

def print_meniu():
    print('Optiunea 1: citirea unei liste')
    print('Optiunea 2: gasirea secventei de lungime maxima ce contine cel mult 3 valori diferite')
    print('Optiunea 3: gasirea secventei de lungime maxima ce contine doar numere prime')
    print('Optiunea 4: gasirea secventei de lungime maxima ce respecta x[i] < x[i+1] < ... < x[i+p]')
    print('Optiunea 5: iesirea din aplicatie')
    
def citire_lista():
    lst=input('Introduceti lista ')
    lst=lst.split(',')
    lst=[numar.strip() for numar in lst]
    lst=[int(numar) for numar in lst]
    return lst

def Nrprim(nr):
        ok=0
        for i in range(2,nr):
                if nr%i==0:
                        return 0
        if(nr==1):
            return 0
        return 1
    
def proprietatea2(lst):
    secventa=[]
    lungime_maxima=0
    pozitie_x=0
    poz_initiala=0
    x=lst[0]
    poz_finala=0
    i=1
    while i<len(lst) and lst[i]==x:
        i+=1
    if i==len(lst):
        i-=1
    y=lst[i]
    pozitie_y=i
    while i<len(lst) and lst[i]==y:
        i+=1
    if i==len(lst):
        i-=1
    z=lst[i]
    pozitie_z=i
    while i<len(lst) and lst[i]==z:
        i+=1
    poz_finala=i 
    lungime_maxima=poz_finala-poz_initiala
    if i==len(lst):
        poz_initiala=0
        poz_finala=len(lst)
    else:
        while i<len(lst):
            nr=lst[i]
            pozitie_x=pozitie_y
            pozitie_y=pozitie_z
            pozitie_z=i
            while i<len(lst) and lst[i]==nr:
                i+=1
            if(i-pozitie_x>lungime_maxima):
                lungime_maxima=i-pozitie_x
                poz_finala=i
                poz_initiala=pozitie_x
    for j in range(poz_initiala,poz_finala):
        secventa.append(lst[j]) 
    return secventa

def proprietatea4(lst):
    secventa=[]
    lungime_maxima=0
    pozitie_initiala=0
    for i in range(len(lst)):
        j=i
        while i<len(lst) and Nrprim(lst[i])==1:
            i+=1
        if i-j>lungime_maxima:
            lungime_maxima=i-j
            pozitie_initiala=j
    for i in range(pozitie_initiala,pozitie_initiala+lungime_maxima):
         secventa.append(lst[i]) 
    if lungime_maxima==0:
        print('Nu exista numere prime')
    else:
        return secventa
        
def verificare_secventa_crescatoare(lst):
    secventa=[]
    lungime_maxima=0
    pozitie_initiala=0
    for i in range(len(lst)):
        j=i
        while i+1<len(lst) and lst[i]<lst[i+1]:
            i+=1
        i+=1
        if i-j>lungime_maxima:
            lungime_maxima=i-j
            pozitie_initiala=j
    for i in range(pozitie_initiala,pozitie_initiala+lungime_maxima):
         secventa.append(lst[i]) 
    if lungime_maxima==0:
        print('Nu exista secventa de nr crescatoare')
    else:
        return secventa

def test_p4():
    lis=[1,2,3,4,3,5]
    assert (verificare_secventa_crescatoare(lis)==[1, 2, 3, 4])  
    lis=[1,2,2,4,5]
    assert (verificare_secventa_crescatoare(lis)==[2, 4, 5])    
    lis=[]
    assert (len(lis)==0)
    lis=[5,4,3,2]
    assert (verificare_secventa_crescatoare(lis)==[5]) 

lista=[]
while True:
    print_meniu()
    test_p4()
    option=input('Introduceti optiunea dorita: ')
    option=option.strip()
    option=int(option)
    match option:
        case 1:
            lista=citire_lista()
            print(lista)
        case 2:
            proprietatea2(lista)
            print(proprietatea2(lista))
        case 3:
            proprietatea4(lista)
            print(proprietatea4(lista))
        case 4:
            verificare_secventa_crescatoare(lista)
            print(verificare_secventa_crescatoare(lista))
        case 5:
            break
        case _:
            print('Nu exista aceasta optiune')
            break


    
