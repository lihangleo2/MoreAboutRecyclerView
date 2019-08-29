package com.lihang.nbadapter.bean;
import java.io.Serializable;

/**
 * Created by leo
 * on 2019/8/28.
 */
public class StickyBean implements Serializable {
    private int id;
    private String headTitle;
    private String sonTitle;

    public StickyBean(int id, String headTitle, String sonTitle) {
        this.id = id;
        this.headTitle = headTitle;
        this.sonTitle = sonTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public String getSonTitle() {
        return sonTitle;
    }

    public void setSonTitle(String sonTitle) {
        this.sonTitle = sonTitle;
    }
}
