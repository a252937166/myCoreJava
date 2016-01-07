package com.homework.stage;

import java.util.Date;

/**
 * Created by OyDn on 2016/1/6/006.
 */
public abstract class Item {
    private String name;
    private Date orderTime = new Date();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOrderTime() {
        return orderTime;
    }
}
