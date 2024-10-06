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
    RepoAbstract& repo;
public:
    undo_adauga(RepoAbstract& repo, const Film& f) : repo{ repo }, adaugat{ f } {}

    void doUndo() override {
        repo.sterge(adaugat);
    }
};


class undo_modifica : public Undo {
    Film vechi, nou;
    RepoAbstract& repo;
public:
    undo_modifica(RepoAbstract& repo, Film& vechi, Film& nou) : repo{ repo }, vechi{ vechi }, nou{ nou } {}

    void doUndo() override {
        repo.sterge(nou);
        repo.adauga(vechi);
    }
};

class undo_sterge : public Undo {
    Film sters;
    RepoAbstract& repo;
public:
    undo_sterge(RepoAbstract& repo, Film& sters) : repo{ repo }, sters{ sters } {}

    void doUndo() override {
        repo.adauga(sters);
    }
};