#pragma once
#include <qwidget.h>
#include <qpainter.h>
#include "qdebug.h"
#include "cosfilm.h"

class HistogramGUI : public QWidget, public Observer
{
private:
    CosFilm& cos;

public:
    HistogramGUI(CosFilm& cos) : cos{ cos }
    {
        cos.adauga_obs(this);
    }

    void update() override
    {
        repaint();
    }

    void paintEvent(QPaintEvent* ev) override
    {
        QPainter p{ this };
        srand(time(NULL));
        for (const auto& u : cos.getAll_cos())
        {
            int x = rand() % 300;
            int y = rand() % 300;
            qDebug() << x << " " << y << "\n";
            p.drawRect(x, y, 20, u.get_an() / 10);
            p.drawImage(x, y, QImage("C:/Users/ungur/OneDrive/Desktop/SEM_2/oop/lab/1314/lab134/lab134/poza.jpeg"));
        }
    }
};
