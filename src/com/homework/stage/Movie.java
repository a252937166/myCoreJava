package com.homework.stage;

import java.util.Date;

/**
 * Created by OyDn on 2016/1/7/007.
 */
public class Movie extends Item{
    private String name;//项目名
    private int price;//价格
    private String date;//日期
    private String director;//导演
    private String introduction;//内容简介
    private String note;//版权说明

    /**
     * 得到项目名
     * @return 返回项目名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置项目名
     * @param name 项目名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 得到价格
     * @return 返回价格
     */
    public int getPrice() {
        return price;
    }

    /**
     * 设置价格
     * @param price 价格
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * 得到出厂日期
     * @return 返回出厂日期
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置出厂日期
     * @param date 返回出厂日期
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 得到导演名字
     * @return 返回导演名字
     */
    public String getDirector() {
        return director;
    }

    /**
     * 设置导演名字
     * @param director 导演名字
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * 得到简介
     * @return 返回简介
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置简介
     * @param introduction 简介
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 得到版权说明
     * @return 返回版权说明
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置版权说明
     * @param note 版权说明
     */
    public void setNote(String note) {
        this.note = note;
    }
}

