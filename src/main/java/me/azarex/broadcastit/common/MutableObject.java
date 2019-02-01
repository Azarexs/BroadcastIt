package me.azarex.broadcastit.common;

import java.util.Objects;

public class MutableObject<T> implements Mutable<T> {

    private T value;

    public MutableObject() {
        this(null);
    }

    public MutableObject(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableObject<?> that = (MutableObject<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
