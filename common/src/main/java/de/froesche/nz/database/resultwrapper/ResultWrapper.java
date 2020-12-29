package de.froesche.nz.database.resultwrapper;

public abstract class ResultWrapper<T> {
    T result;

    public ResultWrapper(T result){
        this.result = result;
    }

    public T getResult(){
        return result;
    }
}
