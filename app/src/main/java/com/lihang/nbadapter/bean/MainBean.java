package com.lihang.nbadapter.bean;

import java.io.Serializable;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class MainBean implements Serializable {
    private String title;
    private String des;


    public MainBean(String title, String des) {
        this.title = title;
        this.des = des;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
