#pragma once
#include <string>
#include <iostream>

using std::string;
using std::cout;

class Film
{
private:
    string titlu;
    string gen;
    string actor;
    int an;

public:
    Film() = default;
    Film(const string& titlu, const string& gen, const string& actor, int an): titlu(titlu), gen(gen), actor(actor), an(an) {}
    Film(const Film& f) :titlu{ f.titlu }, gen{ f.gen }, actor{ f.actor }, an{ f.an }
    {
        //cout << "constr copie reusita\n";
    }
    string get_titlu() const
    {
        return titlu;
    }

    string get_gen() const
    {
        return gen;
    }

    string get_actor() const
    {
        return actor;
    }

    int get_an() const
    {
        return an;
    }
};
