package com.lyl.boon.entity;

import java.util.List;

/**
 * Wing_Li
 * 2016/3/30.
 */
public class BaseGankEntiry<T> extends BaseEntiry {
    private boolean error;
    private T results;
    private List<String> category;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
