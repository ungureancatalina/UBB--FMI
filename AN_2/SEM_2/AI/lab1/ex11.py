#Considerându-se o matrice cu n x m elemente binare (0 sau 1), să se înlocuiască cu 1 toate
#aparițiile elementelor egale cu 0 care sunt complet înconjurate de 1.
#De ex. matricea
#[[1,1,1,1,0,0,1,1,0,1],
#[1,0,0,1,1,0,1,1,1,1],
#[1,0,0,1,1,1,1,1,1,1],
#[1,1,1,1,0,0,1,1,0,1],
#[1,0,0,1,1,0,1,1,0,0],
#[1,1,0,1,1,0,0,1,0,1],
#[1,1,1,0,1,0,1,0,0,1],
#[1,1,1,0,1,1,1,1,1,1]]
#*devine *
#[[1,1,1,1,0,0,1,1,0,1],
#[1,1,1,1,1,0,1,1,1,1],
#[1,1,1,1,1,1,1,1,1,1],
#[1,1,1,1,1,1,1,1,0,1],
#[1,1,1,1,1,1,1,1,0,0],
#[1,1,1,1,1,1,1,1,0,1],
#[1,1,1,0,1,1,1,0,0,1],
#[1,1,1,0,1,1,1,1,1,1]]

def inlocuire_0_cu_1(matr):
    n=len(matr)
    m=len(matr[0])
    copie=matr.copy()
    for i in range(1,n-1):
        for j in range(1,m-1):
            if matr[i][j]==0 and matr[i - 1][j] == 1 and matr[i + 1][j] == 1 and matr[i][j - 1] == 1 and matr[i][j + 1] == 1:
                copie[i][j] = 1
    return copie

def test():
    m=[[1,1,1,1,0,0,1,1,0,1],[1,0,0,1,1,0,1,1,1,1],[1,0,0,1,1,1,1,1,1,1],[1,1,1,1,0,0,1,1,0,1],[1,0,0,1,1,0,1,1,0,0],[1,1,0,1,1,0,0,1,0,1],[1,1,1,0,1,0,1,0,0,1],[1,1,1,0,1,1,1,1,1,1]]
    rez=inlocuire_0_cu_1(m)
    for i in range(0,len(rez)):
        print(rez[i])


test()