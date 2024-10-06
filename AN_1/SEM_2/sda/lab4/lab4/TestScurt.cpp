#include "TestScurt.h"
#include "MD.h"
#include "IteratorMD.h"
#include <assert.h>
#include <vector>
#include<iostream>

void testAll() {
	MD m;
	m.adauga(1, 100);
	m.adauga(2, 200);
	m.adauga(3, 300);
	m.adauga(1, 500);
	m.adauga(2, 600);
	m.adauga(4, 800);

	assert(m.dim() == 6);

	assert(m.sterge(5, 600) == false);
	assert(m.sterge(1, 500) == true);

	assert(m.dim() == 5);

	vector<TValoare> v;
	v = m.cauta(6);
	assert(v.size() == 0);

	v = m.cauta(1);
	assert(v.size() == 1);

	assert(m.vid() == false);

	IteratorMD im = m.iterator();
	assert(im.valid() == true);
	while (im.valid()) {
		im.element();
		im.urmator();
	}
	assert(im.valid() == false);
	im.prim();
	assert(im.valid() == true);
}

void testMultimeaCheilor()
{
    MD md1;
    md1.adauga(1, 100);
    md1.adauga(2, 200);
    md1.adauga(1, 101);
    md1.adauga(3, 300);
    md1.adauga(2, 201);
    md1.adauga(4, 400);
    md1.adauga(3, 301);

    vector<TCheie> chei1 = md1.multimeaCheilor();
    vector<TCheie> expected1 = { 1, 2, 3, 4 };

    sort(chei1.begin(), chei1.end());
    sort(expected1.begin(), expected1.end());

    assert(chei1 == expected1);

    MD md2;
    md2.adauga(1, 100);
    md2.adauga(1, 101);
    md2.adauga(1, 102);
    md2.adauga(2, 200);
    md2.adauga(2, 201);
    md2.adauga(3, 300);

    vector<TCheie> chei2 = md2.multimeaCheilor();
    vector<TCheie> expected2 = { 1, 2, 3 };

    sort(chei2.begin(), chei2.end());
    sort(expected2.begin(), expected2.end());

    assert(chei2 == expected2);

    MD md3;
    vector<TCheie> chei3 = md3.multimeaCheilor();
    vector<TCheie> expected3 = {};

    assert(chei3 == expected3);
}