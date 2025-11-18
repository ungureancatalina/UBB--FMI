#include <iostream>
#include <string>
#include <mpi.h>
#include "MPI_Variant0.h"
#include "MPI_Variant1.h"
#include "MPI_Variant2.h"
#include "MPI_Variant3.h"
#include "GenerateRandom.h"
#include "CompareFiles.h"

using namespace std;

int main(int argc, char** argv) {
    MPI_Init(&argc, &argv);

    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    int opt = 0;

    // doar procesul 0 afiseaza meniul si citeste inputul
    if (rank == 0) {
        cout << "1. MPI - varianta 0 (secventiala)" << endl;
        cout << "2. MPI - varianta 1 (comunicare standard)" << endl;
        cout << "3. MPI - varianta 2 (scatter/gather)" << endl;
        cout << "4. MPI - varianta 3 (asincrona)" << endl;
        cout << "5. Generator numere mari" << endl;
        cout << "6. Comparare fisiere rezultate" << endl;
        cout << "Alege optiunea: ";
        cin >> opt;
    }

    // procesul 0 trimite tuturor optiunea aleasa
    MPI_Bcast(&opt, 1, MPI_INT, 0, MPI_COMM_WORLD);

    // fiecare proces ruleaza doar varianta corespunzatoare
    switch (opt) {
        case 1:
            if (rank == 0) runSequential();
            break;
        case 2:
            runVariant1();
            break;
        case 3:
            runVariant2();
            break;
        case 4:
            runVariant3();
            break;
        case 5:
            if (rank == 0) runGenerateRandom();
            break;
        case 6:
            if (rank == 0) runCompareFiles();
            break;
        default:
            if (rank == 0)
                cout << "Optiune invalida!" << endl;
            break;
    }

    MPI_Finalize();
    return 0;
}
