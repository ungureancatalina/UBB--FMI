#include <vector>
#include <iostream>
#include <string>
#include <cstring>

#include "ui.h"
#include "domain.h"
#include "validator.h"

using namespace std;

void Ui::meniu()
{
    cout << "Meniu:" << endl;
    cout << " Optiunea 1. Afisare lista de filme" << endl;
    cout << " Optiunea 2. Adaugare film in lista de filme" << endl;
    cout << " Optiunea 3. Stergere film din lista de filme" << endl;
    cout << " Optiunea 4. Modificare film din lista de filme" << endl;
    cout << " Optiunea 5. Cautare film in lista de filme" << endl;
    cout << " Optiunea 6. Filtrare lista de filme dupa titlu" << endl;
    cout << " Optiunea 7. Filtrare lista de filme dupa an" << endl;
    cout << " Optiunea 8. Sortare lista de filme" << endl;
    cout << " Optiunea 9. Raport lista" << endl;
    cout << " Optiunea 10. Cos filme" << endl;
    cout << " Optiunea 11. Undo" << endl;
    cout << " Optiunea 12. Iesire" << endl;
}

void Ui::meniu2()
{
    cout << "Meniu:" << endl;
    cout << " Optiunea 1. Adaugare filme in cos" << endl;
    cout << " Optiunea 2. Adaugare random de filme in cos" << endl;
    cout << " Optiunea 3. Golire cos" << endl;
    cout << " Optiunea 4. Afisare filme in cos" << endl;
    cout << " Optiunea 5. Export cos" << endl;
    cout << " Optiunea 6. Iesire" << endl;
}
//
//void Ui::adauga_default()
//{
//    string titlu, gen, actor;
//    int an;
//    titlu = "Pacea", gen = "actiune", actor = "Mihai", an = 2005;
//    this->serv.adauga(titlu, gen, actor, an);
//    titlu = "Marea", gen = "dragoste", actor = "Ana", an = 2000;
//    this->serv.adauga(titlu, gen, actor, an);
//    titlu = "Pruncul", gen = "dragoste", actor = "Carla", an = 2005;
//    this->serv.adauga(titlu, gen, actor, an);
//    titlu = "Natura", gen = "fictiune", actor = "Tudor", an = 2004;
//    this->serv.adauga(titlu, gen, actor, an);
//}

void Ui::adauga_ui()
{
    string titlu, gen, actor;
    int an;
    cout << "Titlul ce doriti a fi adaugat este: ";
    getline(cin >> ws, titlu);
    cout << "Genul ce doriti a fi adaugat este: ";
    getline(cin >> ws, gen);
    cout << "Actorul ce doriti a fi adaugat este: ";
    getline(cin >> ws, actor);
    cout << "Anul ce doriti a fi adaugat este: ";
    cin >> an;
    try
    {
        serv.adauga(titlu, gen, actor, an);
    }
    catch (Exceptie exceptie)
    {
        cout << exceptie.get_mesaj();
    }
}

void Ui::sterge_ui()
{
    string titlu;
    cout << "Titlul ce doriti a fi sters este: ";
    cin >> titlu;
    try
    {
        serv.sterge(titlu);
    }
    catch (Exceptie exceptie)
    {
        cout << exceptie.get_mesaj();
    }
}

void Ui::modifica_ui()
{
    string titlu, gen, actor;
    int an;
    cout << "Titlul nou este: ";
    cin >> titlu;
    cout << "Genul nou este: ";
    cin >> gen;
    cout << "Actorul nou este: ";
    cin >> actor;
    cout << "Anul nou este: ";
    cin >> an;
    try
    {
        serv.modifica(titlu, gen, actor, an);
    }
    catch (Exceptie exceptie)
    {
        cout << exceptie.get_mesaj();
    }
}

void Ui::afisare_ui(const vector <Film>& filme)
{
    cout << "Filmele sunt:" << endl;
    for (const auto& f : filme)
    {
        cout << "Titlul: " << f.get_titlu() << " | Genul: " << f.get_gen() << " | Actorul: " << f.get_actor() << " | Anul: " << f.get_an() << endl;
    }
}

void Ui::cauta_ui()
{
    string titlu;
    cout << "Titlul filmului ce doriti a fi cautat este: ";
    cin >> titlu;
    if (serv.cauta(titlu))
        cout << "Exista filmul in lista \n";
    else
        cout << "Nu exista filmul in lista\n";
}

void Ui::filtrare_titlu_ui()
{
    string titlu;
    cout << "Titlul dupa care doriti sa fie filtrata lista este: ";
    cin >> titlu;
    afisare_ui(serv.filtrare_titlu(titlu));
}

void Ui::filtrare_an_ui()
{
    int an;
    cout << "Anul dupa care doriti sa fie filtrata lista este: ";
    cin >> an;
    afisare_ui(serv.filtrare_an(an));
}

void Ui::sortare_ui()
{
    cout << "1. Titlu | 2. Actor | 3. An si gen" << endl;
    string optiune;
    cout << "Optiunea dorita este: ";
    cin >> optiune;
    if (optiune == "1") {
        auto lista_sortata = serv.sortare_titlu();
        afisare_ui(lista_sortata);
    }
    else if (optiune == "2") {
        auto lista_sortata = serv.sortare_actor();
        afisare_ui(lista_sortata);
    }
    else if (optiune == "3") {
        auto lista_sortata = serv.sortare_an_gen();
        afisare_ui(lista_sortata);
    }
}

void Ui::cos_adauga_ui()
{
    string titlu;
    cout << "Titlul ce doriti a fi adaugat in cos este:";
    cin >> titlu;
    try {
        serv.cos_adauga(titlu);
        cout << " Filmul s-a adaugat in cos\n";
    }
    catch (Exceptie ex) {
        cout << ex.get_mesaj() << endl;
    }
}


void Ui::cos_adauga_random_ui()
{
    int numar;
    char str[101];
    cout << "Introduecti un nr de filme: ";
    cin >> str;
    numar = stoi(str);
    if (!(numar < 0 || strlen(str) > 10))
    {
        try
        {
            serv.cos_adauga_random(numar);
            cout << numar << " filme s-au adaugat in cos\n";
        }
        catch (Exceptie ex)
        {
            cout << ex.get_mesaj() << endl;
        }
    }
    else cout << "Numarul introdus nu este corect" << endl;
}

void Ui::cos_sterge_ui()
{
    serv.cos_sterge();
    cout << " Filmele s-au sters din cos\n";
}

void Ui::cos_export_ui()
{
    string filename;
    cout << "Introduceti numele fisierului (.csv sau .html): " << endl;
    cin >> filename;
    string referinta = "C:/Users/ungur/OneDrive/Desktop/SEM_2/oop/lab/89/lab_8_9";
    serv.cos_export(referinta.append(filename));
}

void Ui::raport_ui()
{
    cout << "Numar de filme" << endl;
    auto map = serv.frecvente_titlu();
    for (const auto& pereche : map)
    {
        cout << pereche.first << ": " << pereche.second << " filme" << endl;
    }
}


void Ui::run2()
{
    while (true)
    {
        meniu2();
        int optiune;
        cout << "Optiunea dorita este: ";
        cin >> optiune;
        try
        {
            switch (optiune)
            {
            case 1:
                cos_adauga_ui();
                break;
            case 2:
                cos_adauga_random_ui();
                break;
            case 3:
                cos_sterge_ui();
                break;
            case 4:
                afisare_ui(serv.getAll_cos());
                break;
            case 5:
                cos_export_ui();
                break;
            case 6:
                return;
            default:
                cout << "Aceasta optiune nu exista" << endl;
            }
        }
        catch (ExceptieRepo& exceptie)
        {
            cout << exceptie << endl;
        }
    }
}

void Ui::run()
{
    //this->adauga_default();
    while (true)
    {
        meniu();
        int optiune;
        cout << "Optiunea dorita este: ";
        cin >> optiune;
        try
        {
            switch (optiune)
            {
            case 1:
                afisare_ui(serv.getAll());
                break;
            case 2:
                adauga_ui();
                break;
            case 3:
                sterge_ui();
                break;
            case 4:
                modifica_ui();
                break;
            case 5:
                cauta_ui();
                break;
            case 6:
                filtrare_titlu_ui();
                break;
            case 7:
                filtrare_an_ui();
                break;
            case 8:
                sortare_ui();
                break;
            case 9:
                raport_ui();
                break;
            case 10:
                run2();
                break;
            case 11:
                try
                {
                    serv.undo();
                    cout << "S-a realizat undo" << endl;
                }
                catch (Exceptie ex)
                {
                    cout << ex.get_mesaj() << endl;
                }
                break;
            case 12:
                return;
            default:
                cout << "Aceasta optiune nu exista" << endl;
            }
        }
        catch (ExceptieRepo& exceptie)
        {
            cout << exceptie << endl;
        }
    }
}