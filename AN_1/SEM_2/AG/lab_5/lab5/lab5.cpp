#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <queue>
using namespace std;

bool bfs(int parinte[], int V, int E, vector<int>* adj, vector<int>& matrice_adj, int nr_cols) {
    vector<bool> vizitat(V, false);
    queue<int> nod;
    nod.push(0);
    vizitat[0] = true;
    while (!nod.empty()) {
        int nc = nod.front();
        nod.pop();
        // Parcurgem to?i vecinii nodului curent
        for (auto nv : adj[nc]) {
            // Verific�m dac� vecinul nu a fost vizitat ?i dac� exist� o capacitate pozitiv� �ntre nodul curent ?i vecin
            if (vizitat[nv] == false && matrice_adj[nc * nr_cols + nv] > 0) {
                nod.push(nv);
                parinte[nv] = nc; 
                vizitat[nv] = true;
            }
        }
    }
    // Return�m true dac� destina?ia (V - 1) a fost vizitat�, altfel return�m false
    return (vizitat[V - 1] == true);
}

int edmondsKarp(int V, int E, vector<int>* adj, vector<int>& matrice_adj, int nr_cols) {
    int* parinte = new int[V];
    int max_flow = 0;
    // C�t timp exist� un drum de la surs� la destina?ie folosind BFS
    while (bfs(parinte, V, E, adj, matrice_adj, nr_cols)) {
        // Calcul�m fluxul maxim pe drumul g�sit
        int path_flow = INT_MAX;
        for (int v = V - 1; v != 0; v = parinte[v]) {
            int u = parinte[v];
            path_flow = min(path_flow, matrice_adj[u * nr_cols + v]);
        }
        // Actualiz�m capacit�?ile arcelor pe drum ?i pe invers
        for (int v = V - 1; v != 0; v = parinte[v]) {
            int u = parinte[v];
            matrice_adj[u * nr_cols + v] -= path_flow;
            matrice_adj[v * nr_cols + u] += path_flow;
        }
        max_flow += path_flow;
    }
    // Eliber�m memoria alocat� pentru vectorul de p�rin?i ?i return�m fluxul maxim
    delete[] parinte;
    return max_flow;
}

int main(int argc, char* argv[]) {
    //nr de varfuri si de arce
    int V, E;
    int x, y, c;
    int sursa = 0;
    if (argc != 3)
    {
        return 1;
    }

    ifstream fin(argv[1]);
    if (!fin.is_open())
    {
        cout << "Eroare la deschidere" << endl;
        return 1;
    }

    ofstream fout(argv[2]);
    if (!fout.is_open())
    {
        cout << "Eroare la deschidere " << endl;
        return 1;
    }
    fin >> V >> E;

    //vector de vectori pentru listele de adiacen?�
    vector<int>* adj = new vector<int>[V]; 
    vector<int> matrice_adj(V * V, 0); 
    int destinatie = V - 1;
    while (fin >> x >> y >> c) {
        matrice_adj[x * V + y] = c;
        adj[x].push_back(y);
        adj[y].push_back(x);
    }
    fout << edmondsKarp(V, E, adj, matrice_adj, V);
    delete[] adj;
    return 0;
}
