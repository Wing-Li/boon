package com.lyl.boon.net.entity;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class BaseTngouEntiry<T> extends BaseEntiry{

    private boolean status;
    private T tngou;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getTngou() {
        return tngou;
    }

    public void setTngou(T tngou) {
        this.tngou = tngou;
    }

}
