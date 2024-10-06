#include "Lista.h"
#include <string.h>
#include <stdlib.h>

MyList createEmpty()
{
	MyList l;
	l.nr_participanti = 0;
	l.maxim = 10;
	l.participant = malloc(sizeof(ElemType) * l.maxim);
	return l;
}

void destroy(MyList* l)
{
	for (int i = 0; i < l->nr_participanti; i++)
	{
		ElemType part = get(l, i);
		distruge_participant(&part);
	}
	l->nr_participanti = 0;
	free(l->participant);
}

ElemType get(MyList* l, int pozitie)
{
	return l->participant[pozitie];
}

int size(MyList* l)
{
	return l->nr_participanti;
}

void add(MyList* l, ElemType elem)
{
	if (l->nr_participanti + 1 > l->maxim)
	{
		int maxim_nou = l->maxim * 2;
		ElemType* part_nou = malloc(sizeof(ElemType) * maxim_nou);
		if (part_nou != NULL)
		{
			for (int i = 0; i < l->nr_participanti; i++) {
				part_nou[i] = l->participant[i];
			}
		}
		free(l->participant);
		l->participant = part_nou;
		l->maxim = maxim_nou;

	}
	if (l->participant != NULL)
	{
		l->participant[l->nr_participanti] = elem;
		l->nr_participanti++;
	}
}

ElemType delete(MyList* l, int pozitie)
{
	ElemType part = l->participant[pozitie];
	for (int i = pozitie; i < l->nr_participanti - 1; i++)
		l->participant[i] = l->participant[i + 1];
	l->nr_participanti--;
	return part;
}
