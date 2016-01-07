package com.homework.stage;

import com.homework.stage.cards.HalfYearCard;
import com.homework.stage.cards.MonthCard;
import com.homework.stage.cards.QuarterCard;
import com.homework.stage.cards.YearCard;
import com.homework.stage.comics.DragonBall;
import com.homework.stage.comics.Naruto;
import com.homework.stage.comics.OnePeace;
import com.homework.stage.movies.*;
import com.homework.stage.musics.AllRise;
import com.homework.stage.musics.EveryBody;
import com.homework.stage.musics.OneLove;
import com.homework.stage.musics.UMakeMeWanna;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by OyDn on 2016/1/6/006.
 */
public class ApplicationMain {
    Set<User> users = new HashSet();
    Set<SoldCard> soldCards = new HashSet<>();
    Scanner scanner = new Scanner(System.in);
    Utils utils = new Utils();//工具类
    boolean isUserLogin = false;//用户登陆状态
    boolean isAdminLogin = false;//管理员登录状态
    Admin admin = new Admin();//管理员
    static User CURRENT_USER;//当前登陆用户
    //设定各类卡的初始价格
    static int MONTH_CARD_PRICE = 18;
    static int QUARTER_CARD_PRICE = 50;
    static int HALF_YEAR_CARD_PRICE = 95;
    static int YEAR_CARD_PRICE = 175;
    /**
     * 注册
     */
    public void register(){
        User user = new User();//新建一个
        System.out.print("请输入昵称：");
        user.setName(scanner.next());//把输入的内容设置为user的昵称
        setUsersPassWord(user);//设置用户密码
        System.out.println("请完善以下基本信息：\n");
        System.out.print("请输入身份证：");
        String input = scanner.next();//从键盘接收输入的身份证信息
        //如果输入的身份证格式不正确，则提示，并重新输入
        while (!utils.isIdCard(input)) {
            System.out.println("您输入的身份证格式不正确，请重新输入：");
            input = scanner.next();
        }
        user.setIdCard(input);//如果正确，则把它设置为用户的身份证号码
        System.out.print("请输入家庭地址：");
        user.setAddress(scanner.next());
        System.out.print("请输入手机号码：");
        input = scanner.next();//从键盘接收输入的手机号码信息
        //如果输入的身份证格式不正确，则提示，并重新输入
        while (!utils.isPhoneNum(input)) {
            System.out.println("您输入的手机号码格式不正确，请重新输入：");
            input = scanner.next();
        }
        user.setPhone(input);//如果正确，则把它设置为用户的手机号码
        System.out.print("请输入电子邮件：");
        input = scanner.next();//把用户输入的电子邮件用input接收
        //如果输入的电子邮件不正确，则提示，并重新输入
        while (!utils.isEmail(input)) {
            System.out.println("您输入的电子邮件格式不正确，请重新输入：");
            input = scanner.next();
        }
        user.setEmail(input);//如果正确，则把它设置为用户的电子邮件
        //随机产生一个9位数，并把它作为新用户的ID，如果用户中已经有了此ID，则重新随机产生一个
         do{
            long id = (long) (Math.random()*1000000000);
            user.setId(id);
        }while (users.contains(user));
        System.out.println("恭喜您，注册成功！");
        //打印注册的基本信息
        System.out.println("您的昵称为：" + user.getName());
        System.out.println("您的身份证为："+user.getIdCard());
        System.out.println("您的家庭地址为：" + user.getAddress());
        System.out.println("您的手机号为：" + user.getPhone());
        System.out.println("您的E-mail为：" + user.getEmail() + "\n");
        System.out.print("正为您生成专属ID");
        //模拟系统正在运作
        try {
            for (int j = 0; j < 10; j++) {
                Thread.sleep(300);//隔0.3秒打印一个*
                System.out.print(".");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\n");//提两行
        System.out.println("生成成功！");
        System.out.println("您获得的专属ID为：" + user.getId());//打印出用户的id
        System.out.println("请妥善保管您的ID，它将用于登陆点播系统！\n");
        users.add(user);
        printFirstMenu();
    }

    /**
     * 设置用户密码
     */
    private void setUsersPassWord(User user) {
        System.out.print("请输入密码（至少6位）：");
        String input = scanner.next();//接收用户输入的密码
        while (input.length() < 6) {
            System.out.println("密码长度不符合要求，请重新输入：");
            input = scanner.next();//接收用户输入的密码
        }
        System.out.print("请确认密码：");
        String reInput = scanner.next();//接收用户确认的密码
        if (!reInput.equals(input)) {
            System.out.println("两次密码不一致，请重新设置密码!");
            setUsersPassWord(user);
            return;
        } else {
            System.out.println("密码设置成功！");
            user.setPassword(input);
        }
    }

    /**
     * 登录
     */
    public void login() {
        System.out.println("请选择登陆方式：1.管理员登陆\t2.用户登录");
        int num = scanner.nextInt();//用num接收输入的内容
        while (num != 1 && num != 2) {
            System.out.println("您的选项不存在，请重新选择");
            num = scanner.nextInt();//用户选择菜单
        }
        switch (num) {
            case 1:
                System.out.print("请输入管理员账号：");
                String account = scanner.next();
                if (!account.equals(admin.getAccount())) {
                    System.out.println("账号不正确！");
                    printFirstMenu();
                    return;
                }
                System.out.print("请输入密码：");
                String password = scanner.next();
                int adminCount = 1;//管理员输入密码次数计数器
                while (!isAdminLogin) {
                    //如果账号密码都正确，则显示登陆成功，并且设置为管理员登陆状态
                    if (account.equals(admin.getAccount()) && password.equals(admin.getPassword())) {
                        System.out.println("登陆成功！");
                        isAdminLogin = true;//管理员登陆状态转变为真
                        isUserLogin = false;
                        adminMenu();
                        return;
                    } else {
                        //如果不正确则显示账号密码错误，并重新输入
                        System.out.print("密码错误，请重新输入：");
                        password = scanner.next();
                        adminCount ++;//输入错误一次加1
                    }
                    //如果连续输入错误超过三次，返回上级菜单
                    if (adminCount == 3) {
                        System.out.println("连续输错3次，请稍后重试！");
                        printFirstMenu();
                        return;
                    }
                }
                break;
            case 2:
                //如果users为空，则跳到注册界面
                if (null == users) {
                    System.out.println("当前没有任何注册用户，请先注册");
                    register();
                    return;
                } else {//如果不为空，则进行正常登陆
                    System.out.print("请输入用户ID：");
                    Long userId = scanner.nextLong();
                    Iterator<User> iterator = users.iterator();
                    //遍历每一个用户，判断他们的id存不存在
                    while (iterator.hasNext()) {
                        User currentUser = iterator.next();
                        if (userId == currentUser.getId()) {
                            //判断有没有被封号
                            if (currentUser.isRoot()) {
                                System.out.print("请输入密码：");
                                String userPassword = scanner.next();
                                //如果密码正确，则登陆成功，并直接跳出此方法！
                                int userCount = 1;//用户输入密码计数器
                                while (!isUserLogin) {
                                    //如果账号密码都正确，则显示登陆成功，并且设置为管理员登陆状态
                                    if (userPassword.equals(currentUser.getPassword())) {
                                        System.out.println("登陆成功！");
                                        CURRENT_USER = currentUser;
                                        isUserLogin = true;//用户登陆状态转变为真
                                        isAdminLogin = false;
                                        userMenu();//进入用户菜单
                                        return;
                                    } else {
                                        //如果不正确则显示账号密码错误，并重新输入
                                        System.out.print("密码错误，请重新输入：");
                                        userPassword = scanner.next();
                                        userCount++;//输入错误一次加1
                                    }
                                    //如果连续输入错误超过三次，返回上级菜单
                                    if (userCount == 3) {
                                        System.out.println("连续输错3次，请稍后重试！");
                                        printFirstMenu();
                                        return;
                                    }
                                }
                            }else {
                                //如果被封号了，进行提示操作
                                System.out.println("您由于违规操作，已经被封号，请联系管理员进行解封。\n");
                                printFirstMenu();
                                return;
                            }
                        }
                    }
                    //如果遍历完了也没有跳出此方法，说明没有此ID，打印友好提示，并返回上级菜单
                    System.out.println("该ID不存在！");
                    printFirstMenu();
                    return;
                }
        }

    }

    /**
     * 查看收益
     */
    private void findEarnings(){

    }

    /**
     * 用户购买充值卡
     */
    private  void buyUserCard() {

    }
    public void printFirstMenu() {
        System.out.println("***************************************************************************");
        System.out.println("请选择您的操作：1.注册\t2.登陆\t3.退出");
        System.out.println("***************************************************************************");
        int num = scanner.nextInt();
        while (num != 1 && num != 2 && num != 3) {
            System.out.println("您的输入有误，请重新输入：");
            num = scanner.nextInt();
        }
        switch (num) {
            case 1:register();return;
            case 2:login();return;
            case 3:
                System.out.println("谢谢使用，再见！");
                return;
        }
    }

    /**
     * 管理员菜单
     */
    public void adminMenu() {
        System.out.println("********************************************************************************************************");
        System.out.println("1.查看用户\t2.封号管理\t3.修改价格\t4.添加充值卡\t5.修改密码\t6.查看平台收益\t7.返回上级菜单");
        System.out.println("********************************************************************************************************");
        int num = scanner.nextInt();
        while (num > 7 || num < 1) {
            System.out.println("输入有误，请重新输入：");
            num = scanner.nextInt();
        }
        switch (num){
            case 1:checkUsers();
                break;
            case 2:banId();
                    break;
            case 3:changePrice();
                    break;
            case 4:addCard();
                break;
            case 5: changePassword();
                break;
            case 6: printEarnings();
                break;
            case 7:printFirstMenu();
                isAdminLogin = false;
                return;
        }
        adminMenu();
    }

    /**
     * 打印收益
     */
    private void printEarnings() {
        System.out.println("***************************************************************************");
        System.out.println("请选择您要看的收益类型：1.日收益 2.月收益 3.季度收益 4.半年收益 5.年收益");
        System.out.println("***************************************************************************");
        Date todayDate = new Date();
        int num = scanner.nextInt();
        while (num > 5 || num < 1) {
            System.out.println("您的输入有误，请重新输入：");
            num = scanner.nextInt();
        }
        Iterator<SoldCard> iterator = soldCards.iterator();
        int earnings = 0;
        while (iterator.hasNext()) {
            SoldCard saleCard = iterator.next();
            if (num == 1 && getDays(todayDate,saleCard.getSoldDate()) < 1) {
                earnings += saleCard.getPrice();
            }else if (num == 2 && getDays(todayDate,saleCard.getSoldDate()) < 30) {
                earnings += saleCard.getPrice();
            }else if (num == 3 && getDays(todayDate,saleCard.getSoldDate()) < 30*4) {
                earnings += saleCard.getPrice();
            }else if (num == 4 && getDays(todayDate,saleCard.getSoldDate()) < 30*6) {
                earnings += saleCard.getPrice();
            }else if (num == 5 && getDays(todayDate,saleCard.getSoldDate()) < 365) {
                earnings += saleCard.getPrice();
            }
        }
        switch (num) {
            case 1:
                System.out.println("日收益为：" + earnings);
                break;
            case 2:
                System.out.println("月收益为：" + earnings);
                break;
            case 3:
                System.out.println("季收益为：" + earnings);
                break;
            case 4:
                System.out.println("半年收益为：" + earnings);
                break;
            case 5:
                System.out.println("年收益为：" + earnings);
                break;
        }
    }

    /**
     * 查看用户
     */
    private void checkUsers() {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            System.out.println("用户ID ：" + user.getId() + "\t" + "用户昵称：" + user.getName());
        }
    }

    /**
     * 封号操作
     */
    private void banId() {
        System.out.print("请输入您要查封的ID：");
        long id = scanner.nextLong();
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (id == user.getId()) {
                user.setRoot(false);
                System.out.println("用户" + user.getId() + "已被封号\n");
                return;
            }
        }
        System.out.println("该用户不存在！\n");
    }

    /**
     * 修改价格
     */
    private void changePrice() {
        System.out.println("***************************************************************************");
        System.out.println("请选择要修改的充值卡种类：1.月卡\t2.季卡\t3.半年卡\t4.年卡");
        System.out.println("***************************************************************************");
        int num = scanner.nextInt();
        while (num != 1 && num != 2 && num != 3 && num != 4) {
            System.out.println("您的输入有误，请重新输入：");
            num = scanner.nextInt();
        }
        System.out.print("请输入新价格：");
        int newPrice = scanner.nextInt();
        switch (num) {
            case 1:
                MONTH_CARD_PRICE = newPrice;
                System.out.println("月卡价格修改成功！\t当前价格为：" + newPrice);
                break;
            case 2:
                QUARTER_CARD_PRICE = newPrice;
                System.out.println("季卡价格修改成功！\t当前价格为：" + newPrice);
                break;
            case 3:
                HALF_YEAR_CARD_PRICE = newPrice;
                System.out.println("半年卡价格修改成功！\t当前价格为：" + newPrice);
                break;
            case 4:
                YEAR_CARD_PRICE = newPrice;
                System.out.println("年卡价格修改成功！\t当前价格为：" + newPrice);
                break;
        }
    }

    /**
     * 增加充值卡
     */
    private void addCard() {
        System.out.println("***************************************************************************");
        System.out.println("请选择您要增加的充值卡种类：1.月卡\t2.季卡\t3.半年卡\t4.年卡");
        System.out.println("***************************************************************************");
        int num = scanner.nextInt();
        while (num != 1 && num != 2 && num != 3 && num != 4) {
            System.out.println("您的输入有误，请重新输入：");
            num = scanner.nextInt();
        }
        long id;
        switch (num) {
            case 1:
                Card monthCard = new MonthCard();
                id = (long)((Math.random())*1000000000000l);
                monthCard.setId(id);
                if (null == admin.getCards()) {
                    admin.getCards().add(monthCard);
                    admin.getSaleCards().add(monthCard);
                }
                while (admin.getCards().contains(monthCard)) {
                    id = (long)((Math.random())*1000000000000l);
                    monthCard.setId(id);
                }
                admin.getCards().add(monthCard);
                admin.getSaleCards().add(monthCard);
                System.out.println("月卡增加成功\t" + "卡号为：" + monthCard.getId());
                break;
            case 2:
                Card quarterCard = new QuarterCard();
                id = (long)((Math.random())*1000000000000l);
                quarterCard.setId(id);
                if (null == admin.getCards()) {
                    admin.getCards().add(quarterCard);
                    admin.getSaleCards().add(quarterCard);
                    return;
                }
                while (admin.getCards().contains(quarterCard)) {
                    id = (long)((Math.random())*1000000000000l);
                    quarterCard.setId(id);
                }
                admin.getCards().add(quarterCard);
                admin.getSaleCards().add(quarterCard);
                System.out.println("季卡增加成功\t" + "卡号为：" + quarterCard.getId());
                break;
            case 3:
                Card halfYearCard = new HalfYearCard();
                id = (long)((Math.random())*1000000000000l);
                halfYearCard.setId(id);
                if (null == admin.getCards()) {
                    admin.getCards().add(halfYearCard);
                    admin.getSaleCards().add(halfYearCard);
                    return;
                }
                while (admin.getCards().contains(halfYearCard)) {
                    id = (long)((Math.random())*1000000000000l);
                    halfYearCard.setId(id);
                }
                admin.getCards().add(halfYearCard);
                admin.getSaleCards().add(halfYearCard);
                System.out.println("半年卡增加成功\t" + "卡号为：" + halfYearCard.getId());
                break;
            case 4:
                Card yearCard = new YearCard();
                id = (long)((Math.random())*1000000000000l);
                yearCard.setId(id);
                if (null == admin.getCards()) {
                    admin.getCards().add(yearCard);
                    admin.getSaleCards().add(yearCard);
                }
                while (admin.getCards().contains(yearCard)) {
                    id = (long)((Math.random())*1000000000000l);
                    yearCard.setId(id);
                }
                admin.getCards().add(yearCard);
                admin.getSaleCards().add(yearCard);
                System.out.println("年卡增加成功\t" + "卡号为：" + yearCard.getId());
                break;
        }

    }

    /**
     * 修改密码
     */
    private void changePassword() {
        System.out.print("请输入旧密码：");
        String input = scanner.next();
        if (!input.equals(admin.getPassword())){
            System.out.println("密码不正确！");
            return;
        }
        while (input.equals(admin.getPassword())) {
            System.out.print("请输入新密码：");
            String newPassword = scanner.next();
            System.out.print("确认新密码：");
            String rePassword = scanner.next();
            if (newPassword.equals(rePassword)) {
                admin.setPassword(newPassword);
                System.out.println("密码修改成功！");
                break;
            } else {
                System.out.println("两次输入不一致，请重新输入！");
            }
        }
    }

    /**
     * 用户菜单
     */
    public void userMenu() {
        System.out.println("***************************************************************************");
        System.out.println("1.修改密码\t2.点播\t3.充值卡管理\t4.点播记录\t5.返回上级菜单");
        System.out.println("***************************************************************************");
        int num = scanner.nextInt();
        while (num > 5 || num < 1) {
            System.out.println("输入有误，请重新输入：");
            num = scanner.nextInt();
        }
        switch (num) {
            case 1:
                System.out.print("请输入原密码：");
                String input = scanner.next();
                if (!input.equals(CURRENT_USER.getPassword())) {
                    System.out.println("密码不正确！");
                    break;
                }
                while (input.equals(CURRENT_USER.getPassword())) {
                    System.out.print("请输入新密码（至少6位）：");
                    String newPassword = scanner.next();
                    while (newPassword.length() < 6) {
                        System.out.println("密码长度不符合要求，请重新输入：");
                        newPassword = scanner.next();//接收用户输入的密码
                    }
                    System.out.print("确认新密码：");
                    String rePassword = scanner.next();
                    if (newPassword.equals(rePassword)) {
                        CURRENT_USER.setPassword(newPassword);
                        System.out.println("密码修改成功！");
                        break;
                    } else {
                        System.out.println("两次输入不一致，请重新输入！");
                    }
                }
                break;
            case 2:
                order();
                break;
            case 3:
                cardManage();
                break;
            case 4:
                orderRecord();
                break;
            case 5:
                printFirstMenu();
                isAdminLogin = false;
                return;
        }
        userMenu();
    }

    /**
     * 点播记录
     */
    private void orderRecord() {
        Date currentDate = new Date();
        Iterator<Item> iterator = CURRENT_USER.getDemandLoggers().iterator();
        int number = 1;
        if (CURRENT_USER.getDemandLoggers().size() == 0) {
            System.out.println("您还未点播任何内容。");
            return;
        }
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (CURRENT_USER.getCurrentCardType().equals("MonthCard") && getDays(currentDate,item.getOrderTime()) <= 7) {
                System.out.println(number++ + "." + item.getName() + "\t点播时间：" +(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(item.getOrderTime()));
            } else if(CURRENT_USER.getCurrentCardType().equals("QuarterCard") && getDays(currentDate,item.getOrderTime()) <= 30) {
                System.out.println(number++ + "." + item.getName() + "\t点播时间：" +(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(item.getOrderTime()));
            } else if(CURRENT_USER.getCurrentCardType().equals("HalfYearCard") && getDays(currentDate,item.getOrderTime()) <= 30*3) {
                System.out.println(number++ + "." + item.getName() + "\t点播时间：" +(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(item.getOrderTime()));
            } else if(CURRENT_USER.getCurrentCardType().equals("HalfYearCard") && getDays(currentDate,item.getOrderTime()) <= 30*6) {
                System.out.println(number++ + "." + item.getName() + "\t点播时间：" +(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(item.getOrderTime()));
            }
        }
        if (number == 1) {
            System.out.println("您当前权限无法查看更久远的点播记录！");
        }
    }

    private void cardManage() {
        System.out.println("***************************************************************************");
        System.out.println("1.购买充值卡\t2.升级充值卡\t3.绑定默认充值卡");
        System.out.println("***************************************************************************");
        int num = scanner.nextInt();
        while (num > 3 || num < 1) {
            System.out.println("输入有误，请重新输入：");
            num = scanner.nextInt();
        }
        switch (num) {
            case 1: buyCard();break;
            case 2: upCard();break;
            case 3: bindingCard();break;
        }
    }

    /**
     * 点播
     */
    private void order() {
        //判断是否已经到期
        if (CURRENT_USER.getDeadlineDate().getTime() > (new Date()).getTime()) {
            System.out.println("请选择您要点播的类型：1.电影\t2.音乐\t3.动漫");
            int num = scanner.nextInt();
            while (num > 3 || num < 1) {
                System.out.println("输入有误，请重新输入：");
                num = scanner.nextInt();
            }
            int orderNum;
            switch (num) {
                case 1:
                    System.out.println("请选择您要点播的电影：");
                    System.out.println("1.侏罗纪公园");
                    System.out.println("2.金刚");
                    System.out.println("3.蜘蛛侠");
                    System.out.println("4.超人");
                    System.out.println("5.万万没想到");
                    orderNum = scanner.nextInt();
                    while (orderNum> 5 || orderNum < 1) {
                        System.out.println("输入有误，请重新输入：");
                        orderNum = scanner.nextInt();
                    }
                    switch (orderNum) {
                        case 1: choice(new JurassicPark());return;
                        case 2: choice(new KingKong());return;
                        case 3: choice(new SpiderMan());return;
                        case 4: choice(new Superman());return;
                        case 5: choice(new Surprise());return;
                    }
                    break;
                case 2:
                    System.out.println("请选择您要点播的音乐：");
                    System.out.println("1.allrise");
                    System.out.println("2.everybody");
                    System.out.println("3.onelove");
                    System.out.println("4.U make me wanna");
                    orderNum = scanner.nextInt();
                    while (orderNum> 4 || orderNum < 1) {
                        System.out.println("输入有误，请重新输入：");
                        orderNum = scanner.nextInt();
                    }
                    switch (orderNum) {
                        case 1:
                            CURRENT_USER.getDemandLoggers().add(new AllRise());
                            System.out.println("点播 allrise 成功！");
                            return;
                        case 2:
                            CURRENT_USER.getDemandLoggers().add(new EveryBody());
                            System.out.println("点播 everybody 成功！");
                            return;
                        case 3:
                            CURRENT_USER.getDemandLoggers().add(new OneLove());
                            System.out.println("点播 onelove 成功！");
                            return;
                        case 4:
                            CURRENT_USER.getDemandLoggers().add(new UMakeMeWanna());
                            System.out.println("点播 U make me wanna 成功！");
                            return;
                    }
                case 3:
                    System.out.println("请选择您要点播的动漫：");
                    System.out.println("1.七龙珠");
                    System.out.println("2.火影忍者");
                    System.out.println("3.海贼王");
                    orderNum = scanner.nextInt();
                    while (orderNum> 3 || orderNum < 1) {
                        System.out.println("输入有误，请重新输入：");
                        orderNum = scanner.nextInt();
                    }
                    switch (orderNum) {
                        case 1:
                            CURRENT_USER.getDemandLoggers().add(new DragonBall());
                            System.out.println("点播 七龙珠 成功！");
                            return;
                        case 2:
                            CURRENT_USER.getDemandLoggers().add(new Naruto());
                            System.out.println("点播 火影忍者 成功！");
                            return;
                        case 3:
                            CURRENT_USER.getDemandLoggers().add(new OnePeace());
                            System.out.println("点播 海贼王 成功！");
                            return;
                    }

            }
        } else {
            System.out.println("充值卡已经到期，请重新充值！");
        }
    }

    /**
     * 选择电影
     * @param item 电影
     */
    private void choice(Movie item) {
        printMessage(item);//打印电影详细信息
        System.out.println("***************************************************************************");
        System.out.println("请选择：1.点播\t2.返回上级菜单");
        System.out.println("***************************************************************************");
        int num = scanner.nextInt();//获得输入的数字
        while (num >2 || num < 1) {
            System.out.println("您的输入有误，请重新输入：");
            num = scanner.nextInt();
        }
        switch (num) {
            case 1:
                CURRENT_USER.getDemandLoggers().add(item);
                System.out.println("点播" + item.getName() + "成功！");
                break;
            case 2: order();break;
        }
    }
    /**
     * 打印电影信息
     * @param item 电影
     */
    private void printMessage(Movie item) {
        System.out.println("片名：" + item.getName() + "\n");
        System.out.println("出品时间：" + item.getDate() + "\n");
        System.out.println("导演：" + item.getDirector() + "\n");
        System.out.println("简介：" + item.getIntroduction() + "\n");
        System.out.println("出品公司：" + item.getNote() + "\n");
    }

    /**
     * 买卡
     */
    private void buyCard() {
        System.out.println("***************************************************************************");
        System.out.println("请选择您要购买的种类：1.月卡" + "(" + MONTH_CARD_PRICE + ")" + "\t" +"2.季卡" + "(" + QUARTER_CARD_PRICE+ ")" + "\t" + "3.半年卡" + "(" + HALF_YEAR_CARD_PRICE + ")"+ "\t"+ "4.年卡" + "(" + YEAR_CARD_PRICE + ")" );
        System.out.println("***************************************************************************");
        int input = scanner.nextInt();//获得输入的数字
        while (input > 4 || input < 1) {
            System.out.println("您的输入有误，请重新输入：");
            input = scanner.nextInt();
        }
        switch (input) {
            case 1:
                Iterator<Card> monthIterator = admin.getSaleCards().iterator();
                while (monthIterator.hasNext()) {
                    Card card = monthIterator.next();
                    if (card instanceof MonthCard) {
                        System.out.println("您买的月卡卡密为：" + card.getId());
                        soldCards.add(new SoldCard(MONTH_CARD_PRICE,new Date()));
                        admin.getSaleCards().remove(card);
                        return;
                    }
                }
                System.out.println("月卡卖光了！");
                break;
            case 2:
                Iterator<Card> quarterIterator = admin.getSaleCards().iterator();
                while (quarterIterator.hasNext()) {
                    Card card = quarterIterator.next();
                    if (card instanceof QuarterCard) {
                        System.out.println("您买的季卡卡密为：" + card.getId());
                        soldCards.add(new SoldCard(QUARTER_CARD_PRICE,new Date()));
                        admin.getSaleCards().remove(card);
                        return;
                    }
                }
                System.out.println("季卡卖光了！");
                break;
            case 3:
                Iterator<Card> halfYearIterator = admin.getSaleCards().iterator();
                while (halfYearIterator.hasNext()) {
                    Card card = halfYearIterator.next();
                    if (card instanceof HalfYearCard) {
                        System.out.println("您买的半年卡卡密为：" + card.getId());
                        soldCards.add(new SoldCard(HALF_YEAR_CARD_PRICE,new Date()));
                        admin.getSaleCards().remove(card);
                        return;
                    }
                }
                System.out.println("半年卡卖光了！");
                break;
            case 4:
                Iterator<Card> yearIterator = admin.getSaleCards().iterator();
                while (yearIterator.hasNext()) {
                    Card card = yearIterator.next();
                    if (card instanceof YearCard) {
                        System.out.println("您买的年卡卡密为：" + card.getId());
                        soldCards.add(new SoldCard(YEAR_CARD_PRICE,new Date()));
                        admin.getSaleCards().remove(card);
                        return;
                    }
                }
                System.out.println("年卡卖光了！");
                break;
        }
    }

    /**
     * 升级卡
     */
    public void upCard() {
        if (CURRENT_USER.isHasCard()) {
            System.out.print("请输入卡密：");
            long input = scanner.nextLong();
            Iterator<Card> iterator = admin.getCards().iterator();
            while (iterator.hasNext()) {
                Card card = iterator.next();
                if (card.getId() == input) {
                    card.setStartDate(CURRENT_USER.getDeadlineDate());//设置充值卡生效时间为当前用户到期时间
                    CURRENT_USER.setDeadlineDate(card.getDeadlineDate());//设置用户新的到期时间为，延长后的时间
                    CURRENT_USER.setCurrentCardType(card.getCardType());//把卡的类名赋值给用户当前卡的类名
                    System.out.println("会员有效期至：" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(CURRENT_USER.getDeadlineDate()));
                    admin.getCards().remove(card);//使用之后应该把该卡删除掉，以免重复使用
                   return;
                }
            }
            System.out.println("卡密不存在！");
        }else {
            System.out.println("您还没有绑定充值卡，请先绑定默认充值卡！");
        }
    }

    /**
     * 绑定默认卡
     */
    private void bindingCard() {
        System.out.print("请输入卡密：");
        long input = scanner.nextLong();
        Iterator<Card> iterator = admin.getCards().iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getId() == input) {
                card.setStartDate(CURRENT_USER.getDeadlineDate());//设置充值卡生效时间为当前用户到期时间
                CURRENT_USER.setDeadlineDate(card.getDeadlineDate());//设置用户新的到期时间为，延长后的时间
                CURRENT_USER.setCurrentCardType(card.getCardType());//把卡的类名赋值给用户当前卡的类名
                System.out.println("绑定成功！");
                System.out.println("会员有效期至：" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(CURRENT_USER.getDeadlineDate()));
                return;
            }
        }
        System.out.println("绑定失败，卡密不存在！");
    }

    /**
     * 获得两个时间之间差多少天
     * @param date1 后面的时间
     * @param date2 前面的时间
     * @return date2 距离 date1还有多少天
     */
    private int getDays(Date date1,Date date2) {
        return (int)((date1.getTime() - date2.getTime())/(1000*60*60*24));
    }
}
