#pragma once
template <typename Element>
class IteratorVector;

template <typename Element>
class VectorDinamic
{
public:
	VectorDinamic();
	VectorDinamic(const VectorDinamic& obiect);
	~VectorDinamic();
	VectorDinamic& operator=(const VectorDinamic& obiect);
	VectorDinamic(VectorDinamic&& obiect);
	VectorDinamic& operator=(VectorDinamic&& obiect);
	void push_back(const Element& element);
	Element& get(int pozitie) const noexcept;
	int size() const noexcept;
	void erase(int poz);

	friend class IteratorVector<Element>;
	IteratorVector<Element> begin() const;
	IteratorVector<Element> end() const;

private:
	int dim;
	int cap;
	Element* el;
	void redim();
};

template <typename Element>
VectorDinamic<Element>::VectorDinamic() :el{ new Element[10] }, cap{ 10 }, dim{ 0 } 
{
}
template <typename Element>
VectorDinamic<Element>::VectorDinamic(const VectorDinamic& obiect) 
{
	el = new Element[obiect.cap];
	for (int i = 0; i < obiect.dim; i++) 
	{
		el[i] = obiect.el[i];
	}
	dim = obiect.dim;
	cap = obiect.cap;
}
template <typename Element>
VectorDinamic<Element>& VectorDinamic<Element>::operator=(const VectorDinamic& obiect)
{
	if (this == &obiect) 
	{
		return *this;
	}
	delete[] el;
	el = new Element[obiect.cap];
	for (int i = 0; i < obiect.dim; i++) 
	{
		el[i] = obiect.el[i];
	}
	dim = obiect.dim;
	cap = obiect.cap;
	return *this;
}
template <typename Element>
VectorDinamic<Element>::VectorDinamic(VectorDinamic&& obiect)
{
	el = obiect.el;
	dim = obiect.dim;
	cap = obiect.cap;

	obiect.el = nullptr;
	obiect.dim = 0;
	obiect.cap = 0;
}

template<typename Element>
VectorDinamic<Element>& VectorDinamic<Element>::operator=(VectorDinamic&& obiect)
{
	if (this == &obiect) {
		return *this;
	}
	delete[] el;

	el = obiect.el;
	dim = obiect.dim;
	cap = obiect.cap;

	obiect.el = nullptr;
	obiect.dim = 0;
	obiect.cap = 0;
	return *this;

}
template <typename Element>
void VectorDinamic<Element>::push_back(const Element& element)
{
	redim();
	el[dim++] = element;
}

template <typename Element>
void VectorDinamic<Element>::erase(int pozitie)
{
	if (pozitie < 0 || pozitie >= dim) return;
	for (int i = pozitie; i < dim - 1; i++) {
		el[i] = el[i + 1];
	}
	dim--;
}

template <typename Element>
Element& VectorDinamic<Element>::get(int pozitie)const noexcept {
	return el[pozitie];
}
template <typename Element>
int VectorDinamic<Element>::size() const noexcept {
	return dim;
}
template <typename Element>
void VectorDinamic<Element>::redim()
{
	if (dim < cap)
		return;
	cap *= 2;
	Element* auxiliar = new Element[cap];
	for (int i = 0; i < dim; i++)
	{
		auxiliar[i] = el[i];
	}
	delete[] el;
	el = auxiliar;
}
template <typename Element>
VectorDinamic<Element>::~VectorDinamic() {
	delete[] el;
}


template <typename Element>
IteratorVector<Element> VectorDinamic<Element>::begin() const {
	return IteratorVector<Element>(*this);
}

template <typename Element>
IteratorVector<Element> VectorDinamic<Element>::end() const {
	return IteratorVector<Element>(*this, dim);
}



template <typename Element>
class IteratorVector {
private:
	const VectorDinamic<Element>& vector;
	int pozitie = 0;
public:
	IteratorVector(const VectorDinamic<Element>& vector) noexcept;
	IteratorVector(const VectorDinamic<Element>& vector, int pozitie) noexcept;
	Element& element()const;
	void urmator();
	Element& operator*();
	IteratorVector& operator++();
	bool operator==(const IteratorVector& obiect)noexcept;
	bool operator!=(const IteratorVector& obiect)noexcept;

};

template <typename Element>
IteratorVector<Element>::IteratorVector(const VectorDinamic<Element>& vector) noexcept : vector{ vector } {}
template <typename Element>
IteratorVector<Element>::IteratorVector(const VectorDinamic<Element>& vector, int pozitie)  noexcept : vector{ vector }, pozitie{ pozitie } {}

template <typename Element>
Element& IteratorVector<Element>::element() const {
	return vector.el[pozitie];
}
template <typename Element>
void IteratorVector<Element>::urmator()
{
	pozitie++;
}
template <typename Element>
Element& IteratorVector<Element>::operator*()
{
	return element();
}
template <typename Element>
IteratorVector<Element>& IteratorVector<Element>::operator++()
{
	urmator();
	return *this;
}
template <typename Element>
bool IteratorVector<Element>::operator==(const IteratorVector& obiect)noexcept
{
	return pozitie == obiect.pozitie;
}
template <typename Element>
bool  IteratorVector<Element>::operator!=(const IteratorVector& obiect)noexcept {
	return !(*this == obiect);
}