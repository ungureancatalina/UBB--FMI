#include "FilmeGUImodel.h"

void FilmeGUIModel::initializare_gui_comp()
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
	//edit_numar_random = new QLineEdit;
	layout_forma->addRow(lbl_titlu, edit_titlu);
	layout_forma->addRow(lbl_gen, edit_gen);
	layout_forma->addRow(lbl_actor, edit_actor);
	layout_forma->addRow(lbl_an, edit_an);
	//layout_forma->addRow(lbl_numar_random, edit_numar_random);

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

	btn_undo = new QPushButton("Undo:");
	layout_stang->addWidget(btn_undo);

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


	btn_cos_filme = new QPushButton("Cos de filme");
	btn_adauga_cos = new QPushButton("Adauga film in cos:");
	edit_titlu_cos = new QLineEdit;
	btn_adauga_random_cos = new QPushButton("Adauga random filme in cos:");
	edit_numar_random = new QLineEdit;
	btn_goleste_cos = new QPushButton("Goleste cos:");
	edit_export_cos = new QLineEdit;
	this->lista_cos_filme = new QListWidget();
	btn_export_cos = new QPushButton("Exporta cos:");

	QWidget* dreapta = new QWidget;
	QVBoxLayout* layout_drept = new QVBoxLayout;
	dreapta->setLayout(layout_drept);

	layout_drept->addWidget(tabel_V);

	btn_add_cos = new QPushButton("Adauga Film Cos");
	btn_sterge_cos = new QPushButton("Sterge Film Cos");
	//btn_random_cos = new QPushButton("Adauga Filme Random Cos");
	layout_drept->addWidget(btn_add_cos);
	layout_drept->addWidget(btn_sterge_cos);
	//layout_drept->addWidget(btn_random_cos);

	layout_drept->addWidget(btn_cos_filme);

	btn_CosCRUGUI = new QPushButton("CosCRUDGui");
	layout_drept->addWidget(btn_CosCRUGUI);

	btn_CosReadOnlyGUI = new QPushButton("CosReadOnlyGUI");
	layout_drept->addWidget(btn_CosReadOnlyGUI);

	layout_main->addWidget(stanga);
	layout_main->addWidget(dreapta);
}

void FilmeGUIModel::conectare_ss()
{
	QObject::connect(btn_adauga, &QPushButton::clicked, this, &FilmeGUIModel::gui_adauga_film);
	QObject::connect(btn_modifica, &QPushButton::clicked, this, &FilmeGUIModel::gui_modifica_film);
	QObject::connect(btn_sterge, &QPushButton::clicked, this, &FilmeGUIModel::gui_sterge_film);
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

	QObject::connect(tabel_V->selectionModel(), &QItemSelectionModel::selectionChanged, [this]() {
		if (tabel_V->selectionModel()->selectedIndexes().isEmpty()) {
			edit_titlu->setText("");
			edit_gen->setText("");
			edit_actor->setText("");
			edit_an->setText("");
			return;
		}
		int selRow = tabel_V->selectionModel()->selectedIndexes().at(0).row();
		auto cel0Index = tabel_V->model()->index(selRow, 0);
		auto cel0Value = tabel_V->model()->data(cel0Index, Qt::DisplayRole).toString();
		edit_titlu->setText(cel0Value);
		auto cel1Index = tabel_V->model()->index(selRow, 1);
		auto cel1Value = tabel_V->model()->data(cel1Index, Qt::DisplayRole).toString();
		edit_gen->setText(cel1Value);
		auto cel2Index = tabel_V->model()->index(selRow, 2);
		auto cel2Value = tabel_V->model()->data(cel2Index, Qt::DisplayRole).toString();
		edit_actor->setText(cel2Value);
		auto cel3Index = tabel_V->model()->index(selRow, 3);
		auto cel3Value = tabel_V->model()->data(cel3Index, Qt::DisplayRole).toString();
		edit_an->setText(cel3Value);
		});

	QObject::connect(btn_cos_filme, &QPushButton::clicked, [&]()
		{
			QWidget* fereastra = new QWidget;
			QFormLayout* layout_cos = new QFormLayout;
			fereastra->setLayout(layout_cos);
			//layout_cos->addWidget(lista_V);
			edit_titlu_cos = new QLineEdit;
			layout_cos->addRow(lbl_titlu_cos, edit_titlu_cos);
			layout_cos->addWidget(btn_adauga_cos);
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
		catch (ExceptieRepo& ex)
		{
			QMessageBox::warning(this, "Eroare", QString::fromStdString(ex.get_mesaj()));
		}
		});
	QObject::connect(btn_add_cos, &QPushButton::clicked, [&]() {
		try {
			string titlu = edit_titlu->text().toStdString();
			edit_titlu->clear();
			this->serv.cos_adauga(titlu);
			QMessageBox::information(this, "Info", QString::fromStdString("Film adaugat"));
		}
		catch (ExceptieRepo& ex)
		{
			QMessageBox::warning(this, "Eroare", QString::fromStdString(ex.get_mesaj()));
		}
		});
	QObject::connect(btn_sterge_cos, &QPushButton::clicked, [&]() {
		try {
			string titlu = edit_titlu->text().toStdString();
			edit_titlu->clear();
			this->serv.cos_sterge(titlu);
			QMessageBox::information(this, "Info", QString::fromStdString("Film sters"));

		}
		catch (ExceptieRepo& ex)
		{
			QMessageBox::warning(this, "Eroare", QString::fromStdString(ex.get_mesaj()));
		}
		});

	QObject::connect(btn_adauga_random_cos, &QPushButton::clicked, [&]() {
		try {
			int nr = edit_numar_random->text().toInt();
			edit_numar_random->clear();
			int numar = this->serv.getAll_cos().size();
			int of = this->serv.cos_adauga_random(nr);
			QMessageBox::information(this, "Info", QString::fromStdString("Filme random adaugate"));

		}
		catch (ExceptieRepo& ex)
		{
			QMessageBox::warning(this, "Eroare", QString::fromStdString(ex.get_mesaj()));
		}
		});
	QObject::connect(btn_goleste_cos, &QPushButton::clicked, [&]() {
		try {
			this->serv.goleste_cos();
			QMessageBox::information(this, "Info", QString::fromStdString("Filme sterse"));
		}
		catch (ExceptieRepo& ex)
		{
			QMessageBox::warning(this, "Eroare", QString::fromStdString(ex.get_mesaj()));
		}
		});
	QObject::connect(btn_export_cos, &QPushButton::clicked, [&]() {
		try {
			string fisier = edit_export_cos->text().toStdString();
			edit_export_cos->clear();
			this->serv.cos_export(fisier);
			lista_cos_filme->clear();
			this->serv.goleste_cos();
		}
		catch (ExceptieRepo& ex)
		{
			QMessageBox::warning(this, "Eroare", QString::fromStdString(ex.get_mesaj()));
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

void FilmeGUIModel::reload_lista_filme(std::vector<Film> filme)
{
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

	model_tabel->set_filme(filme);
}

void FilmeGUIModel::gui_adauga_film()
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

void FilmeGUIModel::gui_modifica_film()
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

void FilmeGUIModel::gui_sterge_film()
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

