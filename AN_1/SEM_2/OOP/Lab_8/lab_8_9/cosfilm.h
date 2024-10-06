#include "domain.h";
#include <vector>
#include <algorithm>
#include <random>

using std::vector;

class CosFilme
{
private:
	vector<Film> cos;
public:

	CosFilme() = default;
	void adauga_film_cos(const Film& f);
	void adauga_random(vector <Film> filme, int numar);
	const vector<Film>& getAll_cos();
	void sterge_film_cos();
};