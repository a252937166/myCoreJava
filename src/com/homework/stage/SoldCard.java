package com.homework.stage;

import java.util.Date;

/**
 * Created by OyDn on 2016/1/7/007.
 */
public class SoldCard {
    int price;
    Date soldDate;
    SoldCard(int price,Date soldDate) {
        this.price = price;
        this.soldDate = soldDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }
}
