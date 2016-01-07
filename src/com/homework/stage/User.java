package com.homework.stage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by OyDn on 2016/1/6/006.
 */
public class User {
    //用户id
    private long id;
    //用户密码
    private String password;
    //用户姓名
    private String name;
    //身份证
    private String idCard;
    //家庭地址
    private String address;
    //电话号码
    private String phone;
    //电子邮件
    private String email;
    private boolean hasCard = false;//用户充值卡绑定状态
    private String currentCardType;

    public String getCurrentCardType() {
        return currentCardType;
    }

    public void setCurrentCardType(String currentCardType) {
        this.currentCardType = currentCardType;
    }

    public boolean isHasCard() {
        return hasCard;
    }

    public void setHasCard(boolean hasCard) {
        this.hasCard = hasCard;
    }

    //充值卡集合
    private List<Card> cards = new ArrayList();
    private Date deadlineDate = new Date();

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }


    //点播日志(消费日志)
    private List<Item> demandLoggers = new ArrayList();
    private Item currentItem;//当前用户点播的内容对象
    private boolean root = true;

    public List<Item> getDemandLoggers() {
        return demandLoggers;
    }

    public void setDemandLoggers(List<Item> demandLoggers) {
        this.demandLoggers = demandLoggers;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    /**
     * 当前用户使用的充值卡
     */
    private UserCard currentCard = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Card> getAccounts() {
        return cards;
    }

    /**
     * 添加充值卡,这里单独提供一个add方法，主要是便于使用。对于方法的调用者来说，
     * 它只关心将充值卡添加到User对象即可，而无需关心是怎么加以及用什么加。
     * @param card
     */
    public void addUserCard(Card card) {
        cards.add(card);
    }
    @Override
    public int hashCode() {
        return (int)this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User)obj;
        return this.id == user.getId() ? true : false;
    }
}
