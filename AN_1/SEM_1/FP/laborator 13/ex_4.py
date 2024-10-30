# Se dă o listă de numere întregi a1,...an, determinați toate sub-secvențele (ordinea elementelor este menținută) strict crescătoare.

def corect(x:list):
    """
    Verifica daca numerele din subsirul x sunt toate in ordine strict crescatoare
    :param x: subsirul
    :type x: list
    :return: 0 sau 1
    :rtpe: int
    """
    if len(x)>=1:
        for i in range(len(x)-1):
            if x[i]>=x[i+1]:
                return False
    return True
    
def solutie(x:list,k:int,lista:list):
    """
    Verifica daca subsirul x are lungimea egala cu k
    :param x: subsirul
    :type x: list
    :param k: lungimea ceruta
    :type k: int
    :param lista: lista de subsiruri
    :type lista: int
    :return: 0 sau 1
    :rtpe: int
    """
    for sir in lista:
        if sir==x:
            return False
    if len(x)!=k:
        return False
    return True

def printare(x:list):
    """
    Afiseaza subsirul x
    :param x: subsirul
    :type x: list
    :return: -
    """ 
    print(x)

def back_recursiv(n:int,a:list,k:int,q:int,lista:list):
    """
    Afla toate subsirurile strict crescatoare de lungime k
    :param n: lungimea sirului initial 
    :type n: int
    :param a: sirul initial
    :type a: list
    :param k: lungimea unui subsir
    :type k: int
    :param q: pozitia de la care incepe subsirul
    :type q: int
    :param lista: lista de subsiruri
    :type lista: int
    :param : 
    :type :
    """
    x=[]
    for i in range(q,n):
        x.append(a[i])
        if corect(x):
            if solutie(x,k,lista):
                printare(x)
                lista.append(x)
                x=[]
            back_recursiv(n,a,k,q+1,lista)
            
    
# def back_iterativ1(n:int,a:list):
#     """
#     Afla toate subsirurile strict crescatoare de lungime k
#     :param n: lungimea sirului initial 
#     :type n: int
#     :param a: sirul initial
#     :type a: list
#     """
#     for i in range(n):
#         x=[]
#         x.append(a[i])
#         for j in range(i+1,n):
#             if a[j]>x[-1]: 
#                 x.append(a[j])
#                 printare(x)
#             else:
#                 break  
            
def corect2(k,x,a):
    """
    Verifica daca numerul adaugat in subsirul x pastreaza cerinta devordine strict crescatoare
    :param k: pozitia curenta
    :type k: int
    :param x: subsirul
    :type x: list
    :param a: sirul initial
    :type a: list
    :return: 0 sau 1
    :rtpe: int
    """
    if k==0:
        return True
    for i in range(k):
        if x[k]<=x[i] or a[x[k]]<=a[x[i]]:
            return False
    return True  
            
def back_iterativ(lung:int, a:list):
    """
    Afla toate subsirurile strict crescatoare de lungime k
    :param lung: lungimea unui subsir
    :type lung: int
    :param a: sirul initial
    :type a: list
    """
    x=[-1]
    k=0
    while k>-1:
        ok=1
        corect=False
        while ok==1 and (not corect):
            if x[k]<len(a)-1:
                x[k]+=1
            else:
                ok=0
            corect=corect2(k,x,a)
        if ok==1:
            if len(x)==lung:
                aux=[]
                for i in range(len(x)):
                    aux.append(a[x[i]])
                print(aux)
            else:
                k+=1
                x.append(-1)
        else:
            k-=1
            x.pop()
            
def start():
    """
    Citeste de la tastatura n si sirul de n numere si determina toate sub-secventele strict crescatoare
    """
    # print('Numarul n este: ')
    # n=int(input())
    # a=[]
    # print('Sirul este: ')
    # for index in range(n):
    #     nr=int(input())
    #     a.append(nr)
    n=7
    a=[1,2,6,7,3,9,5]
    lista=[]
    print('recursiv')
    for k in range(2,n):
        back_recursiv(n,a,k,0,lista)
    lista=[]
    print('iterativ')
    # back_iterativ1(n,a)
    for k in range(2,n):
        back_iterativ(k,a)
start()

