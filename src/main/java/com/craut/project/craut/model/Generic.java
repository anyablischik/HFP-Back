package com.craut.project.craut.model;

public class Generic<T> {

    private T t;

    public Generic(T t) {
        this.t = t;
    }

    public T getComponent(){
        return this.t;
    }

    public void setComponent(T t1){
        this.t=t1;
    }
}
