#pragma once
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
#include <QStringList>
#include "service.h"
#include "CosCRUDGUI.h"
#include "CosReadOnlyGUI.h"

using std::vector;
using std::string;
class FilmGUI : public QWidget
{
private:

	FilmServ& serv;
	QListView* lista_V;
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

	QPushButton* btn_actiune;
	QPushButton* btn_horror;
	QPushButton* btn_dragoste;
	QPushButton* btn_comedie;
	QPushButton* btn_altele;

	QPushButton* btn_undo;

	QTableWidget* tabel_filme;
	QListWidget* lista_filme;

	QPushButton* btn_cos_filme;
	QWidget* fereastra_cos;
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

	CosGUILista* fereastra;
	HistogramGUI* fereastra_figuri;

	QPushButton* btn_add_cos;
	QPushButton* btn_sterge_cos;
	QPushButton* btn_random_cos;

	void initializare_componente_GUI();
	void conectare_signals_slots();
	void reload_lista_filme(vector<Film> filme);
	void reload_cos_filme(vector<Film> filme);
public:
	FilmGUI(FilmServ& serv) : serv{ serv }
	{
		initializare_componente_GUI();
		conectare_signals_slots();
		reload_lista_filme(serv.getAll());
	}

	int getSelectedIndex()
	{
		auto currentIndex = this->lista_filme->selectionModel()->currentIndex();
		return currentIndex.row();
	}

	void GUI_adauga();
	void GUI_modifica();
	void GUI_sterge();

};