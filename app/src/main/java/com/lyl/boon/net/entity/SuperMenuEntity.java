package com.lyl.boon.net.entity;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class SuperMenuEntity extends BaseEntity {

    private String title;
    private String type;

    public SuperMenuEntity(String title, String type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}