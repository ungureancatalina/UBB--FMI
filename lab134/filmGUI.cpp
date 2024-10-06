#include "filmGUI.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void FilmGUI::initializare_componente_GUI()
{
	QHBoxLayout* layout_main = new QHBoxLayout;
	this->setLayout(layout_main);

	QWidget* stanga = new QWidget;
	QVBoxLayout* layout_stang = new QVBoxLayout;
	stanga->setLayout(layout_stang);

	QWidget* forma = new QWidget;
	QFormLayout* layout_forma = new QFormLayout;
	forma->setLayout(layout_forma);
	edit_titlu = new QLineEdit;
	edit_gen = new QLineEdit;
	edit_actor = new QLineEdit;
	edit_an = new QLineEdit;
	edit_numar_random = new QLineEdit;
	layout_forma->addRow(lbl_titlu, edit_titlu);
	layout_forma->addRow(lbl_gen, edit_gen);
	layout_forma->addRow(lbl_actor, edit_actor);
	layout_forma->addRow(lbl_an, edit_an);
	layout_forma->addRow(lbl_numar_random, edit_numar_random);

	btn_adauga = new QPushButton("Adauga:");
	layout_forma->addWidget(btn_adauga);
	btn_modifica = new QPushButton("Modifica:");
	layout_forma->addWidget(btn_modifica);
	btn_sterge = new QPushButton("Sterge:");
	layout_forma->addWidget(btn_sterge);
	layout_stang->addWidget(forma);

	QVBoxLayout* layout_grup_radio = new QVBoxLayout;
	this->grup->setLayout(layout_grup_radio);
	layout_grup_radio->addWidget(rbtn_sortare_titlu);
	layout_grup_radio->addWidget(rbtn_sortare_actor);
	layout_grup_radio->addWidget(rbtn_sortare_an_gen);
	btn_sortare = new QPushButton("Sortare:");
	layout_grup_radio->addWidget(btn_sortare);
	layout_stang->addWidget(grup);

	QWidget* forma_filtrare = new QWidget;
	QFormLayout* layout_filtrare_forma = new QFormLayout;
	forma_filtrare->setLayout(layout_filtrare_forma);
	edit_titlu_filtrare = new QLineEdit();
	layout_filtrare_forma->addRow(lbl_filtrare, edit_titlu_filtrare);
	btn_filtrare = new QPushButton("Filtrare dupa titlu:");
	layout_filtrare_forma->addWidget(btn_filtrare);
	edit_an_filtrare = new QLineEdit();
	layout_filtrare_forma->addRow(lbl_filtrare2, edit_an_filtrare);
	btn_filtrare2 = new QPushButton("Filtrare dupa an:");
	layout_filtrare_forma->addWidget(btn_filtrare2);
	layout_stang->addWidget(forma_filtrare);

	btn_reload_data = new QPushButton("Reload:");
	layout_stang->addWidget(btn_reload_data);
	
	btn_actiune = new QPushButton("Actiune");
	btn_horror = new QPushButton("Horror");
	btn_dragoste = new QPushButton("Dragoste");
	btn_comedie = new QPushButton("Comedie");
	btn_altele = new QPushButton("Altele");
	layout_stang->addWidget(btn_actiune);
	layout_stang->addWidget(btn_horror);
	layout_stang->addWidget(btn_dragoste);
	layout_stang->addWidget(btn_comedie);
	layout_stang->addWidget(btn_altele);
	int contor = 0;
	for (int i = 0; i < serv.getAll().size(); i++)
		if (serv.getAll().at(i).get_gen() == "actiune")
			contor++;
	if (contor == 0)
		btn_actiune->setVisible(false);
	contor = 0;
	for (int i = 0; i < serv.getAll().size(); i++)
		if (serv.getAll().at(i).get_gen() == "horror")
			contor++;
	if (contor == 0)
		btn_horror->setVisible(false);
	contor = 0;
	for (int i = 0; i < serv.getAll().size(); i++)
		if (serv.getAll().at(i).get_gen() == "dragoste")
			contor++;
	if (contor == 0)
		btn_dragoste->setVisible(false);
	contor = 0;
	for (int i = 0; i < serv.getAll().size(); i++)
		if (serv.getAll().at(i).get_gen() == "comedie")
			contor++;
	if (contor == 0)
		btn_comedie->setVisible(false);
	contor = 0;
	for (int i = 0; i < serv.getAll().size(); i++)
		if (serv.getAll().at(i).get_gen() == "altele")
			contor++;
	if (contor == 0)
		btn_altele->setVisible(false);

	btn_undo = new QPushButton("Undo:");
	layout_stang->addWidget(btn_undo);

	btn_cos_filme = new QPushButton("Cos de filme");
	btn_adauga_cos = new QPushButton("Adauga film in cos:");
	edit_titlu_cos = new QLineEdit;
	btn_adauga_random_cos = new QPushButton("Adauga random filme in cos:");
	edit_numar_random = new QLineEdit;
	btn_goleste_cos = new QPushButton("Goleste cos:");
	edit_export_cos = new QLineEdit;
	this->lista_cos_filme = new QListWidget();
	btn_export_cos = new QPushButton("Exporta cos:");

	layout_stang->addWidget(btn_cos_filme);

	btn_CosCRUGUI = new QPushButton("CosCRUDGui");
	layout_stang->addWidget(btn_CosCRUGUI);

	btn_CosReadOnlyGUI = new QPushButton("CosReadOnlyGUI");
	layout_stang->addWidget(btn_CosReadOnlyGUI);

	QWidget* dreapta = new QWidget;
	QVBoxLayout* layout_drept = new QVBoxLayout;
	dreapta->setLayout(layout_drept);

	int linii = 10;
	int coloane = 4;
	this->tabel_filme = new QTableWidget{ linii, coloane };
	QStringList header_tabel;
	header_tabel << "Titlu" << "Gen" << "Actor" << "An";
	this->tabel_filme->setHorizontalHeaderLabels(header_tabel);
	this->tabel_filme->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);

	layout_drept->addWidget(tabel_filme);

	this->lista_filme = new QListWidget();
	layout_drept->addWidget(lista_filme);

	btn_add_cos = new QPushButton("Adauga Film Cos");
	btn_sterge_cos = new QPushButton("Sterge Film Cos");
	//btn_random_cos = new QPushButton("Adauga Filme Random Cos");
	layout_drept->addWidget(btn_add_cos);
	layout_drept->addWidget(btn_sterge_cos);
	//layout_drept->addWidget(btn_random_cos);

	layout_main->addWidget(stanga);
	layout_main->addWidget(dreapta);

}

void FilmGUI::conectare_signals_slots()
{
	QObject::connect(btn_adauga, &QPushButton::clicked, this, &FilmGUI::GUI_adauga);
	QObject::connect(btn_modifica, &QPushButton::clicked, this, &FilmGUI::GUI_modifica);
	QObject::connect(btn_sterge, &QPushButton::clicked, this, &FilmGUI::GUI_sterge);
	QObject::connect(btn_sortare, &QPushButton::clicked, [&]()
		{
			if (this->rbtn_sortare_titlu->isChecked())
				this->reload_lista_filme(serv.sortare_titlu());
			else if (this->rbtn_sortare_actor->isChecked())
				this->reload_lista_filme(serv.sortare_actor());
			else if (this->rbtn_sortare_an_gen->isChecked())
				this->reload_lista_filme(serv.sortare_an_gen());
		});
	QObject::connect(btn_filtrare, &QPushButton::clicked, [&]() {
		string filterC = this->edit_titlu_filtrare->text().toStdString();
		this->reload_lista_filme(serv.filtrare_titlu(filterC));
		});
	QObject::connect(btn_filtrare2, &QPushButton::clicked, [&]() {
		string filterC = this->edit_an_filtrare->text().toStdString();
		this->reload_lista_filme(serv.filtrare_an(stoi(filterC)));
		});
	QObject::connect(btn_undo, &QPushButton::clicked, [&]()
		{
			try
			{
				this->serv.undo();
				this->reload_lista_filme(this->serv.getAll());
				QMessageBox::information(this, "Info", QString::fromStdString("Undo realizat"));
			}
			catch (ExceptieRepo& ex)
			{
				QMessageBox::warning(this, "Eroare", QString::fromStdString(ex.get_mesaj()));
			}
		});

	QObject::connect(btn_reload_data, &QPushButton::clicked, [&]()
		{
			this->reload_lista_filme(serv.getAll());
		});

	QObject::connect(btn_actiune, &QPushButton::clicked, [&]()
		{
			int cnt = 0;
			for (int i = 0; i < serv.getAll().size(); i++)
				if (serv.getAll().at(i).get_gen() == "actiune")
					cnt++;
			QMessageBox::information(this, "Info", QString::fromStdString("Numarul de filme de actiune: %1").arg(cnt));
		});

	QObject::connect(btn_horror, &QPushButton::clicked, [&]()
		{
			int cnt = 0;
			for (int i = 0; i < serv.getAll().size(); i++)
				if (serv.getAll().at(i).get_gen() == "horror")
					cnt++;
			QMessageBox::information(this, "Info", QString::fromStdString("Numarul de filme horror: %1").arg(cnt));
		});

	QObject::connect(btn_dragoste, &QPushButton::clicked, [&]()
		{
			int cnt = 0;
			for (int i = 0; i < serv.getAll().size(); i++)
				if (serv.getAll().at(i).get_gen() == "dragoste")
					cnt++;
			QMessageBox::information(this, "Info", QString::fromStdString("Numarul de filme de dragoste: %1").arg(cnt));
		});

	QObject::connect(btn_comedie, &QPushButton::clicked, [&]()
		{
			int cnt = 0;
			for (int i = 0; i < serv.getAll().size(); i++)
				if (serv.getAll().at(i).get_gen() == "comedie")
					cnt++;
			QMessageBox::information(this, "Info", QString::fromStdString("Numarul de filme de comedie: %1").arg(cnt));
		});

	QObject::connect(btn_altele, &QPushButton::clicked, [&]()
		{
			int cnt = 0;
			for (int i = 0; i < serv.getAll().size(); i++)
				if (serv.getAll().at(i).get_gen() == "altele")
					cnt++;
			QMessageBox::information(this, "Info", QString::fromStdString("Numarul de filme diferite de cele anterioare: %1").arg(cnt));
		});

	QObject::connect(this->lista_filme, &QListWidget::itemSelectionChanged, [&]() {
		int index = this->getSelectedIndex();
		auto elements = this->serv.getAll();
		if (index >= elements.size()) {
			this->edit_titlu->clear();
			this->edit_gen->clear();
			this->edit_actor->clear();
			this->edit_an->clear();
			this->edit_titlu_cos->clear();
			return;
		}
		auto element = elements.at(index);
		this->edit_titlu->setText(QString::fromStdString(element.get_titlu()));
		this->edit_titlu_cos->setText(QString::fromStdString(element.get_titlu()));
		this->edit_gen->setText(QString::fromStdString(element.get_gen()));
		this->edit_actor->setText(QString::fromStdString(element.get_actor()));
		this->edit_an->setText(QString::fromStdString(std::to_string(element.get_an())));
		});

	QObject::connect(this->tabel_filme, &QTableWidget::itemClicked, [&]() {
		auto currentIndex = this->tabel_filme->selectionModel()->currentIndex();
		int index = currentIndex.row();
		auto elements = this->serv.getAll();
		if (index >= elements.size()) {
			this->edit_titlu->clear();
			this->edit_gen->clear();
			this->edit_actor->clear();
			this->edit_an->clear();
			this->edit_titlu_cos->clear();
			return;
		}
		auto element = elements.at(index);
		this->edit_titlu->setText(QString::fromStdString(element.get_titlu()));
		this->edit_titlu_cos->setText(QString::fromStdString(element.get_titlu()));
		this->edit_gen->setText(QString::fromStdString(element.get_gen()));
		this->edit_actor->setText(QString::fromStdString(element.get_actor()));
		this->edit_an->setText(QString::fromStdString(std::to_string(element.get_an())));
		});

	QObject::connect(btn_cos_filme, &QPushButton::clicked, [&]()
		{
			QWidget* fereastra = new QWidget;
			QFormLayout* layout_cos = new QFormLayout;
			fereastra->setLayout(layout_cos);
			//layout_cos->addWidget(lista_filme);
			edit_titlu_cos = new QLineEdit;
			layout_cos->addRow(lbl_titlu_cos, edit_titlu_cos);
			layout_cos->addWidget(btn_adauga_cos);
			edit_numar_random = new QLineEdit;
			layout_cos->addRow(lbl_numar_random, edit_numar_random);
			layout_cos->addWidget(btn_adauga_random_cos);
			layout_cos->addWidget(btn_goleste_cos);
			edit_export_cos = new QLineEdit;
			layout_cos->addRow(lbl_nume_fisier_export, edit_export_cos);
			layout_cos->addWidget(btn_export_cos);
			layout_cos->addWidget(lista_cos_filme);
			fereastra->show();
		});
	QObject::connect(btn_adauga_cos, &QPushButton::clicked, [&]() {
		try {
			string titlu = edit_titlu_cos->text().toStdString();
			edit_titlu_cos->clear();
			this->serv.cos_adauga(titlu);
			QMessageBox::information(this, "Info", QString::fromStdString("Film adaugat"));
			
		}
		catch (ExceptieRepo)
		{
			QMessageBox::warning(this, "Eroare", "Filmul nu exista!");
		}
		});
	QObject::connect(btn_add_cos, &QPushButton::clicked, [&]() {
		try {
			string titlu = edit_titlu_cos->text().toStdString();
			edit_titlu_cos->clear();
			this->serv.cos_adauga(titlu);
			QMessageBox::information(this, "Info", QString::fromStdString("Film adaugat"));
			
		}
		catch (ExceptieRepo)
		{
			QMessageBox::warning(this, "Eroare", "Filmul nu exista!");
		}
		});
	QObject::connect(btn_sterge_cos, &QPushButton::clicked, [&]() {
		try {
			string titlu = edit_titlu_cos->text().toStdString();
			edit_titlu_cos->clear();
			this->serv.cos_sterge(titlu);
			QMessageBox::information(this, "Info", QString::fromStdString("Film sters"));
			this->reload_cos_filme(this->serv.getAll_cos());
		}
		catch (ExceptieRepo)
		{
			QMessageBox::warning(this, "Eroare", "Filmul nu exista!");
		}
		});
	QObject::connect(btn_adauga_random_cos, &QPushButton::clicked, [&]() {
		try {
			int nr = edit_numar_random->text().toInt();
			edit_numar_random->clear();
			int numar = this->serv.getAll_cos().size();
			int f = this->serv.cos_adauga_random(nr);
			QMessageBox::information(this, "Info", QString::fromStdString("Filme adaugate"));
			this->reload_cos_filme(this->serv.getAll_cos());
		}
		catch (ExceptieRepo) {
			QMessageBox::warning(this, "Eroare", "Filmul nu exista!");
		}
		});
	QObject::connect(btn_goleste_cos, &QPushButton::clicked, [&]() {
		try {
			this->serv.goleste_cos();
			this->reload_cos_filme(this->serv.getAll_cos());
		}
		catch (ExceptieRepo) {
			QMessageBox::warning(this, "Eroare", "");
		}
		});
	QObject::connect(btn_export_cos, &QPushButton::clicked, [&]() {
		try {
			string fisier = edit_export_cos->text().toStdString();
			edit_export_cos->clear();
			this->serv.cos_export(fisier);
			lista_cos_filme->clear();
			this->serv.goleste_cos();
			this->reload_cos_filme(this->serv.getAll_cos());
		}
		catch (ExceptieRepo) {
			QMessageBox::warning(this, "Eroare", "");
		}
		});

	QObject::connect(btn_CosCRUGUI, &QPushButton::clicked, [&]() {
		auto fereastra = new CosGUILista{ serv.get_cos() };
		fereastra->show();
		});

	QObject::connect(btn_CosReadOnlyGUI, &QPushButton::clicked, [&]() {
		auto fereastra_figuri = new HistogramGUI{ serv.get_cos() };
		fereastra_figuri->show();
		});
}

void FilmGUI::reload_lista_filme(vector<Film> filme)
{
	this->tabel_filme->clearContents();
	this->tabel_filme->setRowCount(10);
	int linia = 0;
	for (auto& f : filme) {
		this->tabel_filme->setItem(linia, 0, new QTableWidgetItem(QString::fromStdString(f.get_titlu())));
		this->tabel_filme->setItem(linia, 1, new QTableWidgetItem(QString::fromStdString(f.get_gen())));
		this->tabel_filme->setItem(linia, 2, new QTableWidgetItem(QString::fromStdString(f.get_actor())));
		this->tabel_filme->setItem(linia, 3, new QTableWidgetItem(QString::number(f.get_an())));
		linia++;
	}
	int contor = 0;
	for (int i = 0; i < serv.getAll().size(); i++)
		if (serv.getAll().at(i).get_gen() == "actiune")
			contor++;
	if (contor == 0)
		btn_actiune->setVisible(false);
	else
		btn_actiune->setVisible(true);

	contor = 0;
	for (int i = 0; i < serv.getAll().size(); i++)
		if (serv.getAll().at(i).get_gen() == "horror")
			contor++;
	if (contor == 0)
		btn_horror->setVisible(false);
	else
		btn_horror->setVisible(true);

	contor = 0;
	for (int i = 0; i < serv.getAll().size(); i++)
		if (serv.getAll().at(i).get_gen() == "dragoste")
			contor++;
	if (contor == 0)
		btn_dragoste->setVisible(false);
	else
		btn_dragoste->setVisible(true);

	contor = 0;
	for (int i = 0; i < serv.getAll().size(); i++)
		if (serv.getAll().at(i).get_gen() == "comedie")
			contor++;
	if (contor == 0)
		btn_comedie->setVisible(false);
	else
		btn_comedie->setVisible(true);

	contor = 0;
	for (int i = 0; i < serv.getAll().size(); i++)
		if (serv.getAll().at(i).get_gen() == "altele")
			contor++;
	if (contor == 0)
		btn_altele->setVisible(false);
	else
		btn_altele->setVisible(true);

	this->lista_filme->clear();
	for (const auto& elem : filme) {
		auto item = new QListWidgetItem(
			QString::fromStdString(elem.get_titlu() + " "+ elem.get_gen() + " "+ elem.get_actor() + " "+ std::to_string(elem.get_an())));
		this->lista_filme->addItem(item);
	}
}

void FilmGUI::reload_cos_filme(vector<Film> filme)
{
	this->lista_cos_filme->clear();
	for (const auto& f : filme)
	{
		auto film = new QListWidgetItem(QString::fromStdString(f.get_titlu() + " " + f.get_gen() + " " + f.get_actor() + " " + std::to_string(f.get_an())));
		this->lista_cos_filme->addItem(film);
	}
}

void FilmGUI::GUI_adauga()
{
	try
	{
		string titlu = edit_titlu->text().toStdString();
		string gen = edit_gen->text().toStdString();
		string actor = edit_actor->text().toStdString();
		int an = edit_an->text().toInt();
		edit_titlu->clear();
		edit_gen->clear();
		edit_actor->clear();
		edit_an->clear();
		this->serv.adauga(titlu, gen, actor, an);
		this->reload_lista_filme(this->serv.getAll());
		QMessageBox::information(this, "Info", QString::fromStdString("Film adaugat"));

	}
	catch (ExceptieRepo)
	{
		QMessageBox::warning(this, "Eroare", "");
	}
	catch (Exceptie& e)
	{
		QMessageBox::warning(this, "Eroare", QString::fromStdString(e.get_mesaj()));
	}
}

void FilmGUI::GUI_modifica()
{
	try {
		string titlu = edit_titlu->text().toStdString();
		string gen = edit_gen->text().toStdString();
		string actor = edit_actor->text().toStdString();
		int an = edit_an->text().toInt();
		edit_titlu->clear();
		edit_gen->clear();
		edit_actor->clear();
		edit_an->clear();
		this->serv.modifica(titlu, gen, actor, an);
		this->reload_lista_filme(this->serv.getAll());
		QMessageBox::information(this, "Info", QString::fromStdString("Film modificat"));

	}
	catch (ExceptieRepo)
	{
		QMessageBox::warning(this, "Eroare", "");
	}
	catch (Exceptie& e)
	{
		QMessageBox::warning(this, "Eroare", QString::fromStdString(e.get_mesaj()));
	}
}

void FilmGUI::GUI_sterge()
{
	try {
		string titlu = edit_titlu->text().toStdString();
		string gen = edit_gen->text().toStdString();
		string actor = edit_actor->text().toStdString();
		int an = edit_an->text().toInt();
		edit_titlu->clear();
		edit_gen->clear();
		edit_actor->clear();
		edit_an->clear();

		this->serv.sterge(titlu);
		this->reload_lista_filme(this->serv.getAll());
		QMessageBox::information(this, "Info", QString::fromStdString("Film sters"));

	}
	catch (ExceptieRepo)
	{
		QMessageBox::warning(this, "Eroare", "");
	}
	catch (Exceptie& e)
	{
		QMessageBox::warning(this, "Eroare", QString::fromStdString(e.get_mesaj()));
	}
}
