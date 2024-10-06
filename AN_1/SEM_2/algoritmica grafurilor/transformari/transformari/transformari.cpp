#include <iostream>
using namespace std;

void lista_muchii_matrice_adiacenta(int l[10][10], int n, int m)
{
	int x, y;
	cin >> n >> m;
	for (int i = 1; i <= m; i++)
	{
		cin >> x >> y;
		l[x][y] = 1;
		l[y][x] = 1;
	}
	for (int i = 1; i <= n; i++)
	{
		for (int j = 1; j <= n; j++)
			cout << l[i][j] << " ";
		cout << endl;
	}
}

void lista_muchii_matrice_incidenta(int l[10][10], int n, int m)
{
	int x, y;
	cin >> n >> m;
	for (int i = 1; i <= m; i++)
	{
		cin >> x >> y;
		l[x][i] = 1;
		l[y][i] = 1;
	}
	for (int i = 1; i <= n; i++)
	{
		for (int j = 1; j <= m; j++)
			cout << l[i][j] << " ";
		cout << endl;
	}
}

void lista_muchii_lista_adiacenta(int l[10][10], int n, int m)
{
	int x, y, a[100] = { 0 };
	cin >> n >> m;
	for (int i = 1; i <= m; i++)
	{
		cin >> x >> y;
		a[x]++;
		a[y]++;
		l[x][a[x]] = y;
		l[y][a[y]] = x;
	}
	for (int i = 1; i <= n; i++)
	{
		cout << i << ": ";
		int ok = 0;
		for (int j = 1; j <= n; j++)
			if (l[i][j] != 0)
			{
				cout << l[i][j] << " ";
				ok++;
			}
		if (ok == 0)
			cout << 0;
		cout << endl;
	}
}

void matrice_incidenta_matrice_adiacenta(int a[10][10], int n, int m)
{
	int l[10][10] = { 0 };
	cin >> n >> m;
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= m; j++)
			cin >> a[i][j];
	for (int j = 1; j <= m; j++)
	{
		int x = 0, y = 0;
		for (int i = 1; i <= n; i++)
			if (a[i][j] == 1)
			{
				if (x == 0)
					x = i;
				else
					y = i;
			}
		l[x][y] = 1;
		l[y][x] = 1;
	}
	for (int i = 1; i <= n; i++)
	{
		for (int j = 1; j <= n; j++)
			cout << l[i][j] << " ";
		cout << endl;
	}
}

void matrice_incidenta_lista_adiacenta(int a[10][10], int n, int m)
{
	int l[10][10] = { 0 };
	int k[100] = { 0 };
	cin >> n >> m;
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= m; j++)
			cin >> a[i][j];
	for (int j = 1; j <= m; j++)
	{
		int x = 0, y = 0;
		for (int i = 1; i <= n; i++)
			if (a[i][j] == 1)
			{
				if (x == 0)
					x = i;
				else
					y = i;
			}
		k[x]++;
		k[y]++;
		l[x][k[x]] = y;
		l[y][k[y]] = x;
	}
	for (int i = 1; i <= n; i++)
	{
		cout << i << ": ";
		int ok = 0;
		for (int j = 1; j <= n; j++)
			if (l[i][j] != 0)
			{
				cout << l[i][j] << " ";
				ok++;
			}
		if (ok == 0)
			cout << 0;
		cout << endl;
	}
}

void matrice_incidenta_lista_muchii(int a[10][10], int n, int m)
{
	cin >> n >> m;
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= m; j++)
			cin >> a[i][j];
	for (int j = 1; j <= m; j++)
	{
		int x = 0, y = 0;
		for (int i = 1; i <= n; i++)
			if (a[i][j] == 1)
			{
				if (x == 0)
					x = i;
				else
					y = i;
			}
		cout << x << " " << y << endl;
	}
}

void matrice_adiacenta_matrice_incidenta(int a[10][10], int n, int m)
{
	int l[10][10] = { 0 };
	cin >> n >> m;
	int k = 1;
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= n; j++)
			cin >> a[i][j];
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= n; j++)
			if (i < j && a[i][j] == 1)
			{
				l[i][k] = 1;
				l[j][k] = 1;
				k++;
			}
	for (int i = 1; i <= n; i++)
	{
		for (int j = 1; j <= m; j++)
			cout << l[i][j] << " ";
		cout << endl;
	}
}

void matrice_adiacenta_lista_adiacenta(int a[10][10], int n, int m)
{
	cin >> n >> m;
	int l[10][10] = { 0 };
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= n; j++)
			cin >> a[i][j];
	int k[100] = { 0 };
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= n; j++)
			if (i < j && a[i][j] == 1)
			{
				k[i]++;
				k[j]++;
				l[i][k[i]] = j;
				l[j][k[j]] = i;
			}
	for (int i = 1; i <= n; i++)
	{
		cout << i << ": ";
		int ok = 0;
		for (int j = 1; j <= n; j++)
			if (l[i][j] != 0)
			{
				cout << l[i][j] << " ";
				ok++;
			}
		if (ok == 0)
			cout << 0;
		cout << endl;
	}
}

void matrice_adiacenta_lista_muchii(int a[10][10], int n, int m)
{
	cin >> n >> m;
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= n; j++)
			cin >> a[i][j];
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= n; j++)
			if (i < j)
				if (a[i][j] == 1)
					cout << i << " " << j << endl;
}

int main()
{
	int n1=0, m1=0;
	int l1[10][10]={0};
	cout << "lista muchii-> matrice adiacenta" << endl;
	//lista_muchii_matrice_adiacenta(l1, n1, m1);
	cout << endl;

	int n2=0, m2=0;
	int l2[10][10] = { 0 };
	cout << "lista muchii-> matrice incidenta" << endl;
	//lista_muchii_matrice_incidenta(l2, n2, m2);
	cout << endl;

	int n3=0, m3=0;
	int l3[10][10] = { 0 };
	cout << "lista muchii-> lista adiacenta" << endl;
	//lista_muchii_lista_adiacenta(l3, n3, m3);
	cout << endl;

	int a1[10][10] = { 0 };
	int n4 = 0, m4 = 0;
	cout << "matrice incidenta-> matrice adiacenta" << endl;
	//matrice_incidenta_matrice_adiacenta(a1, n4, m4);
	cout << endl;

	int a2[10][10] = { 0 };
	int n5 = 0, m5 = 0;
	cout << "matrice incidenta-> lista muchii" << endl;
	//matrice_incidenta_lista_adiacenta(a2, n5, m5);
	cout << endl;

	int a3[10][10] = { 0 };
	int n6 = 0, m6 = 0;
	cout << "matrice incidenta-> lista adiacenta" << endl;
	//matrice_incidenta_lista_muchii(a3, n6, m6);
	cout << endl;

	int a4[10][10] = { 0 };
	int n7 = 0, m7 = 0;
	cout << "matrice adiacenta-> matrice incidenta" << endl;
	//matrice_adiacenta_matrice_incidenta(a4, n7, m7);
	cout << endl;

	int a5[10][10] = { 0 };
	int n8 = 0, m8 = 0;
	cout << "matrice adiacenta-> lista muchii" << endl;
	//matrice_adiacenta_lista_muchii(a5, n8, m8);
	cout << endl;

	int a6[10][10] = { 0 };
	int n9 = 0, m9 = 0;
	cout << "matrice adiacenta-> lista adiacenta" << endl;
	//matrice_adiacenta_lista_adiacenta(a6, n9, m9);
	cout << endl;

}

//lista muchii
//5 6
//1 2
//1 3
//1 5
//2 4
//3 4
//4 5

//matrice incidenta
//5 6
//1 1 1 0 0 0
//1 0 0 1 0 0
//0 1 0 0 1 0
//0 0 0 1 1 1
//0 0 1 0 0 1

//matrice adiacenta
//5 6
//0 1 1 0 1
//1 0 0 1 0
//1 0 0 1 0
//0 1 1 0 1
//1 0 0 1 0