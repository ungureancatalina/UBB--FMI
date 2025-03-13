#include <iostream>
#include <cstring>
#include <fstream>
#include <vector>
#include <queue>
#include <list>
#include <string>

using namespace std;
const int INF = 1000000;

class graf_fis
{
	string in;
	string out;
	int V, E, S;
	list <pair<int, int>>* adj;

public:
	graf_fis(string in, string out);
	void dijkstra();
	~graf_fis();
};

graf_fis::graf_fis(string in, string out)
{
	this->in = in;
	this->out = out;

	ifstream fin(this->in);
	if (!fin)
	{
		cout << "Fisierul de intrare nu a putut fi deschis!\n";
		exit(-1);
	}
	int V, E, S;
	fin >> V >> E >> S;
	this->V = V;
	this->E = E;
	this->S = S;
	this->adj = new list<pair<int, int>>[V];

	int u, v, w;
	while (fin >> u >> v >> w)
	{
		adj[u].push_back({ v, w });
	}
	fin.close();
}

void graf_fis::dijkstra()
{
	priority_queue <pair<int, int>, vector <pair<int, int>>, greater <pair<int, int>>> coada;
	vector<int> distanta(V, INF);

	coada.push({ 0, this->S });
	distanta[this->S] = 0;
	while (!coada.empty())
	{
		int u = coada.top().second;
		coada.pop();
		for (auto i : adj[u])
		{
			int v = i.first;
			int w = i.second;
			if (distanta[v] > distanta[u] + w)
			{
				distanta[v] = distanta[u] + w;
				coada.push({ distanta[v], v });
			}
		}
	}
	ofstream fout(this->out);
	for (int i = 0; i < V; ++i)
	{
		if (distanta[i] == INF)
		{
			fout << "INF ";
		}
		else 
		{
			fout << distanta[i] << " ";
		}
	}
	fout << "\n";
	fout.close();
}

graf_fis::~graf_fis()
{
	delete[] adj;
}


int main(int argc, char** argv)
{
	if (argc != 3)
	{
		cout << "Numar invalid de argumente!\n";
		cout << " Utilizare: ./p1.exe fisier1 fisier2\n";
		exit(-1);
	}
	graf_fis G(argv[1], argv[2]);
	G.dijkstra();
	return 0;
}