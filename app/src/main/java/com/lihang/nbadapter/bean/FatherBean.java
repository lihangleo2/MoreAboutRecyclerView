package com.lihang.nbadapter.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class FatherBean implements Serializable {
    private String title;//条目
    private boolean isSelect;//是否被选中(选中则展示子集)
    private ArrayList<String> sons;//子集分类

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public ArrayList<String> getSons() {
        return sons;
    }

    public void setSons(ArrayList<String> sons) {
        this.sons = sons;
    }
}
