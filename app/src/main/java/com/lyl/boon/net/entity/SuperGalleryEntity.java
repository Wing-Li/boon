package com.lyl.boon.net.entity;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class SuperGalleryEntity extends BaseEntity {

    private boolean end;
    private int count;
    private int lastid;
    private List<ListBean> list;

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLastid() {
        return lastid;
    }

    public void setLastid(int lastid) {
        this.lastid = lastid;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean extends BaseEntity {
        private String id;
        private String imageid;
        private String group_title;
        private String tag;
        private String label;
        private int grpseq;
        private String cover_imgurl;
        private int cover_height;
        private int cover_width;
        private int total_count;
        private int index;
        private String qhimg_url;
        private String qhimg_thumb_url;
        private int qhimg_width;
        private int qhimg_height;
        private String dsptime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageid() {
            return imageid;
        }

        public void setImageid(String imageid) {
            this.imageid = imageid;
        }

        public String getGroup_title() {
            return group_title;
        }

        public void setGroup_title(String group_title) {
            this.group_title = group_title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getGrpseq() {
            return grpseq;
        }

        public void setGrpseq(int grpseq) {
            this.grpseq = grpseq;
        }

        public String getCover_imgurl() {
            return cover_imgurl;
        }

        public void setCover_imgurl(String cover_imgurl) {
            this.cover_imgurl = cover_imgurl;
        }

        public int getCover_height() {
            return cover_height;
        }

        public void setCover_height(int cover_height) {
            this.cover_height = cover_height;
        }

        public int getCover_width() {
            return cover_width;
        }

        public void setCover_width(int cover_width) {
            this.cover_width = cover_width;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getQhimg_url() {
            return qhimg_url;
        }

        public void setQhimg_url(String qhimg_url) {
            this.qhimg_url = qhimg_url;
        }

        public String getQhimg_thumb_url() {
            return qhimg_thumb_url;
        }

        public void setQhimg_thumb_url(String qhimg_thumb_url) {
            this.qhimg_thumb_url = qhimg_thumb_url;
        }

        public int getQhimg_width() {
            return qhimg_width;
        }

        public void setQhimg_width(int qhimg_width) {
            this.qhimg_width = qhimg_width;
        }

        public int getQhimg_height() {
            return qhimg_height;
        }

        public void setQhimg_height(int qhimg_height) {
            this.qhimg_height = qhimg_height;
        }

        public String getDsptime() {
            return dsptime;
        }

        public void setDsptime(String dsptime) {
            this.dsptime = dsptime;
        }
    }
}
