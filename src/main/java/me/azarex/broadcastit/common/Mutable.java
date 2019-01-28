package me.azarex.broadcastit.common;

public interface Mutable<T> {

    T getValue();
    void setValue(T value);
}
