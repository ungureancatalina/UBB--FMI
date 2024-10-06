#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

#include "controller.h"
#include "array.h"
#include "validator.h"


Controller create_Controller()
{
    Controller controller;
    controller.repo = create_vector(delete_Oferta);
    controller.undo = create_vector(destroy_vector);
    return controller;
}

void destroy_Controller(Controller* controller)
{
    destroy_vector(controller->repo);
    destroy_vector(controller->undo);
}

void test_transform_chars_to_integer()
{
    //Functie care testeaza transform_chars_to_integer
    assert(transform_chars_to_integer("12345") == 12345);
    assert(transform_chars_to_integer("01234") == 1234);
    assert(transform_chars_to_integer("5") == 5);
}



int add_oferta(Controller* controller, char* tip, int suprafata, char* adresa, int pret)
{
    Oferta* of = create_Oferta(tip, suprafata, adresa, pret);
    int done = validare_oferta(of);
    if (!done)
    {
        delete_Oferta(of);
        return 0;
    }
    Vector* to_undo = copyList(controller->repo, copyOferta);
    add_vector(controller->repo,of);
    add_vector(controller->undo,to_undo);
    return 1;
}

int edit(Controller* controller, int id, char* tip, int suprafata, char* adresa, int pret)
{
    Oferta* of1 = create_Oferta(tip, suprafata, adresa, pret);
    int done = validare_oferta(of1);
    if (!done)
    {
        delete_Oferta(of1);
        return 0;
    }
    Vector* to_undo = copyList(controller->repo, copyOferta);
    add_vector(controller->undo, to_undo);
    Oferta* of2 = set_vector_element(controller->repo, id, of1);
    delete_Oferta(of2);
    return 1;
}

int remove_of(Controller* controller, int id)
{
    if (id >= 0 && id < controller->repo->size)
    {
        Vector* to_undo = copyList(controller->repo, copyOferta);
        add_vector(controller->undo, to_undo);
        Oferta* of = remove_vector_element(controller->repo,id);
        delete_Oferta(of);
        return 1;
    }
    return 0;
}

int compare_Oferta_by_price(Oferta* of1, Oferta* of2)
{
    if (of1->pret > of2->pret)
        return 1;
    if (of1->pret < of2->pret)
        return -1;
    return 0;
}
int compare_Oferta_by_type(Oferta* of1, Oferta* of2)
{
    return strcmp(of1->tip, of2->tip);
}

void sort(Vector* v, CompareFct cmpF) 
{
    for (int i = 0; i < v->size; i++) 
    {
        for (int j = i + 1; j < v->size; j++) 
        {
            void* of1 = get_vector_element(v, i);
            void* of2 = get_vector_element(v, j);
            if (cmpF(of1, of2) > 0) {
                set_vector_element(v, i, of2);
                set_vector_element(v, j, of1);
            }
        }
    }
}

void sort2(Vector* v, CompareFct cmpF, CompareFct cmpF2)
{
	for (int i = 0; i < v->size; i++)
	{
		for (int j = i + 1; j < v->size; j++)
		{
			void* of1 = get_vector_element(v, i);
			void* of2 = get_vector_element(v, j);
			if (cmpF(of1, of2) > 0) 
			{
				set_vector_element(v, i, of2);
				set_vector_element(v, j, of1);
			}
			else if (cmpF(of1, of2) == 0)
			{
				if (cmpF2(of1, of2) > 0) 
				{
					set_vector_element(v, i, of2);
					set_vector_element(v, j, of1);
				}
			}
		}
	}
}

Vector* sort_by_price(Controller* controller)
{
    Vector* array = copyList(controller->repo, copyOferta);
    sort(array, compare_Oferta_by_price);
    return array;
}

Vector* sort_by_tip(Controller* controller)
{
    Vector* array = copyList(controller->repo, copyOferta);
    sort(array, compare_Oferta_by_type);
    return array;
}

Vector* sort_by_tip_price(Controller* controller)
{
	Vector* array = copyList(controller->repo, copyOferta);
	sort2(array, compare_Oferta_by_type, compare_Oferta_by_price);
	return array;
}

Vector* filter_by_tip(Controller* controller, char* tip)
{
    if (strcmp(tip, "") != 0)
    {
        Vector* array = create_vector(delete_Oferta);
        for (int i = 0; i < controller->repo->size; i++)
        {
            Oferta* element = (Oferta*)get_vector_element(controller->repo, i);
            if (check_equ_string(element->tip, tip))
                add_vector(array, create_Oferta(element->tip, element->suprafata, element->adresa, element->pret));
        }
        return array;
    }
    else
        return copyList(controller->repo, copyOferta);
   }
    

Vector* filter_by_suprafata(Controller* controller, char* mini_txt, char* maxi_txt)
{
    int mini = transform_chars_to_integer(mini_txt);
    int maxi = transform_chars_to_integer(maxi_txt);
    Vector* array = create_vector(delete_Oferta);
    for (int i = 0; i < controller->repo->size; i++)
    {
        Oferta* element = (Oferta*)get_vector_element(controller->repo, i);
        if (element->suprafata >= mini && element->suprafata <= maxi)
            add_vector(array, create_Oferta(element->tip, element->suprafata, element->adresa, element->pret));
    }
    return array;
}


Vector* filter_by_pret(Controller* controller, char* mini_txt, char* maxi_txt)
{
    int mini = transform_chars_to_integer(mini_txt);
    int maxi = transform_chars_to_integer(maxi_txt);
    Vector* array = create_vector(delete_Oferta);
    for (int i = 0; i < controller->repo->size; i++)
    {
        Oferta* element = (Oferta*)get_vector_element(controller->repo, i);
        if (element->pret >= mini && element->pret <= maxi)
            add_vector(array, create_Oferta(element->tip, element->suprafata, element->adresa, element->pret));
    }
    return array;
}



Vector* filter_by_pret_multiplu(Controller* controller, char* multiplu)
{
    int mult = transform_chars_to_integer(multiplu);
    Vector* array = create_vector(delete_Oferta);
    for (int i = 0; i < controller->repo->size; i++)
    {
        Oferta* element = (Oferta*)get_vector_element(controller->repo, i);
        if (element->pret % mult == 0)
            add_vector(array, create_Oferta(element->tip, element->suprafata, element->adresa, element->pret));
    }
    return array;

}

int undo(Controller* controller)
{
    if (controller->undo == 0)
        return 0;
    Vector* vec = remove_vector_element(controller->undo, controller->undo->size - 1);
    destroy_vector(controller->repo);
    controller->repo = vec;
    return 1;
}

void test_add() 
{
	Controller controller = create_Controller();
	int done = add_oferta(&controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
	assert(done == 1);
	int done2 = add_oferta(&controller, "", 25, "Cluj,str.Libertatii,nr.1", 550);
	assert(done2 == 0);
	int done3 = add_oferta(&controller, "apartament", 25, "", 550);
	assert(done3 == 0);
	int done4 = add_oferta(&controller, "apartament", -25, "Cluj,str.Libertatii,nr.1", 550);
	assert(done4 == 0);
	int done5 = add_oferta(&controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", -550);
	assert(done5 == 0);
	destroy_Controller(&controller);
}

void test_edit() 
{
	Controller controller = create_Controller();
	int done = add_oferta(&controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
	assert(done == 1);
	int done2 = add_oferta(&controller, "casa", 120, "Cluj,str.Aurel Vlaicu,nr.184", 250);
	assert(done2 == 1);
	assert(controller.repo->size == 2);
	int editare = edit(&controller,1, "casa", 175, "Alba-Iulia,str.Neagra,nr.5", 1550);
	assert(editare == 1);
	int editare2 = edit(&controller,1, "", 175, "Alba-Iulia,str.Neagra,nr.5", 1550);
	assert(editare2 == 0);
	destroy_Controller(&controller);
}

void test_remove() 
{
	Controller controller = create_Controller();
	int done = add_oferta(&controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
	assert(done == 1);
	int done2 = add_oferta(&controller, "casa", 120, "Cluj,str.Aurel Vlaicu,nr.184", 250);
	assert(done2 == 1);
	assert(controller.repo->size == 2);
	int sters = remove_of(&controller,1);
	assert(sters == 1);
	assert(controller.repo->size == 1);
	int sters2 = remove_of(&controller, 1);
	assert(sters2 == 0);
	int sters3 = remove_of(&controller, 1);
	assert(sters3 == 0);
	destroy_Controller(&controller);
}

void test_filtrare() 
{
	Controller controller = create_Controller();
	int done = add_oferta(&controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
	assert(done == 1);
	int done2 = add_oferta(&controller, "casa", 120, "Cluj,str.Aurel Vlaicu,nr.184", 250);
	assert(done2 == 1);
	int done3 = add_oferta(&controller, "casa", 175, "Alba-Iulia,str.Neagra,nr.5", 1550);
	assert(done3 == 1);
	int done4 = add_oferta(&controller, "teren", 375, "Sibiu,str.Rovine,nr.12", 5550);
	assert(done4 == 1);
	assert(controller.repo->size == 4);
	Vector* vec = filter_by_tip(&controller, "casa");
	assert(vec->size == 2);
	destroy_vector(vec);
	Vector* vec2 = filter_by_tip(&controller, "teren");
	assert(vec2->size == 1);
	destroy_vector(vec2);
	Vector* vec3 = filter_by_pret(&controller, "100", "700");
	assert(vec3->size == 2);
	destroy_vector(vec3);
	Vector* vec4 = filter_by_pret(&controller, "100", "200");
	assert(vec4->size == 0);
	destroy_vector(vec4);
	Vector* vec5 = filter_by_pret_multiplu(&controller, "3");
	assert(vec5->size == 1);
	destroy_vector(vec5);
	Vector* vec6 = filter_by_suprafata(&controller, "50", "200");
	assert(vec6->size == 2);
	destroy_vector(vec6);
	Vector* vec7 = filter_by_suprafata(&controller, "50", "80");
	assert(vec7->size == 0);
	destroy_vector(vec7);
	destroy_Controller(&controller);
}

void test_cmp()
{
//Controller controller = create_Controller();
	Oferta* of1 = create_Oferta("teren", 52, "Sibiu,str.Luncii,nr.14", 350);
	Oferta* of2 = create_Oferta("apartament", 25, "Cluj,str.Libertatii,nr.1", 350);
	assert(compare_Oferta_by_price(of1,of2)==0);
}

void test_sort_tip() 
{
	Controller controller = create_Controller();
	int done = add_oferta(&controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
	assert(done == 1);
	int done2 = add_oferta(&controller, "casa", 120, "Cluj,str.Aurel Vlaicu,nr.184", 250);
	assert(done2 == 1);
	int done3 = add_oferta(&controller, "casa", 175, "Alba-Iulia,str.Neagra,nr.5", 1550);
	assert(done3 == 1);
	int done4 = add_oferta(&controller, "teren", 375, "Sibiu,str.Rovine,nr.12", 5550);
	assert(done4 == 1);
	assert(controller.repo->size == 4);

	Vector* vec = sort_by_tip(&controller);
	Oferta* o1 = get_vector_element(vec, 0);
	Oferta* o2 = get_vector_element(vec, 1);
	Oferta* o3 = get_vector_element(vec, 2);
	Oferta* o4 = get_vector_element(vec, 3);
	assert(strcmp(o1->tip, "apartament") == 0);
	assert(strcmp(o2->tip, "casa") == 0);
	assert(strcmp(o3->tip, "casa") == 0);
	assert(strcmp(o4->tip, "teren") == 0);
	destroy_vector(vec);
	destroy_Controller(&controller);
}

void test_sort_tip_pret()
{
	Controller controller = create_Controller();
	int done = add_oferta(&controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
	assert(done == 1);
	int done3 = add_oferta(&controller, "casa", 175, "Alba-Iulia,str.Neagra,nr.5", 1550);
	assert(done3 == 1);
	int done2 = add_oferta(&controller, "casa", 120, "Cluj,str.Aurel Vlaicu,nr.184", 250);
	assert(done2 == 1);
	int done4 = add_oferta(&controller, "teren", 375, "Sibiu,str.Rovine,nr.12", 5550);
	assert(done4 == 1);
	assert(controller.repo->size == 4);

	Vector* vec = sort_by_tip_price(&controller);
	Oferta* o1 = get_vector_element(vec, 0);
	Oferta* o2 = get_vector_element(vec, 1);
	Oferta* o3 = get_vector_element(vec, 2);
	Oferta* o4 = get_vector_element(vec, 3);
	assert(strcmp(o1->tip, "apartament") == 0);
	assert(strcmp(o2->tip, "casa") == 0);
	assert(strcmp(o3->tip, "casa") == 0);
	assert(o2->pret==250);
	assert(o3->pret==1550);
	assert(o1->pret == 550);
	assert(o4->pret == 5550);
	assert(strcmp(o4->tip, "teren") == 0);
	destroy_vector(vec);
	destroy_Controller(&controller);
}

void test_sort_tip_pret2()
{
	Controller controller = create_Controller();
	int done = add_oferta(&controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
	assert(done == 1);
	int done2 = add_oferta(&controller, "casa", 120, "Cluj,str.Aurel Vlaicu,nr.184", 250);
	assert(done2 == 1);
	int done3 = add_oferta(&controller, "casa", 175, "Alba-Iulia,str.Neagra,nr.5", 1550);
	assert(done3 == 1);
	int done4 = add_oferta(&controller, "teren", 375, "Sibiu,str.Rovine,nr.12", 5550);
	assert(done4 == 1);
	assert(controller.repo->size == 4);

	Vector* vec = sort_by_tip_price(&controller);
	Oferta* o1 = get_vector_element(vec, 0);
	Oferta* o2 = get_vector_element(vec, 1);
	Oferta* o3 = get_vector_element(vec, 2);
	Oferta* o4 = get_vector_element(vec, 3);
	assert(strcmp(o1->tip, "apartament") == 0);
	assert(strcmp(o2->tip, "casa") == 0);
	assert(strcmp(o3->tip, "casa") == 0);
	assert(strcmp(o4->tip, "teren") == 0);
	destroy_vector(vec);
	destroy_Controller(&controller);
}

void test_sort_pret()
{
	Controller controller = create_Controller();
	int done = add_oferta(&controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
	assert(done == 1);
	int done2 = add_oferta(&controller, "casa", 120, "Cluj,str.Aurel Vlaicu,nr.184", 250);
	assert(done2 == 1);
	int done3 = add_oferta(&controller, "casa", 175, "Alba-Iulia,str.Neagra,nr.5", 1550);
	assert(done3 == 1);
	int done4 = add_oferta(&controller, "teren", 375, "Sibiu,str.Rovine,nr.12", 5550);
	assert(done4 == 1);
	assert(controller.repo->size == 4);

	Vector* vec = sort_by_price(&controller);
	Oferta* o1 = get_vector_element(vec, 0);
	Oferta* o2 = get_vector_element(vec, 1);
	Oferta* o3 = get_vector_element(vec, 2);
	Oferta* o4 = get_vector_element(vec, 3);
	assert(o1->pret==250);
	assert(o2->pret==550);
	assert(o3->pret==1550);
	assert(o4->pret==5550);
	destroy_vector(vec);
	destroy_Controller(&controller);
}

void test_undo() 
{
	Controller controller = create_Controller();
	assert(add_oferta(&controller, "apartament", 25, "Cluj,str.Libertatii,nr.1", 550) == 1);
	assert(edit(&controller, 0, "casa", 175, "Alba-Iulia,str.Neagra,nr.5", 1550) == 1);
	assert(remove_of(&controller,0) == 1);
	undo(&controller);
	Vector* vec = filter_by_tip(&controller, "");
	assert(vec->size == 1);
	Oferta* o = get_vector_element(vec, 0);
	assert(o->pret == 1550);
	destroy_vector(vec);
	undo(&controller);
	Vector* vec3 = filter_by_tip(&controller, "");
	assert(vec3->size == 1);
	Oferta* o1 = get_vector_element(vec3, 0);
	assert(o1->pret == 550);
	destroy_vector(vec3);
	undo(&controller);
	Vector* vec2 = filter_by_tip(&controller, "");
	assert(vec2->size == 0);
	destroy_vector(vec2);
	destroy_Controller(&controller);
	//assert(undo(&controller) == 0);
}

void test_controller()
{
	test_transform_chars_to_integer();
	test_add();
	test_edit();
	test_remove();
	test_sort_pret();
	test_sort_tip();
	test_filtrare();
	test_undo();
	test_cmp();
	test_sort_tip_pret();
	test_sort_tip_pret2();

}