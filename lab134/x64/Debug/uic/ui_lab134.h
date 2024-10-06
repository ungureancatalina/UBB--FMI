/********************************************************************************
** Form generated from reading UI file 'lab134.ui'
**
** Created by: Qt User Interface Compiler version 6.7.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_LAB134_H
#define UI_LAB134_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_lab134Class
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *lab134Class)
    {
        if (lab134Class->objectName().isEmpty())
            lab134Class->setObjectName("lab134Class");
        lab134Class->resize(600, 400);
        menuBar = new QMenuBar(lab134Class);
        menuBar->setObjectName("menuBar");
        lab134Class->setMenuBar(menuBar);
        mainToolBar = new QToolBar(lab134Class);
        mainToolBar->setObjectName("mainToolBar");
        lab134Class->addToolBar(mainToolBar);
        centralWidget = new QWidget(lab134Class);
        centralWidget->setObjectName("centralWidget");
        lab134Class->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(lab134Class);
        statusBar->setObjectName("statusBar");
        lab134Class->setStatusBar(statusBar);

        retranslateUi(lab134Class);

        QMetaObject::connectSlotsByName(lab134Class);
    } // setupUi

    void retranslateUi(QMainWindow *lab134Class)
    {
        lab134Class->setWindowTitle(QCoreApplication::translate("lab134Class", "lab134", nullptr));
    } // retranslateUi

};

namespace Ui {
    class lab134Class: public Ui_lab134Class {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_LAB134_H
