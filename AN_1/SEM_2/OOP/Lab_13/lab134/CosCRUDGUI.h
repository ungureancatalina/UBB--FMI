#pragma once
#include <qwidget.h>
#include <qtimer.h>
#include <QtWidgets/QHBoxLayout>
#include <qpushbutton.h>
#include <qlistwidget.h>
#include <qtablewidget.h>
#include <qstring.h>
#include <qlabel.h>
#include <vector>
#include "cosfilm.h"
#include "observer.h"

class CosGUILista : public QWidget, public Observer 
{
private:
    CosFilm& cos;
    QListWidget* lista;
    QPushButton* btnGolire;
    QPushButton* btnRandom;

    void load(const std::vector<Film>& filme) {
        lista->clear();
        for (const auto& f : filme) {
            auto item = new QListWidgetItem(QString::fromStdString(f.get_titlu() + " "+ f.get_gen() + " "+ f.get_actor() + " " + std::to_string(f.get_an())));
            lista->addItem(item);
        }
    }

    void initializare_gui() {
        QVBoxLayout* layout = new QVBoxLayout;
        lista = new QListWidget;
        layout->addWidget(lista);

        btnGolire = new QPushButton("Goleste cos");
        layout->addWidget(btnGolire);

        btnRandom = new QPushButton("Adauga random 3 filme");
        layout->addWidget(btnRandom);

        setLayout(layout);
    }

    void conectare() {
        cos.adauga_obs(this);

        QObject::connect(btnGolire, &QPushButton::clicked, [&]() {
            cos.golire_cos();
            load(cos.getAll_cos());
            });

        QObject::connect(btnRandom, &QPushButton::clicked, [&]() {
            cos.umple_cos(3);
            load(cos.getAll_cos());
            });
    }

public:
    CosGUILista(CosFilm& cos) : cos{ cos } {
        initializare_gui();
        conectare();
        load(cos.getAll_cos());
    }

    void update() override {
        load(cos.getAll_cos());
    }

    ~CosGUILista() {
        cos.sterge_obs(this);
    }
};
