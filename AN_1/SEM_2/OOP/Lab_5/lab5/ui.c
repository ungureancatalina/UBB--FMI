#define _CRTDBG_MAP_ALLOC
#include <crtdbg.h>
#include <windows.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>


#include "controller.h"


void print_menu()
{
    printf("### MENU ###\n");
    printf("1.Adaugare oferta.\n"); 
    printf("2.Actualizare oferta\n"); 
    printf("3.Sterge oferta\n"); 
    printf("4.Vizualiare oferte\n"); 
    printf("5.Vizualizare oferete ordonat dupa pret, tip\n");
    printf("6.Vizualizare oferta filtrate dupa suprafata\n"); 
    printf("7.Vizualizare oferta filtrate dupa tip\n"); 
    printf("8.Vizualizare oferta filtrate dupa pret\n");
    printf("9.Vizualizare oferta filtrate dupa pret in functie de un multiplu\n");
    printf("10.Undo\n");
    printf("11.Exit aplication\n");
}
void sub_menu_sort_offerts()
{
    printf("### SUBMENU SORT ###\n");
    printf("1.Ordonare dupa pret\n");
    printf("2.Ordonare dupa tip\n");
    printf("3.Ordonare dupa tip, iar apoi dupa pret\n");
    printf("4.Back\n");
}
void sub_menu_filter_tip_offerts()
{
    printf("### SUBMENU FILTER BY TIP ###\n");
    printf("1.Afisare toate cele care sunt de tip teren\n");
    printf("2.Afisare toate cele care sunt de tip casa\n");
    printf("3.Afisare toate cele care sunt de tip apartament\n");
    printf("4.Back\n");
}
void adauga_default(Controller* controller)
{
    add_oferta(controller, "teren", 52, "Sibiu,str.Luncii,nr.14", 350);
    add_oferta(controller, "casa", 120, "Cluj,str.Aurel Vlaicu,nr.184", 250);
    add_oferta(controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
    add_oferta(controller, "teren", 375, "Sibiu,str.Rovine,nr.12", 5550);
    add_oferta(controller, "casa", 275, "Pitesti,str.Ploiesti,nr.23", 150);
    add_oferta(controller, "casa", 175, "Alba-Iulia,str.Neagra,nr.5", 1550);
}
int digits(int nr)
{
    int cnt = 0;
    while (nr)
    {
        cnt++;
        nr /= 10;
    }
    return cnt;
}
void display_oferte_by_array(Vector* vec)
{
    if (vec->size == 0)
        printf("Lista este vida\n");
    else
    {
        printf("Ofertele sunt: \n");
        for (int i = 0; i < vec->size; i++)
        {
            Oferta* oferta = (Oferta*)get_vector_element(vec, i);
            printf("Tip: %s | Suprafata: %d | Adresa: %s | Pret: %d \n", oferta->tip, oferta->suprafata, oferta->adresa, oferta->pret);

        }
    }
}
void test_modules()
{
    test_validation();
    test_array();
    test_domain();
    test_controller();
}

void read_line(char* store)
{
    fflush(stdin);
    fgets(store, 150, stdin);
    store[strlen(store) - 1] = '\0';
}
void run_console()
{
    Controller controller = create_Controller();
    int program_run = 1;
    adauga_default(&controller);
    char* optiune = (char*)malloc(100 * sizeof(char));
    int citire = 0;
    while (program_run)
    {
        print_menu();
        printf("Introduceti o optiune:");
        read_line(optiune);
        Sleep(1000);
        if (validate_option(optiune, 1, 11))
        {
            int option = transform_chars_to_integer(optiune);
            if (option == 1)
            {
                char* tip = (char*)malloc(200 * sizeof(char));
                int suprafata=0;
                char* adresa = (char*)malloc(200 * sizeof(char));
                int pret=0;
                printf("Introduceti tipul imobilului. Tipul poate fi doar teren, casa sau apartament:");
                citire=scanf("%s",tip);
                printf("Introduceti suprafata in metrii patrati:");
                citire=scanf("%d", &suprafata);
                printf("Introduceti adresa imobilului:");
                citire = scanf("%s", adresa);
                printf("Introduceti pretul imobilului:");
                citire=scanf("%d", &pret);
                int result = add_oferta(&controller, tip, suprafata, adresa, pret);
                if (result)
                    printf("Oferta a fost adaugata cu succes!\n");
                free(tip), free(adresa);
            }
            if (option == 2)
            {
                int id=0;
                printf("Introduceti id-ul ofertei:");
                citire =scanf("%d", &id);
                char* tip = (char*)malloc(200 * sizeof(char));
                int suprafata=0;
                char* adresa = (char*)malloc(200 * sizeof(char));
                int pret=0;
                printf("Introduceti tipul imobilului. Tipul poate fi doar teren, casa sau apartament:");
                citire = scanf("%s", tip);
                printf("Introduceti suprafata in metrii patrati:");
                citire=scanf("%d", &suprafata);
                printf("Introduceti adresa imobilului:");
                citire = scanf("%s", adresa);
                printf("Introduceti pretul imobilului:");
                citire=scanf("%d", &pret);
                int result = edit(&controller, id, tip, suprafata, adresa, pret);
                if (result)
                    printf("Oferta a fost modificata cu succes\n");
                free(tip), free(adresa);
            }
            if (option == 3)
            {
                int id=0;
                printf("Introduceti id-ul ofertei:");
                citire=scanf("%d",&id);
                int result = remove_of(&controller, id);
                if (result)
                    printf("Oferta a fost stearsa cu succes!\n");
            }
            if (option == 4)
            {
                display_oferte_by_array(controller.repo);
            }
            if (option == 5)
            {
                sub_menu_sort_offerts();
                printf("Introduceti o optiune:");
                char* sub_optiune = (char*)malloc(100 * sizeof(char));
                read_line(sub_optiune);
                while (!validate_option(sub_optiune, 1, 5))
                {
                    printf("Optiunea introdusa este invalida.\n");
                    sub_menu_sort_offerts();
                    printf("Introduceti o optiune:");
                    read_line(sub_optiune);
                    Sleep(1000);
                }
                int option_s = transform_chars_to_integer(sub_optiune);
                if (option_s == 1)
                {
                    Vector* oferte = sort_by_price(&controller);
                    display_oferte_by_array(oferte);
                    free(get_Vector_Array(oferte));
                    free(oferte);
                }
                else if (option_s == 2)
                {
                    Vector* oferte = sort_by_tip(&controller);
                    display_oferte_by_array(oferte);
                    free(get_Vector_Array(oferte));
                    free(oferte);
                }
                else if (option_s == 3)
                {
                    Vector* oferte = sort_by_tip_price(&controller);
                    display_oferte_by_array(oferte);
                    free(get_Vector_Array(oferte));
                    free(oferte);
                }
                free(sub_optiune);
            }
            if (option == 6)
            {
                char* val_mini = (char*)malloc(200 * sizeof(char));
                char* val_maxi = (char*)malloc(200 * sizeof(char));
                printf("Introduceti suprafata minima:");
                read_line(val_mini);
                printf("Introduceti suprafata maxima:");
                read_line(val_maxi);
                if (!check_number_string(val_mini))
                    printf("Valoare minima introdusa nu este un numar intreg pozitiv!\n");
                else if (!check_number_string(val_maxi))
                    printf("Valoare maxima introdusa nu este un numar intreg pozitiv\n");
                else {
                    Vector* oferte = filter_by_suprafata(&controller, val_mini, val_maxi);
                    printf("%d records from database:\n", get_Vector_Size(oferte)), display_oferte_by_array(oferte);
                    free(get_Vector_Array(oferte));
                    free(oferte);
                }
                free(val_mini);
                free(val_maxi);
            }
            if (option == 7)
            {
                sub_menu_filter_tip_offerts();
                printf("Introduceti o optiune:");
                char* sub_optiune = (char*)malloc(100 * sizeof(char));
                read_line(sub_optiune);
                while (!validate_option(sub_optiune, 1, 4))
                {
                    printf("Optiunea introdusa este invalida.\n");
                    sub_menu_filter_tip_offerts();
                    printf("Introduceti o optiune:");
                    read_line(sub_optiune);
                    Sleep(1000);
                }
                int option_s = transform_chars_to_integer(sub_optiune);
                printf("%d", option_s);
                if (option_s == 1)
                {
                    Vector* oferte = filter_by_tip(&controller, "teren");
                    printf("%d records from database:\n", get_Vector_Size(oferte)), display_oferte_by_array(oferte);
                    free(get_Vector_Array(oferte));
                    free(oferte);
                }
                if (option_s == 2)
                {
                    Vector* oferte = filter_by_tip(&controller, "casa");
                    printf("%d records from database:\n", get_Vector_Size(oferte)), display_oferte_by_array(oferte);
                    free(get_Vector_Array(oferte));
                    free(oferte);
                }
                if (option_s == 3)
                {
                    Vector* oferte = filter_by_tip(&controller, "apartament");
                    printf("%d records from database:\n", get_Vector_Size(oferte)), display_oferte_by_array(oferte);
                    free(get_Vector_Array(oferte));
                    free(oferte);
                }
                free(sub_optiune);
            }
            if (option == 8)
            {
                char* val_mini = (char*)malloc(200 * sizeof(char));
                char* val_maxi = (char*)malloc(200 * sizeof(char));
                printf("Introduceti pretul minim:");
                read_line(val_mini);
                printf("Introduceti pretul maxim:");
                read_line(val_maxi);
                if (!check_number_string(val_mini))
                    printf("Valoare minima introdusa nu este un numar intreg pozitiv!\n");
                else if (!check_number_string(val_maxi))
                    printf("Valoare maxima introdusa nu este un numar intreg pozitiv\n");
                else {
                    int* size = (int*)malloc(sizeof(int));
                    if (size != NULL)
                    {
                        Vector* oferte = filter_by_pret(&controller, val_mini, val_maxi);
                        printf("%d records from database:\n", get_Vector_Size(oferte)), display_oferte_by_array(oferte);
                        free(get_Vector_Array(oferte));
                        free(oferte);
                    }
                    free(size);
                }
                free(val_mini), free(val_maxi);
            }
            if (option == 9)
            {
                char* multiplu = (char*)malloc(200 * sizeof(char));
                printf("Introduceti un nr intreg care va fi multiplu:");
                read_line(multiplu);
                if (!check_number_string(multiplu))
                    printf("Valoare introdusa nu este un numar intreg pozitiv!\n");
                else {
                    int* size = (int*)malloc(sizeof(int));
                    if (size != NULL)
                    {
                        Vector* oferte = filter_by_pret_multiplu(&controller, multiplu);
                        printf("%d records from database:\n", get_Vector_Size(oferte)), display_oferte_by_array(oferte);
                        free(get_Vector_Array(oferte));
                        free(oferte);
                    }
                    free(size);
                }
                free(multiplu);
            }
            if (option == 10)
            {
                if (undo(&controller) == 1)
                    printf("Undo done succesfully");
                else
                    printf("Undo went wrong");
            }
            if (option == 11)
            {
                printf("Aplication closed succesfully");
                destroy_Controller(&controller);
                program_run = 0;
            }
        }
        else
            printf("Optiune invalida!\n");
        Sleep(1000);
    }
    free(optiune);
}

int main()
{
    test_modules();
    run_console();
    _CrtDumpMemoryLeaks();
    return 0;
}