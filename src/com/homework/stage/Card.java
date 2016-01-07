package com.homework.stage;
import java.util.Date;

/**
 * Created by OyDn on 2016/1/6/006.
 */
public abstract class Card {
    private String cardType;//卡的类型

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    private long id;//卡号
    private int price;//价格
    private Date startDate;//生效时间
    private Date deadlineDate;//到期时间
    protected Card(){
        startDate = new Date();//默认生效时间是现在
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public abstract Date getDeadlineDate();

    @Override
    public int hashCode() {
        return (int)this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }
        Card card = (Card)obj;
        return this.id == card.getId() ? true : false;
    }
}
