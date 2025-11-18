#ifndef LAB3_COMPAREFILES_H
#define LAB3_COMPAREFILES_H

#include <string>

// Compara doua fisiere, ignorand spatiile si newline-urile
bool filesAreIdenticalTokens(const std::string& file1, const std::string& file2);

// Ruleaza compararea cu input din linia de comanda
void runCompareFiles();

#endif