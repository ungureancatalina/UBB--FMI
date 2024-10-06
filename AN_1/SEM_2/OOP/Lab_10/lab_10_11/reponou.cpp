#include <algorithm>
#include "repository.h"
#include "validator.h"

void RepoNou::adauga(const Film& f)
{
    filme.insert(std::make_pair(f.get_gen(), f));
}

void RepoNou::sterge(const Film& f)
{
    auto it = filme.find(f.get_gen());
    if (it != filme.end()) {
        filme.erase(it);
    }
}

const Film& RepoNou::cauta(std::string gen)
{
    static Film film_default;
    for (const auto& pair : filme)
    {
        if (pair.first == gen)
        {
            return pair.second;
        }
    }
    return film_default;
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