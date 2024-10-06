#pragma once
#include <QAbstractTableModel>
#include "domain.h"
#include <vector>
#include <qdebug.h>

using namespace std;

class ModelTabel : public QAbstractTableModel 
{
	std::vector<Film> filme;
public:
	ModelTabel(const std::vector<Film>& filme) : filme{ filme } {};

	int rowCount(const QModelIndex& parent = QModelIndex()) const override 
	{
		return filme.size();
	}
	int columnCount(const QModelIndex& parent = QModelIndex()) const override 
	{
		return 4;
	}

	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override 
	{
		if (role == Qt::DisplayRole) {
			Film f = filme[index.row()];
			if (index.column() == 0) {
				return QString::fromStdString(f.get_titlu());
			}
			else if (index.column() == 1) {
				return QString::fromStdString(f.get_gen());
			}
			else if (index.column() == 2) {
				return QString::fromStdString(f.get_actor());
			}
			else if (index.column() == 3) {
				return QString::number(f.get_an());
			}
		}
		return QVariant{};
	}

	void set_filme(const vector<Film>& filme) 
	{
		emit layoutAboutToBeChanged();
		this->filme = filme;
		emit layoutChanged();
	}
};