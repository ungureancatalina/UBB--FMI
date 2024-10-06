#include "validator.h"
#include <string.h>
using namespace std;

void Validator::validare(const Film& f)
{
    vector <string> errors;
    if (f.get_titlu().length() < 0)
        errors.push_back("Titlul nu poate fi vid");
    if (f.get_actor().length() < 0)
        errors.push_back("Numele actorului nu poate fi vid");
    if (f.get_an() <= 0)
        errors.push_back("Anul nu poate fi mai mic ca 0");
    if (f.get_gen().length() < 0)
        errors.push_back("Genul filmului nu poate fi vid");
    if (errors.size() != 0)
        throw Exceptie(errors);
}
