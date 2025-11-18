#ifndef LAB3_GENERATERANDOM_H
#define LAB3_GENERATERANDOM_H

#include <string>

// Genereaza un numar random cu n cifre in fisierul dat
void generateNumber(const std::string& filename, int n);

// Ruleaza generatorul complet (cere N1, N2 si genereaza fisierele)
void runGenerateRandom();

#endif