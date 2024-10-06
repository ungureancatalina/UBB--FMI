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
    Film() = delete;
    Film(string titlu, string gen, string actor, int an) : titlu(titlu), gen(gen), actor(actor), an(an) {}
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

    int get_an() const noexcept
    {
        return an;
    }

    void set_titlu(string titlu2)
    {
        titlu = titlu2;
    }

    void set_gen(string gen2)
    {
        gen = gen2;
    }

    void set_actor(string actor2)
    {
        actor = actor2;
    }

    void set_an(int an2)
    {
        an = an2;
    }

    Film& operator=(const Film& f) {
        this->titlu = f.titlu;
        this->gen = f.gen;
        this->actor = f.actor;
        this->an = f.an;
        return (*this);
    }
};