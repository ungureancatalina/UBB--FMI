#include <QtWidgets/QApplication>
#include "FilmeGUImodel.h"
#include "CosCRUDGUI.h"
#include "filmGUI.h"
#include "teste.h"
#include "CosReadOnlyGUI.h"
#include <stdlib.h>
#include <iostream>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QtWidgets/QTextEdit>
#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qtablewidget.h>
#include <QtWidgets/qmessagebox.h>
#include <qlist.h>
#include <string>
#include <list>
#include <QTreeView>
#include <QListView>
#include <QStringListModel>
#include <QVBoxLayout>
#include <QComboBox>
#include <qdebug.h>

using std::cout;
using std::endl;

int randuri = 10000;
int coloane = 100;

int main(int argc, char* argv[])
{
	//teste();
	FilmRepoFile repo{ "C:/Users/ungur/OneDrive/Desktop/SEM_2/oop/lab/1314/lab134/lab134/filme.txt" };
	Validator validator;
	CosFilm cos(repo);
	FilmServ serv(repo, validator);
	QApplication a(argc, argv);
	FilmeGUIModel gui(serv);
	gui.show();
	return a.exec();

}