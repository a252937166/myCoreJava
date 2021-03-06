package com.homework.stage.cards;
import com.homework.stage.Card;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 季卡
 * Created by OyDn on 2016/1/6/006.
 */
public class QuarterCard extends Card{
    public QuarterCard() {
        super();
        this.setCardType("QuarterCard");
    }
    @Override
    /**
     * 获得充值卡到期时间
     */
    public Date getDeadlineDate() {
        Calendar gregorianCalendar = new GregorianCalendar();//新建一个GregorianCalendar日历类
        gregorianCalendar.setTime(this.getStartDate());//把日历时间设置为充值卡生效的时间
        gregorianCalendar.add(gregorianCalendar.DATE,30*4);//在此时间上增加30*4天
        Date deadlineDate = gregorianCalendar.getTime();//把30天后的日期赋值给deadlineDate
        return deadlineDate;//把deadlineDate作为返回值
    }
}
