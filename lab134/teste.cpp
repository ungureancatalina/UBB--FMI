#include "teste.h"
#include "domain.h"
#include "repository.h"
#include "validator.h"
#include "service.h"
#include <iostream>
#include <assert.h>
#include <string>
#include <fstream>
#include <sstream>

void test_domain()
{
	Film f{ "A", "B", "C", 1 };
	assert(f.get_titlu() == "A");
	assert(f.get_gen() == "B");
	assert(f.get_actor() == "C");
	assert(f.get_an() == 100);

	f.set_titlu("X");
	f.set_gen("Y");
	f.set_actor("Z");
	f.set_an(9);

	assert(f.get_titlu() == "X");
	assert(f.get_gen() == "Y");
	assert(f.get_actor() == "Z");
	assert(f.get_an() == 9);
}

void test_add_repo() {
	FilmRepo repo;
	Film f{ "A", "B", "C", 1 };
	repo.adauga(f);
	assert(repo.getAll().size() == 1);
	try {
		repo.adauga(f);
		assert(false);
	}
	catch (ExceptieRepo) {
		assert(true);
	}
}

void test_delete_repo() {
	Film f1{ "A", "B", "C", 1 };
	Film f2{ "D", "E", "F", 2 };
	Film f3{ "G", "H", "I", 3 };
	FilmRepo repo;
	repo.adauga(f1);
	repo.adauga(f2);
	repo.adauga(f3);
	try {
		repo.sterge("Z");
		assert(false);
	}
	catch (ExceptieRepo) {
		assert(true);
	}
	repo.sterge("A");
	auto list = repo.getAll();
	assert(list.size() == 2);
	assert(list.at(0).get_titlu() == "D");
	assert(list.at(1).get_titlu() == "G");
}

void test_modify_repo() {
	Film f1{ "A", "B", "C", 1 };
	Film f2{ "D", "E", "F", 2 };
	Film f3{ "G", "H", "I", 3 };
	FilmRepo repo;
	repo.adauga(f1);
	repo.modifica(f2);
	auto list = repo.getAll();
	assert(list.size() == 1);
	assert(list.at(0).get_an() == 2);
	assert(list.at(0).get_titlu() == "D");
	try {
		repo.modifica(f3);
		assert(false);
	}
	catch (ExceptieRepo) {
		assert(true);
	}
}

void test_find_repo() {
	Film f1{ "A", "B", "C", 1 };
	Film f2{ "D", "E", "F", 2 };
	Film f3{ "G", "H", "I", 3 };
	FilmRepo repo;
	repo.adauga(f1);
	assert(repo.exista(f1));
	assert(!repo.exista(f2));
	repo.adauga(f2);
	repo.adauga(f3);
	assert(repo.cauta("D").get_titlu() == f2.get_titlu());
	try {
		repo.cauta("s"); 
		assert(false);
	}
	catch (ExceptieRepo) {
		assert(true);
	}
}

void test_validator() {
	Validator validator;
	Film f1{ "A", "B", "C", 1 };
	Film f2{ "", "", "", 0 };
	validator.validare(f1);
	try {
		validator.validare(f2);
		assert(false);
	}
	catch (Exceptie) {
		assert(true);
	}
}

void test_add_service() {
	FilmRepo repo;
	Validator valid;
	FilmServ service{ repo, valid };

	service.adauga("A", "B", "C", 1);
	service.adauga("D", "E", "F", 2);
	assert(service.getAll().size() == 2);
	try {
		service.adauga("A", "d", "r", 1);
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
	try {
		service.adauga("", "", "", 0);
		assert(false);
	}
	catch (Exceptie&) {
		assert(true);
	}
}

void test_modify_service() {
	FilmRepo repo;
	Validator valid;
	FilmServ service{ repo, valid };

	service.adauga("A", "B", "C", 1);
	service.adauga("D", "E", "F", 2);
	assert(service.getAll().size() == 2);
	service.modifica("A", "S", "R", 3);
	assert(service.getAll().at(0).get_an() == 3);
	try {
		service.modifica("A", "B", "C", 1);
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
	try {
		service.modifica("D", "E", "F", 0);
		assert(false);
	}
	catch (Exceptie&) {
		assert(true);
	}
}

void test_delete_service() {
	FilmRepo repo;
	Validator valid;
	FilmServ service{ repo, valid };

	service.adauga("A", "B", "C", 1);
	service.adauga("D", "E", "F", 2);
	service.adauga("G", "H", "I", 3);
	assert(service.getAll().size() == 3);
	service.sterge("D");
	assert(service.getAll().size() == 2);
	try {
		service.sterge("");
		assert(false);
	}
	catch (Exceptie&) {
		assert(true);
	}
	try {
		service.sterge("h");
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
}

void test_filter_service() {
	FilmRepo repo;
	Validator valid;
	FilmServ service{ repo, valid };

	service.adauga("A", "B", "C", 1);
	service.adauga("D", "E", "F", 2);
	service.adauga("G", "H", "I", 2);
	service.adauga("J", "K", "L", 4);
	vector<Film> f1 = service.filtrare_titlu("A");
	assert(f1.size() == 1);
	vector<Film> f2 = service.filtrare_titlu("L");
	assert(f2.size() == 0);

	vector<Film> f3 = service.filtrare_an(2);
	assert(f3.size() == 2);
	vector<Film> f4 = service.filtrare_an(5);
	assert(f4.size() == 0);
}

void test_sort_service() {
	FilmRepo repo;
	Validator valid;
	FilmServ service{ repo, valid };
	service.adauga("D", "E", "F", 2);
	service.adauga("A", "B", "C", 1);
	service.adauga("J", "K", "L", 4);
	service.adauga("G", "H", "I", 2);
	
	vector<Film>f1 = service.sortare_titlu();
	vector<Film>f2 = service.sortare_actor();
	vector<Film>f3 = service.sortare_an_gen();

	assert(f1.size() == 4);
	assert(f1.at(0).get_titlu() == "A");
	assert(f1.at(3).get_titlu() == "J");

	assert(f2.size() == 4);
	assert(f2.at(1).get_actor() == "F");
	assert(f2.at(2).get_actor() == "I");

	assert(f3.size() == 4);
	assert(f3.at(0).get_an() == 1);
	assert(f3.at(1).get_an() == 2);
}

void test_cos()
{
	FilmRepo repo;
	Validator valid;
	FilmServ service{ repo, valid };
	service.adauga("D", "E", "F", 2);
	service.adauga("A", "B", "C", 1);
	service.adauga("J", "K", "L", 4);
	service.adauga("G", "H", "I", 2);

	service.cos_adauga("A");
	assert(service.getAll_cos().size() == 1);

	service.cos_adauga_random(2);
	assert(service.getAll_cos().size() == 2);

	service.goleste_cos();
	assert(service.getAll_cos().size() == 0);
}

void test_count() {
	FilmRepo repo;
	Validator valid;
	FilmServ service{ repo, valid };
	service.adauga("D", "E", "F", 2);
	service.adauga("A", "E", "C", 1);
	service.adauga("J", "K", "L", 4);
	service.adauga("G", "H", "I", 2);
	assert(service.nr_filme_cos("E") == 2);
	assert(service.nr_filme_cos("K") == 1);
	assert(service.nr_filme_cos("S") == 0);
}

void test_export() {
	FilmRepo repo;
	Validator valid;
	FilmServ service{ repo, valid };
	service.adauga("D", "E", "F", 2);
	service.adauga("A", "B", "C", 1);
	service.adauga("J", "K", "L", 4);
	service.adauga("G", "H", "I", 2);

	service.cos_adauga("A");
	service.cos_adauga("D");
	service.cos_adauga("J");
	assert(service.getAll_cos().size() == 3);

	service.cos_export("test");

	string line;
	int cnt = 0;
	std::ifstream fin("test");
	while (std::getline(fin, line)) {
		
		cnt++;
	}
	assert(cnt == 3);
}

void test_repo_file() {
	std::ofstream ofs;
	ofs.open("test.txt", std::ofstream::out | std::ofstream::trunc);
	ofs << "Pruncul,dragoste,Carla,2005\n";
	ofs << "Marea,dragoste,Ana,2000\n";
	ofs << "Pacea,horror,Mihaela,2005\n";
	ofs << "Pas,drama,Ana,2000\n";
	ofs.close();
	try {
		FilmRepoFile testRepoF{ "test2.txt" };
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
	FilmRepoFile testRepoF{ "test.txt" };
	assert(testRepoF.getAll().size() == 4);


	auto foundof = testRepoF.cauta("Pas");
	assert(foundof.get_gen() == "drama");
	assert(foundof.get_actor() == "Ana");
	assert(foundof.get_an() == 2000);

	Film f{ "Lotul", "actiune", "Tenia", 2008 };

	testRepoF.adauga(f);
	assert(testRepoF.getAll().size() == 5);
	Film found = testRepoF.cauta("Lotul");
	assert(found.get_an() == 2008);
}

void test_all() {
	test_domain();
	test_add_repo();
	test_delete_repo();
	test_modify_repo();
	test_find_repo();
	test_validator();
	test_add_service();
	test_modify_service();
	test_delete_service();
	test_filter_service();
	test_sort_service();
	test_cos();
	test_count();
	test_export();
	test_repo_file();
}