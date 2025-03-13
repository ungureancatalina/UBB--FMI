#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
using namespace std;

ifstream f("C:/Users/ungur/OneDrive/Desktop/SEM_2/ag/lab/4/ex2/ex2/fisier.txt");

int main()
{
    int M, i, x, y;
    f >> M;
    vector<int> cod;
    for (i = 0; i < M; i++) {
        int x;
        f >> x;
        cod.push_back(x);
    }

    vector<int> tati(M + 1, -1);
    for (i = 0; i < M; i++) {
        vector<int>::iterator it;
        x = *cod.begin();
        vector<int> mic(M + 1, 0);
        int k;
        for (it = cod.begin(); it != cod.end(); it++) {
            mic[*it] = 1;
        }
        k = 0;
        bool gasit = false;
        while (k <= M && !gasit) {
            if (mic[k] == 0) {
                y = k;
                gasit = true;
            }
            k++;
        }
        cod.erase(cod.begin());
        tati[y] = x;
        cod.push_back(y);
    }
    cout << M + 1 << "\n";
    vector<int>::iterator it;
    for (it = tati.begin(); it != tati.end(); it++) {
        cout << *it << " ";
    }
    f.close();
    return 0;
}
