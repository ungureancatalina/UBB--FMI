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
	int V;
	int E;
	list<pair<int, int>>* adj;

public:
	graf_fis(string in, string out);
	void dijkstra(int src);
	bool bellmanFord();
	void johnson();
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
	int V, E;
	fin >> V >> E;
	this->adj = new list<pair<int, int>>[V];
	this->V = V;
	this->E = E;
	int u, v, w;
	while (fin >> u >> v >> w)
	{
		adj[u].push_back({ v, w });
	}
	fin.close();
}

void graf_fis::dijkstra(int src) 
{
	priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> coada;
	vector<int> distanta(V, INF);

	coada.push({ 0, src });
	distanta[src] = 0;
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

	ofstream fout;
	fout.open(this->out, ios_base::app);
	for (int i = 0; i < V; ++i) {
		if (distanta[i] == INF) {
			fout << "INF ";
		}
		else {
			fout << distanta[i] << " ";
		}
	}
	fout << "\n";
}

bool graf_fis::bellmanFord() 
{
	struct arc 
	{
		int s, d, w;
	};
	vector<arc> arc;
	vector<int> dist(V + 1, INF);
	dist[V] = 0;
	for (int i = 0; i < V; ++i) 
	{
		arc.push_back({ V, i, 0 });
		for (auto j : adj[i]) 
		{
			arc.push_back({ i, j.first, j.second });
		}
	}
	for (int i = 0; i < V - 1; ++i) 
	{
		for (auto j : arc) {
			if (dist[j.s] != INF && dist[j.d] > dist[j.s] + j.w) 
			{
				dist[j.d] = dist[j.s] + j.w;
			}
		}
	}
	for (int i = 0; i < V; ++i) 
	{
		arc.pop_back();
	}
	ofstream fout(this->out);
	for (auto j : arc) 
	{
		if (dist[j.s] + j.w < dist[j.d]) 
		{
			return false;
		}
	}
	for (int i = 0; i < V; ++i) 
	{
		for (auto j : adj[i]) {
			j.second += dist[i] - dist[j.first];
			fout << i << " " << j.first << " " << j.second << "\n";
		}
	}
	fout.close();
	return true;
}

void graf_fis::johnson() 
{
	if (this->bellmanFord() == false) 
	{
		ofstream fout(out);
		fout << "-1\n";
		fout.close();
		return;
	}
	for (int i = 0; i < V; ++i) 
	{
		this->dijkstra(i);
	}
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
		cout << " Utilizare: ./p2.exe fisier1 fisier2\n";
		exit(-1);
	}
	graf_fis G(argv[1], argv[2]);
	G.johnson();
	return 0;
}