#pragma once
#include "service.h"
#include "domain.h"
#include <vector>

class Ui
{
    FilmServ& serv;
    void meniu();
    void meniu2();
    void adauga_default();
    void adauga_ui();
    static void afisare_ui(const vector <Film>& filme);
    void modifica_ui();
    void sterge_ui();
    void cauta_ui();
    void sortare_ui();
    void filtrare_titlu_ui();
    void filtrare_an_ui();
    void cos_adauga_ui();
    void cos_adauga_random_ui();
    void cos_sterge_ui();
    void cos_export_ui();
    void raport_ui();
    
public:
    explicit Ui(FilmServ& serv) : serv{ serv }
    {
    }
    Ui(const Ui& fs) = delete;
    void run();
    void run2();
};