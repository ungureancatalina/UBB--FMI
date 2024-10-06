#include <stdio.h>
#include <iostream>
#include <fstream>
using namespace std;
ifstream f("Text.txt");

/* 
1. Fie un fisier ce contine un graf neorientat reprezentat sub forma: prima linie contine numarul nodurilor iar urmatoarele randuri 
muchiile grafului. Sa se scrie un program in C/C++ care sa citeasca fisierul si sa reprezinte/stocheze un graf folosind matricea de adiacenta, 
lista de adiacenta si matricea de incidenta. Sa se converteasca un graf dintr-o forma de reprezentare in alta.
Fisier -> matrice de adiacenta -> lista adiacenta -> matrice de incidenta -> lista adiacenta -> matrice de adiacenta -> lista.
exemplu fisier
in.txt
4 4
1 2
3 4
2 3
4 2

*/

void matrice_adiacenta()
{
    int x, y;
    int n, m, a[100][100] = { 0 };
    f >> n >> m;
    for (int i = 1; i <= m; i++)
    {
        f >> x >> y;
        a[x][y] = 1;
        a[y][x] = 1;
    }
    cout << "Matricea de adiacenta:" << endl;
    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= n; j++)
            cout << a[i][j];
        cout << endl;
    }
    f.close();
}

void lista_adiacenta()
{
    f.open("Text.txt");
    int x, y;
    int n, m, a[100][100] = { 0 }, l[100] = {0};
    f >> n >> m;
    for (int i = 1; i <= m; i++)
    {
        f >> x >> y;
        l[x]++;
        l[y]++;
        a[x][l[x]] = y;
        a[y][l[y]] = x;

    }
    cout << "Lista de adiacenta:" << endl;
    for (int i = 1; i <= n; i++)
    {
        cout << i<<": ";
        int ok = 0;
        for (int j = 0; j < n; j++)
            if (a[i][j] != 0)
            {
                cout << a[i][j] << " ";
                ok++;
            }
        if (ok == 0)
            cout << 0;
        cout << endl;
    }
    f.close();
}

void matrice_incidenta()
{
    f.open("Text.txt");
    int x, y;
    int n, m, a[100][100] = { 0 };
    f >> n >> m;
    for (int i = 1; i <= m; i++)
    {
        f >> x >> y;
        a[x][i] = 1;
        a[y][i] = 1;
    }
    cout << "Matricea de incidenta:" << endl;
    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= m; j++)
            cout << a[i][j];
        cout << endl;
    }
    f.close();
}

int main()
{
    matrice_adiacenta();
    cout << endl;
    lista_adiacenta();
    cout << endl;
    matrice_incidenta();
}

