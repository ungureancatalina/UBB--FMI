#include <stdio.h>
#include <iostream>
#include <fstream>
#define inf 10000000
using namespace std;
ifstream f("Text.txt");

/*
2. Fie un graf reprezentat sub o anumita forma (graful este citit dintr-un fisier). Sa se rezolve:
a. sa se determine nodurile izolate dintr-un graf.
b. sa se determine daca graful este regular.
c. pentru un graf reprezentat cu matricea de adiacenta sa se determine matricea distantelor.
d. pentru un graf reprezentat cu o matrice de adiacenta sa se determine daca este conex.
*/

void nod_izolat(int n,int m,int a[100][100])
{
    int ok = 0;
    for (int i = 1; i <= n; i++)
    {
        ok = 0;
        for (int j = 1; j <= n; j++)
            if (a[i][j] == 1)
                ok++;
        if (ok == 0)
            cout << i << " ";
    }
}

int graf_regular(int n, int m, int a[100][100])
{
    int v[100] = { 0 }, ok = 1;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            if (a[i][j] == 1)
                v[i]++;
    for (int i = 1; i < n; i++)
        if (v[i] != v[i + 1])
            ok = 0;
    return ok;
}

void matricea_distantelor(int n, int m, int a[100][100],int d[100][100])
{
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
             if (a[i][j] == 1)
                 d[i][j] = 1;
             else
                 d[i][j] = inf;
    for (int k = 1; k <= n; k++) 
        for (int i = 1; i <= n; i++) 
            for (int j = 1; j <= n; j++) 
                if (d[i][k] != inf && d[k][j] != inf) 
                    if (d[i][j] > d[i][k] + d[k][j]) 
                        d[i][j] = d[i][k] + d[k][j];
    for (int i = 1; i <= n; i++)
    {
        d[i][i] = 0;
        for (int j = 1; j <= n; j++)
            if (d[i][j] == inf)
                cout << "inf" << " ";
            else
                cout << a[i][j]<<" ";
        cout << endl;
    }
}

void dfs(int n,int a[100][100], int x, int vizitat[100])
{
    vizitat[x] = 1;
    for (int i = 1; i <= n; i++) {
        if (a[x][i] == 1 && vizitat[i] == 0)
            dfs(n,a,i,vizitat);
    }
}

int graf_conex(int n, int a[100][100])
{
    int ok = 1, vizitat[100] = { 0 };
    dfs(n,a,1,vizitat);
    for (int i = 1; i <= n; i++)
        if (vizitat[i] == 0)
            ok = 0;
    return ok;
}

int main()
{
    int x, y;
    int n, m, a[100][100] = { 0 }, d[100][100] = { 0 };
    f >> n >> m;
    for (int i = 1; i <= m; i++)
    {
        f >> x >> y;
        a[x][y] = 1;
        a[y][x] = 1;
    }
    cout << "Nodurile izolate sunt: ";
    nod_izolat(n, m, a);
    cout << endl;
    int ok = graf_regular(n, m, a);
    if (ok == 0)
        cout << "Nu este graf regulat";
    else
        cout << "Este graf regulat";
    cout << endl;
    cout << "Matricea drumurilor este:" << endl;
    matricea_distantelor(n,m,a,d);
    ok = graf_conex(n, a);
    if (ok == 0)
        cout << "Nu este graf conex";
    else
        cout << "Este graf conex";
}

