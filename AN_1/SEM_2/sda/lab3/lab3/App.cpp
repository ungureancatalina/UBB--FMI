#include <iostream>
#include <assert.h>
#include "DO.h"
#include "Iterator.h"
#include "TestScurt.h"
#include "TestExtins.h"
using namespace std;

int main()
{
    testAll();
    testInlocuire();
    cout << "Teste scurte corecte!" << endl;
    testAllExtins();
    cout << "Teste extinse corecte!" << endl;
    return 0;
}
