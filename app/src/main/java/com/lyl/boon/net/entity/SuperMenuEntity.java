package com.lyl.boon.net.entity;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class SuperMenuEntity extends BaseEntity {

    private String title;
    private int type;

    public SuperMenuEntity(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}