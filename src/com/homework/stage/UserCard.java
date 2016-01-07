package com.homework.stage;

import java.util.Date;

/**
 * Created by OyDn on 2016/1/6/006.
 */
public class UserCard{
    //卡的生效时间
    private Date createDate;

    //卡的购买时间
    private Date buyDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
}