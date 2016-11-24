package com.lyl.boon.entity;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class SuperMenuEntiry extends BaseEntiry{

    //    id	int	分类id，需要查询该类下的列表就需要传入才参数
//    name	string	分类名称
//    title	string	分类的标题（网页显示的标题）
//    keywords	string	分类的关键词（网页显示的标题）
//    description	string	分类的描述（网页显示的标题）
//    seq	string	分类的排序，从小到大的递增排序

    private int id;
    private String name;
    private String title;
    private String keywords;
    private String description;
    private int seq;//排序 从0。。。。10开始

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

}
