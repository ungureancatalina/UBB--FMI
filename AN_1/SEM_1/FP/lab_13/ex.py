
def bkt_recursiv(q, x, k, a : list):
    if q > k:
        return
    elif q == k:
        print(x)
    else:
        if q >= 1:
            for i in range(len(a)):
                if a[i] == x[q - 1]:
                    inceput = i
                    break
            for i in range(inceput + 1, len(a)):
                x.append(a[i])
                if corect(x):
                    bkt_recursiv(q + 1, x, k, a)
                x.pop()
        else:
            for i in range(len(a)):
                x.append(a[i])
                if corect(x):
                    bkt_recursiv(q + 1, x, k, a)
                x.pop()
                
def corect(x:list):
    if len(x)>=1:
        for i in range(len(x)-1):
            if x[i]>=x[i+1]:
                return False
    return True

n=7
a=[1,2,6,7,3,9,5]
for k in range(2,n):
    x=[]
    bkt_recursiv(0, x, k, a)
