#include <iostream>
#include <fstream>
#include <string>
#include <cstring>
#include <queue>
using namespace std;
#define inf 100000000

// Implementa?i algoritmul lui Moore pentru un graf orientat neponderat (algoritm bazat pe Breath-first search, vezi cursul 2).
// Datele sunt citete din fisierul graf.txt. Primul rând din graf.txt con?ine numãrul vârfurilor, iar urmãtoarele rânduri con?in muchiile grafului. 
// Programul trebuie sã afiseze lan?ul cel mai scurt dintr-un vârf (vârful sursa poate fi citit de la tastatura).

void Moore(int a[100][100], int n, int s,int len[100], int parinte[100])
{
	for (int i = 1; i <= n; i++)
		len[i] = inf;
	len[s] = 0;
	parinte[s] = 0;
	queue<int>coada;
	coada.push(s);
	while (!coada.empty()) 
	{
		int x = coada.front();
		coada.pop();
		for (int y = 1; y <= n; y++) 
			if (a[x][y]==1 && (len[y]==inf || len[y]==0))
			{
				parinte[y] = x;
				len[y] = len[x] + 1;
				coada.push(y);
			}
	}
}

void Drum_Moore(int v,int len[100],int parinte[100])
{
	int dim = len[v];
	int drum[100];
	drum[dim] = v;
	while (dim!=0) 
	{
		dim--;
		drum[dim] = parinte[drum[dim+1]];
	}
	for (int i = 0; i < len[v]; i++) {
		cout << drum[i] << " ";
	}
	cout << drum[len[v]];
}

void pb1() 
{
	ifstream f1("graf1.txt");
	int n, x, y, s;
	int len[100], parinte[100];
	int a[100][100] = { 0 }, l[100] = { 0 };
	f1 >> n;
	while (f1 >> x >> y)
	{
		a[x][y] = 1;
	}
	f1.close();
	cout << "Introduceti varful sursa: ";
	cin >> s;
	Moore(a, n, s, len, parinte);
	for (int i = 1; i <= n; i++)
	{ 
		if(len[i]!=inf)
		{
			cout << "Lungimea drumului minim intre " << s << " si " << i << " este: " << len[i]<<" iar drumul este: ";
			Drum_Moore(i,len,parinte);
			cout << endl;
		}
		if (len[i] == inf) 
			cout << "Nu exista drum intre " << s << " si " << i << endl;
	}
}

// Sa se determine închiderea transitivã a unui graf orientat. (Închiderea tranzitivã poate fi reprezentatã ca matricea care descrie, pentru fiecare vârf în parte, care sunt
// vârfurile accesibile din acest vârf. Matricea inchiderii tranzitive aratã unde se poate ajunge din fiecare vârf.) ex. figura inchidere_tranzitiva.png - pentru graful de sus
// se construieste matricea de jos care arata inchiderea tranzitiva.

void pb2() 
{
	ifstream f2("graf2.txt");
	int n, x, y, s;
	int a[100][100] = { 0 };
	f2>>n;
	while (f2 >> x >> y)
	{
		a[x][y] = 1;
	}
	f2.close();
	int t[100][100] = { 0 };
	for (int i = 1; i <= n; i++) 
		for (int j = 1; j <= n; j++)
			if (a[i][j] == 1)
				t[i][j] = 1;
			else
				t[i][j] = 0;
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= n; j++)
			for (int k = 1; k <= n; k++)
				if ((t[i][j] == 0) && (t[i][k] == 1) && (t[k][j] == 1))
					t[i][j] = 1;
	cout << endl << "Inchiderea tranzitiva a grafului dat este:" << endl;
	for (int i = 1; i <= n; i++) 
	{
		for (int j = 1; j <= n; j++)
			cout << t[i][j] << " ";
		cout << endl;
	}
}

// Sã se scrie un program care gãse?te o solu?ie pentru unul din urmãtoarele labirinturi: labirint_1.txt, labirint_2.txt, labirint_cuvinte.txt. Pentru labirintul 1 si 2 
// punctul de pornire este indicat de litera S ?i punctul de oprire este indicat de litera F, spre deosebire de labirintul 1 ?i 2, labirintul 3 nu are o solu?ie unicã.

void bordare(int a[1000][1000], int n, int m)
{
	for (int i = 0; i <= n + 1; i++)
		a[i][0] = a[i][m + 1] = -1;
	for (int i = 0; i <= m + 1; i++)
		a[0][i] = a[n + 1][i] = -1;
}

void lee(int x, int y, int xf, int yf, int a[1000][1000])
{
	if (x == xf && y == yf) {
		return;
	}
	if (a[x + 1][y] == 0 || a[x][y] + 1 < a[x + 1][y]) {
		a[x + 1][y] = a[x][y] + 1;
		lee(x + 1, y, xf, yf, a);
	}
	if (a[x - 1][y] == 0 || a[x][y] + 1 < a[x - 1][y]) {
		a[x - 1][y] = a[x][y] + 1;
		lee(x - 1, y, xf, yf, a);
	}
	if (a[x][y + 1] == 0 || a[x][y] + 1 < a[x][y + 1]) {
		a[x][y + 1] = a[x][y] + 1;
		lee(x, y + 1, xf, yf, a);
	}
	if (a[x][y - 1] == 0 || a[x][y] + 1 < a[x][y - 1]) {
		a[x][y - 1] = a[x][y] + 1;
		lee(x, y - 1, xf, yf, a);
	}
}

void pb3()
{
	ifstream f("labirint_1.txt");
	ofstream g("labirint1_rezolvat.txt");
	int a[1000][1000];
	int n = 0, m, xs, ys, xf, yf;
	char s[1000];
	while (f.getline(s, 1005)) 
	{
		n++;
		m = strlen(s);
		for (int i = 0; i < strlen(s); i++) 
		{
			if (s[i] == '1')
				a[n][i + 1] = -1;
			else if (s[i] == ' ')
				a[n][i + 1] = 0;
			else if (s[i] == 'S') 
			{
				a[n][i + 1] = 1;
				xs = n;
				ys = i + 1;
			}
			else 
			{
				xf = n;
				yf = i + 1;
			}
		}
	}
	bordare(a,n,m);
	lee(xs, ys,xf,yf,a);
	cout << "Lungimea drumului: " << a[xf][yf] << '\n';
	int x, y;
	x = xf;
	y = yf;
	while (x != xs || y != ys) 
	{
		if (a[x - 1][y] + 1 == a[x][y])
		{
			a[x][y] = -2;
			x = x - 1;
		}
		else if (a[x + 1][y] + 1 == a[x][y]) 
		{
			a[x][y] = -2;
			x++;
		}
		else if (a[x][y - 1] + 1 == a[x][y]) 
		{
			a[x][y] = -2;
			y--;
		}
		else if (a[x][y + 1] + 1 == a[x][y]) 
		{
			a[x][y] = -2;
			y++;
		}
	}
	a[xf][yf] = -3;
	for (int i = 1; i <= n; i++) 
	{
		for (int j = 1; j <= m; j++) 
		{
			if (a[i][j] == 1)
				g << 'S';
			else if (a[i][j] == -2)
				g << 'X';
			else if (a[i][j] == -3)
				g << 'F';
			else g << ' ';
		}
		g << '\n';
	}
}


// Pentru un graf dat sã se afi?eze pe ecran vârfurile descoperite de algoritmul BFS ?i distan?a fa?ã de vârful sursã (arborele descoperit). 

struct proprietati
{
	char culoare[30];
	int parinte;
	int distanta;
};

struct varf
{
	int val;
	proprietati pr;
};

void bfs(int a[100][100], int n, int s, struct varf vf[100])
{
	for (int i = 1; i <= n; i++)
		if (i != s)
		{
			strcpy(vf[i].pr.culoare, "alb");
			vf[i].pr.parinte = 0;
			vf[i].pr.distanta = inf;
		}
	strcpy(vf[s].pr.culoare, "gri");
	vf[s].pr.distanta = 0;
	vf[s].pr.parinte = 0;
	queue<int>coada;
	coada.push(s);
	while (!coada.empty())
	{
		int elem = coada.front();
		coada.pop();
		for (int i = 1; i <= n; i++)
		{
			if (a[elem][i]==1 && strcmp(vf[i].pr.culoare, "alb") == 0)
			{
				strcpy(vf[i].pr.culoare, "gri");
				vf[i].pr.distanta = vf[elem].pr.distanta+1;
				vf[i].pr.parinte = elem;
				coada.push(i);
			}
		}
		strcpy(vf[elem].pr.culoare, "negru");
	}
}

void pb4()
{
	ifstream f4("graf4.txt");
	int n, x, y, s;
	int a[100][100] = { 0 };
	f4 >> n;
	while (f4 >> x >> y)
	{
		a[x][y] = 1;
		a[y][x] = 1;
	}
	f4.close();
	varf vf[100];
	for (int i = 1; i <= n; i++)
		vf[i].val = i;
	cout << "Introduceti varful sursa: ";
	cin >> s;
	bfs(a, n, s, vf);
	for (int i = 1; i <= n; i++) {
		if (strcmp(vf[i].pr.culoare, "negru") == 0) {
			cout << "Nodul " << i << " este descroperit si se afla la distanta " << vf[i].pr.distanta << " fata de nodul sursa " << s << ", avand ca parinte pe " << vf[i].pr.parinte << "\n";
		}
		else {
			cout << "Nodul " << i << " nu a fost descoperit de BFS si nu exista un drum intre el si nodul sursa " << s << "\n";

		}
	}
}

// Pentru un graf dat sã se afi?eze pe ecran vârfurile descoperite de apelul recursiv al procedurii DFS_VISIT(G, u) (apadurea descoperitã de DFS).

struct proprietati2
{
	char culoare[30];
	int parinte;
	int descoperire, finalizare;
};

struct varf2 
{
	int val;
	proprietati2 pr;
};

void dfs_vizit(int a[100][100], int n, struct varf2 vf[100],int &timp, int elem)
{
	timp++;
	vf[elem].pr.descoperire = timp;
	strcpy(vf[elem].pr.culoare, "gri");
	for (int i = 1; i <= n; i++)
	{
		if (strcmp(vf[i].pr.culoare, "alb") == 0 && a[elem][i]==1) 
		{
			vf[i].pr.parinte = elem;
			dfs_vizit(a,n,vf,timp,i);
		}
	}
	strcpy(vf[elem].pr.culoare, "negru");
	timp++;
	vf[elem].pr.finalizare = timp;
}

void dfs(int a[100][100],int n, struct varf2 vf[100])
{
	for (int k = 1; k<=n; k++)
	{
		for (int i = 1; i <= n; i++)
		{
			strcpy(vf[i].pr.culoare, "alb");
			vf[i].pr.parinte = 0;
		}
		int timp = 0;
		cout << "In parcurgerea DFS din varful " << k << " am descoperit varfurile: ";
		for (int i = 1; i <= n; i++)
			if (strcmp(vf[k].pr.culoare, "alb") == 0)
				dfs_vizit(a, n, vf, timp, k);
		cout << endl;
	}
	
}

void pb5()
{
	ifstream f5("graf5.txt");
	int n, x, y, s;
	int a[100][100] = { 0 };
	f5 >> n;
	while (f5 >> x >> y)
	{
		a[x][y] = 1;
		a[y][x] = 1;
	}
	f5.close();
	varf2 vf[100];
	for (int i = 1; i <= n; i++)
		vf[i].val = i;
	dfs(a, n, vf);
}

// Start program

int main()
{
	cout << "Problmea 1" << endl;
	pb1();
	cout << "Problmea 2" << endl;
	pb2();
	cout << "Problmea 3" << endl;
	//pb3();
	cout << "Problmea 4" << endl;
	pb4();
	cout << "Problmea 5" << endl;
	pb5();
}