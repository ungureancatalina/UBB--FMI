#include "domain.h"
#include "repository.h"

class Undo
{
public:
    virtual void doUndo() = 0;

    virtual ~Undo() {};
};

class undo_adauga : public Undo {
    Film adaugat;
    FilmRepo& repo;
public:
    undo_adauga(FilmRepo& repo, const Film& f) : repo{ repo }, adaugat{ f } {}

    void doUndo() override {
        repo.sterge(adaugat.get_titlu());
    }
};


class undo_modifica : public Undo {
    Film vechi, nou;
    FilmRepo& repo;
public:
    undo_modifica(FilmRepo& repo, Film& vechi, Film& nou) : repo{ repo }, vechi{ vechi }, nou{ nou } {}

    void doUndo() override {
        repo.sterge(nou.get_titlu());
        repo.adauga(vechi);
    }
};

class undo_sterge : public Undo {
    Film sters;
    FilmRepo& repo;
public:
    undo_sterge(FilmRepo& repo, Film& sters) : repo{ repo }, sters{ sters } {}

    void doUndo() override {
        repo.adauga(sters);
    }
};