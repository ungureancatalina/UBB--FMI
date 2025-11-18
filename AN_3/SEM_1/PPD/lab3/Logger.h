#ifndef LAB3_LOGGER_H
#define LAB3_LOGGER_H

#include <string>
#include <functional>
#include <chrono>

// Scrie o linie de log în Results.txt
void logResult(const std::string& variantName,
               int N1,
               int N2,
               int processes,
               long long timeNs);

long long averageExecutionTime(int runs, const std::function<void()>& func);

#endif