#include <iostream>
#include <fstream>

using namespace std;

ifstream f("C:/Users/ungur/OneDrive/Desktop/SEM_2/ag/lab/4/ex1/lab4/fisier.txt");
int parinte[101], prufer[101];

int frunza_min(int n) 
{
    int fm = 0, i = 0;
    while (i < n) 
    {
        if (fm == parinte[i]) 
        {
            fm++;
            i = -1;
        }
        i++;
    }
    return fm;
}

void codare_prufer(int n) 
{
    for (int i = 0; i < n - 1; i++) 
    {
        int fm = frunza_min(n);
        prufer[i] = parinte[fm];
        parinte[fm] = fm;
    }
}

int main(int argc, char* argv[]) 
{
    if (argc != 3)
    {
        return 1;
    }

    ifstream f(argv[1]);
    if (!f.is_open())
    {
        cout << "Eroare la deschidere" << endl;
        return 1;
    }

    ofstream g(argv[2]);
    if (!g.is_open())
    {
        cout << "Eroare la deschidere "<< endl;
        return 1;
    }
    int n;
    f >> n;
    for (int i = 0; i < n; i++) 
    {
        f >> parinte[i];
    }
    codare_prufer(n);
    g << n - 1 << endl;
    for (int i = 0; i < n - 1; i++) 
    {
        g << prufer[i] << " ";
    }
    f.close();
    g.close();
    return 0;
}