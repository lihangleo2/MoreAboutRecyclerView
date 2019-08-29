package com.lihang.nbadapter.bean;

import java.io.Serializable;

/**
 * Created by leo
 * on 2019/8/26.
 */
public class Person implements Serializable {
    private String id;
    private String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
