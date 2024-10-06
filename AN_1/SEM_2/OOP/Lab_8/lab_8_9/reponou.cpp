#include <algorithm>
#include "repository.h"
#include "validator.h"

void RepoNou::adauga(const Film& f) 
{
    filme.insert(std::make_pair(f.get_titlu(), f));
}

void RepoNou::sterge(const Film& f) 
{
    int i = 0;
    for (const auto& i : filme)
    {
        if (i.first == f.get_titlu())
        {
            filme.erase(i.first);
            return;
        }
    }
}

const Film& RepoNou::cauta(std::string titlu) 
{
    int i = 0;
    for (const auto& i : filme)
    {
        if (i.first == titlu)
        {
            return i.second;
        }
    }
}

vector<Film> filmul;

vector<Film>& RepoNou::getAll() 
{
    filmul.clear();
    for (auto& f : filme) 
    {
        filmul.push_back(f.second);
    }
    return filmul;
}