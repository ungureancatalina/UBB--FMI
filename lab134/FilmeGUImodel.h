#pragma once
#include <qwidget.h>
#include <qlistwidget.h>
#include <qlistview.h>
#include <qpushbutton.h>
#include <qlabel.h>
#include <qlineedit.h>
#include <qtablewidget.h>
#include <vector>
#include <vector>
#include <string>
#include <QtWidgets/QApplication>
#include <QLabel>
#include <QPushButton>
#include <QHBoxLayout>
#include <QFormLayout>
#include <QLineEdit>
#include <QTableWidget>
#include <QMessageBox>
#include <QHeaderView>
#include <QGroupBox>
#include <QRadioButton>
#include <qlistwidget.h>
#include "service.h"
#include "domain.h"
#include "modeltabel.h"
#include "CosCRUDGUI.h"
#include "CosReadOnlyGUI.h"

class FilmeGUIModel : public QWidget
{
private:
	FilmServ& serv;
	ModelTabel* model_tabel;
	QListView* lista_V = new QListView;

	QTableView* tabel_V = new QTableView;

	QLabel* lbl_titlu = new QLabel{ "Titlul:" };
	QLabel* lbl_gen = new QLabel{ "Genul:" };
	QLabel* lbl_actor = new QLabel{ "Actorul:" };
	QLabel* lbl_an = new QLabel{ "Anul:" };

	QLineEdit* edit_titlu;
	QLineEdit* edit_gen;
	QLineEdit* edit_actor;
	QLineEdit* edit_an;

	QPushButton* btn_adauga;
	QPushButton* btn_modifica;
	QPushButton* btn_sterge;

	QGroupBox* grup = new QGroupBox(tr("Sortari dupa: "));

	QRadioButton* rbtn_sortare_titlu = new QRadioButton(QString::fromStdString("Titlu"));
	QRadioButton* rbtn_sortare_actor = new QRadioButton(QString::fromStdString("Actor"));
	QRadioButton* rbtn_sortare_an_gen = new QRadioButton(QString::fromStdString("An si gen"));
	QPushButton* btn_sortare;

	QLabel* lbl_filtrare = new QLabel{ "Titlul filtrat:" };
	QLineEdit* edit_titlu_filtrare;
	QPushButton* btn_filtrare;
	QLabel* lbl_filtrare2 = new QLabel{ "Anul filtrat:" };
	QLineEdit* edit_an_filtrare;
	QPushButton* btn_filtrare2;

	QPushButton* btn_reload_data;
	QPushButton* btn_undo;

	QPushButton* btn_actiune;
	QPushButton* btn_horror;
	QPushButton* btn_dragoste;
	QPushButton* btn_comedie;
	QPushButton* btn_altele;

	QPushButton* btn_cos_filme;
	QPushButton* btn_adauga_cos;
	QLineEdit* edit_titlu_cos;
	QPushButton* btn_adauga_random_cos;
	QLineEdit* edit_numar_random;
	QPushButton* btn_goleste_cos;
	QListWidget* lista_cos_filme;
	QLineEdit* edit_export_cos;
	QPushButton* btn_export_cos;


	QLabel* lbl_numar_random = new QLabel{ "Numarul random:" };
	QLabel* lbl_titlu_cos = new QLabel{ "Titlu film: " };
	QLabel* lbl_nume_fisier_export = new QLabel{ "Numele fisierului:" };

	QPushButton* btn_CosCRUGUI;
	QPushButton* btn_CosReadOnlyGUI;

	QPushButton* btn_add_cos;
	QPushButton* btn_sterge_cos;
	QPushButton* btn_random_cos;

	void initializare_gui_comp();
	void conectare_ss();
	void reload_lista_filme(std::vector<Film> filme);
	void gui_adauga_film();
	void gui_modifica_film();
	void gui_sterge_film();
public:
	FilmeGUIModel(FilmServ& serv) :serv{ serv } {
		initializare_gui_comp();
		model_tabel = new ModelTabel{ serv.getAll() };
		tabel_V->setModel(model_tabel);
		conectare_ss();
	}

};
