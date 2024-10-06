
#pragma once
#include "domain.h"
#include <vector>
#include <algorithm>
#include <random>
#include <chrono>
#include "observer.h"
#include "repository.h"

using std::vector;

class CosFilm : public Observable
{
private:
	vector<Film> cos;
	const FilmRepo& repo;
public:

	CosFilm(const FilmRepo& repo) : repo(repo) {};
	void adauga_film_cos(const Film& f);
	void adauga_random(vector <Film> filme, int numar);
	const vector<Film>& getAll_cos();
	void sterge_film_cos(string titlu);
	void golire_cos();
	void exporta_cos(string fisier);
	bool gasit(vector<Film> film, vector<Film> cos);
	void umple_cos(int nr) 
	{
		int seed = std::chrono::system_clock::now().time_since_epoch().count();
		vector<Film> filme = repo.getAll();
		std::shuffle(filme.begin(), filme.end(), std::default_random_engine(seed)); 
		while (cos.size() < nr && filme.size() > 0) 
		{
			cos.push_back(filme.back());
			filme.pop_back();
		}
		notify();
	}
};

