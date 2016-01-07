package com.homework.stage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by OyDn on 2016/1/6/006.
 */
public class Admin {
    String account;
    String password;
    Set<Card> cards = new HashSet();
    Set<Card> saleCards = new HashSet();

    public Set<Card> getSaleCards() {
        return saleCards;
    }

    public void setSaleCards(Set<Card> saleCards) {
        this.saleCards = saleCards;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public Admin() {
        this.account = "admin";
        this.password = "admin";
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
