# 2. Se da data nașterii (zi/luna/an), determinați vârsta persoanei in zile

data=[]
for i in range(3):
    d=input()
    d=int(d)
    data.append(d)
zile=0
zile+=(data[2]-2023-1)*365
zile+=(data[1]-12-1)*365
