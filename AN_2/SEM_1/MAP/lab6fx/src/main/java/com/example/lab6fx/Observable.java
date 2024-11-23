package com.example.lab6fx;

import com.example.lab6fx.event.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E event);
}
