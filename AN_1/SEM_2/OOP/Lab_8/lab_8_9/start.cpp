#include "ui.h"
#include "service.h"
#include "domain.h"
#include "validator.h"
#include "teste.h"

int main()
{
	teste();
	string fisier = "C:/Users/ungur/OneDrive/Desktop/SEM_2/oop/lab/89/lab_8_9/filme.txt";
	//FilmRepoFile repo{ fisier };
	RepoNou repo;
	Validator valid;
	CosFilme cos;
	FilmServ serv(repo, valid, cos);
	Ui ui(serv);
	ui.run();
}