#pragma once
#include <string>
#include <vector>
#include "domain.h"
using namespace std;

class Validator
{
public:
	void validare(const Film& f);
};

class Exceptie
{
	vector <string> mesaj;
public:
	Exceptie(vector <string> mesaj) : mesaj{ mesaj }
	{
	}

	string get_mesaj() {
		string msj = "";
		for (const string eroare : mesaj) 
		{
			msj += eroare + "\n";
		}
		return msj;
	}
};