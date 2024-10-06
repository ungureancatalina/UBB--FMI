#include <QtWidgets/QApplication>
#include <QLabel>
#include "ui.h"
#include "repository.h"
#include "service.h"
#include "validator.h"
#include "teste.h"
#include "filmGUI.h"

//int main()
int main(int argc, char* argv[])
{
	teste();
	QApplication a(argc, argv);
	string fisier = "C:/Users/ungur/OneDrive/Desktop/SEM_2/oop/lab/10,11/lab_10_11/filme.txt";
	FilmRepoFile repo{ fisier };
	//RepoNou repo;
	Validator valid;
	CosFilme cos;
	FilmServ serv(repo, valid, cos);
	//Ui ui(serv);
	//ui.run();

	FilmGUI gui{ serv };
	gui.show();
	return a.exec();
}