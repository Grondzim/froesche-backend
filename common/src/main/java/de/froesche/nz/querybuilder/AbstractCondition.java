package de.froesche.nz.querybuilder;

public abstract class AbstractCondition<S, T> implements IsCondition<T> {
    private S column;
    private T value;

    AbstractCondition(S column, T value){
        this.column =column;
        this.value = value;
    }

    public S getColumn() {
        return column;
    }

    @Override
    public T getValue() {
        return value;
    }

}