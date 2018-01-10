package com.lyl.boon.net.entity;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class SuperImageEntity extends BaseEntity {

    private int dspcnt;
    private String group_title;
    private String website_dspname;
    private String group_pageurl;
    private boolean living;
    private boolean summary;
    private boolean end;
    private int lastid;
    private int count;
    private String commercial_ads;
    private List<ListBean> list;

    public int getDspcnt() {
        return dspcnt;
    }

    public void setDspcnt(int dspcnt) {
        this.dspcnt = dspcnt;
    }

    public String getGroup_title() {
        return group_title;
    }

    public void setGroup_title(String group_title) {
        this.group_title = group_title;
    }

    public String getWebsite_dspname() {
        return website_dspname;
    }

    public void setWebsite_dspname(String website_dspname) {
        this.website_dspname = website_dspname;
    }

    public String getGroup_pageurl() {
        return group_pageurl;
    }

    public void setGroup_pageurl(String group_pageurl) {
        this.group_pageurl = group_pageurl;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public boolean isSummary() {
        return summary;
    }

    public void setSummary(boolean summary) {
        this.summary = summary;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getLastid() {
        return lastid;
    }

    public void setLastid(int lastid) {
        this.lastid = lastid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCommercial_ads() {
        return commercial_ads;
    }

    public void setCommercial_ads(String commercial_ads) {
        this.commercial_ads = commercial_ads;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String imageid;
        private String group_id;
        private String pic_url;
        private String pic_pageurl;
        private int pic_height;
        private int pic_width;
        private int imgfeature;
        private String else_query;
        private String pic_size;
        private String pic_title;
        private String pic_desc;
        private String ins_time;
        private String index;
        private String qhimg_url;
        private String qhimg_thumb_url;
        private int qhimg_thumb_width;
        private int qhimg_thumb_height;
        private String dsptime;
        private String downurl;
        private String imgurl_dkey;

        public String getImageid() {
            return imageid;
        }

        public void setImageid(String imageid) {
            this.imageid = imageid;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getPic_pageurl() {
            return pic_pageurl;
        }

        public void setPic_pageurl(String pic_pageurl) {
            this.pic_pageurl = pic_pageurl;
        }

        public int getPic_height() {
            return pic_height;
        }

        public void setPic_height(int pic_height) {
            this.pic_height = pic_height;
        }

        public int getPic_width() {
            return pic_width;
        }

        public void setPic_width(int pic_width) {
            this.pic_width = pic_width;
        }

        public int getImgfeature() {
            return imgfeature;
        }

        public void setImgfeature(int imgfeature) {
            this.imgfeature = imgfeature;
        }

        public String getElse_query() {
            return else_query;
        }

        public void setElse_query(String else_query) {
            this.else_query = else_query;
        }

        public String getPic_size() {
            return pic_size;
        }

        public void setPic_size(String pic_size) {
            this.pic_size = pic_size;
        }

        public String getPic_title() {
            return pic_title;
        }

        public void setPic_title(String pic_title) {
            this.pic_title = pic_title;
        }

        public String getPic_desc() {
            return pic_desc;
        }

        public void setPic_desc(String pic_desc) {
            this.pic_desc = pic_desc;
        }

        public String getIns_time() {
            return ins_time;
        }

        public void setIns_time(String ins_time) {
            this.ins_time = ins_time;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
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

        public int getQhimg_thumb_width() {
            return qhimg_thumb_width;
        }

        public void setQhimg_thumb_width(int qhimg_thumb_width) {
            this.qhimg_thumb_width = qhimg_thumb_width;
        }

        public int getQhimg_thumb_height() {
            return qhimg_thumb_height;
        }

        public void setQhimg_thumb_height(int qhimg_thumb_height) {
            this.qhimg_thumb_height = qhimg_thumb_height;
        }

        public String getDsptime() {
            return dsptime;
        }

        public void setDsptime(String dsptime) {
            this.dsptime = dsptime;
        }

        public String getDownurl() {
            return downurl;
        }

        public void setDownurl(String downurl) {
            this.downurl = downurl;
        }

        public String getImgurl_dkey() {
            return imgurl_dkey;
        }

        public void setImgurl_dkey(String imgurl_dkey) {
            this.imgurl_dkey = imgurl_dkey;
        }
    }
}
