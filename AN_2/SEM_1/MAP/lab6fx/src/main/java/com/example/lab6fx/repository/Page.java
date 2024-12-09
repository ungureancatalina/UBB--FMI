package com.example.lab6fx.repository;

public class Page<E> {
    private Iterable<E> elementsOnPage;
    private int totalElementCount;

    public Page(Iterable<E> elementsOnPage, int totalElementCount) {
        this.elementsOnPage = elementsOnPage;
        this.totalElementCount = totalElementCount;
    }

    public Iterable<E> getElementsOnPage() {
        return elementsOnPage;
    }

    public int getTotalElementCount() {
        return totalElementCount;
    }
}