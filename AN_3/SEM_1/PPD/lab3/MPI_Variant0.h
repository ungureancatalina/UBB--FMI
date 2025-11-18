#ifndef LAB3_MPI_VARIANT0_H
#define LAB3_MPI_VARIANT0_H

#include <vector>
#include <string>

// Citeste un numar mare din fisier
std::vector<unsigned char> readNumberSequential(const std::string& filename);

// Aduna doua numere mari (reprezentate ca vectori)
std::vector<unsigned char> addLargeNumbersSequential(const std::vector<unsigned char>& num1,
                                                     const std::vector<unsigned char>& num2);

// Scrie rezultatul intr-un fisier
void writeNumberSequential(const std::vector<unsigned char>& number, const std::string& filename);

// Ruleaza varianta secventiala completa
void runSequential();

#endif
