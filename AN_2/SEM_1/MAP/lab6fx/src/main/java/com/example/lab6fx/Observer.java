package com.example.lab6fx;

import com.example.lab6fx.event.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
