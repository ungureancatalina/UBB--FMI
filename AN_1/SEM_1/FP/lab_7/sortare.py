def insertion_sort(lista:list,key=lambda x: x,reverse=False):
    '''
    Functie ce sorteaza lista prin insertion sort
    :param: lista
    :type: list
    :param: key
    :type: str
    :param: reverse
    :type: False
    :return: -
    '''
    n=len(lista)
    if n<=1:
        return lista 
    for i in range(1,n):
        j=i-1
        while j>=0 and key(lista[j])>key(lista[i]):
            lista[j],lista[i]=lista[i],lista[j]
            j-=1
    if reverse:
        lista.reverse()
        
def two_sorts(lista:list,key=lambda x: (x[0],x[1]),reverse=False):
    '''
    Functie ce sorteaza lista prin insertion sort cu 2 parametrii 
    :param: lista
    :type: list
    :param: key
    :type: str
    :param: reverse
    :type: False
    :return: -
    '''
    n=len(lista)
    if n<=1:
        return lista 
    for i in range(1,n):
        j=i-1
        while j>=0 and key(lista[j])>key(lista[i]):
            lista[j],lista[i]=lista[i],lista[j]
            j-=1
    if reverse:
        lista.reverse()
    
def getNextGap(gap):
    gap=(gap*10)//13
    if gap<1:
        return 1
    return gap
    
def comb_sort(lista:list,key=lambda x: x,reverse=False):
    '''
    Functie ce sorteaza lista prin comb sort
    :param: lista
    :type: list
    :param: key
    :type: str
    :param: reverse
    :type: False
    :return: -
    '''
    n=len(lista)
    gap=n
    schimbat=True
    while gap!=1 or schimbat==1:
        gap=getNextGap(gap)
        schimbat=False
        for i in range(0,n-gap):
            if key(lista[i])>key(lista[i+gap]):
                lista[i],lista[i + gap]=lista[i + gap],lista[i]
                schimbat=True
    if reverse:
        lista.reverse()
            
def mergeSort(lista:list):
    '''
    Functie ce sorteaza lista prin merge sort
    :param: lista
    :type: list
    :return: -
    '''
    if len(lista)>1:
        mijloc = len(lista)//2
        st=lista[:mijloc]
        dr=lista[mijloc:]
        mergeSort(st)
        mergeSort(dr)
        i=j=k=0
        while i<len(st) and j<len(dr):
            if st[i]<=dr[j]:
                lista[k]=st[i]
                i+=1
            else:
                lista[k]=dr[j]
                j+=1
            k+=1
        while i<len(st):
            lista[k]=st[i]
            i+=1
            k+=1
        while j<len(dr):
            lista[k]=dr[j]
            j+=1
            k+=1