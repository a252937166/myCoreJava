package com.javastudy.sc;

import com.sun.glass.ui.SystemClipboard;
import com.sun.javaws.exceptions.ExitException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by OyDn on 2016/1/26/026.
 * 客户端
 */
public class Client {
    String s = "";//验证码初始化为0
    Socket socket = null;//客户端socket初始化为空
    InputStream in = null;//字节输入流初始化为空
    BufferedReader reader = null;//字符输入流初始化为空
    BufferedWriter writer = null;//字符输出流初始化为空
    Utils utils = new Utils();//工具类，用于判断是否是合格账号等
    Scanner scanner = new Scanner(System.in);//键盘获取信息类

    /**
     * 主函数
     * @param args 在命令行运行时候输入的参数
     */
    public static void main(String[] args) {
        new Client().start();
    }

    /**
     * 客户端开始运行
     */
    public void start() {
        try {
            socket = new Socket("127.0.0.1",10001);//连接本机10001的端口
        } catch (IOException e) {
            e.printStackTrace();
        }
        choice();//选择功能
    }

    private void choice() {
        System.out.println("请选择：1.登陆 2.注册 3.退出");//提示可以选择的功能
        String choiceNum = scanner.next();//从键盘接收选择
        //如果选择错误，则重新选择
        if (!(choiceNum.equals("1") || choiceNum.equals("2")|| choiceNum.equals("3"))) {
            System.out.println("输入错误，请重新输入 ：1.登陆 2.注册 3.退出");//提示用户选择
            choiceNum = scanner.next();//从键盘接收选择
        }
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));//把字节输出流转换为字符输出流
            writer.write(choiceNum + "\r\n");//把选择写入缓冲区
            writer.flush();//把缓冲区内容发给服务器端
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (choiceNum) {
            case "1":
                login();//如果选择1，则执行登陆功能
                break;
            case "2":
                register();//如果选择2.则执行注册功能
                break;
            case "3":
                System.out.println("再见！");//如果选择3，则打印再见
                break;
        }
    }

    /**
     * 登陆
     */
    public void login() {
        System.out.print("用户名：");//提示输入用户名
        String account = scanner.next();//从键盘接收用户名
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));//把字节输出流转换为字符输出流
            writer.write(account+"\r\n");//把账号写入缓冲区
            writer.flush();//把缓冲区内容发给服务器端
            System.out.print("密码：");//提示输入密码
            String password = scanner.next();//从键盘接收密码
            writer.write(password + "\r\n");//把密码写入缓冲区
            writer.flush();//把缓冲区内容发给服务器端
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));//把字节输入流转换为字符输入流
            String msg = reader.readLine();//读取服务器端的信息
            System.out.println(msg);//打印服务器端的信息
            System.out.println("请选择：1.重新登陆 2.退出");//提示选择内容
            String choiceAgain = scanner.next();//从键盘接收选择内容
            //如果选择错误，则重新选择
            if (!(choiceAgain.equals("1") || choiceAgain.equals("2"))) {
                System.out.println("输入错误，请重新输入 ：1.重新登陆 2.退出");
                choiceAgain = scanner.next();
            }
            writer.write(choiceAgain + "\r\n");//把选择的功能写入缓冲区
            writer.flush();//把缓冲区内容发给服务器端
            switch (choiceAgain) {
                case "1" : login();break;///如果选择1，则执行登陆方法
                case "2" :
                    System.out.println("再见！");//如果选择2，则打印再见
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册
     */
    public void register() {
        System.out.print("用户名(不能含有特殊字符)：");//提示输入用户名
        String account = scanner.next();//键盘获取用户名
        //如果用户名合法
        if (utils.isAccount(account)) {
            try {
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));//把socket的字节输出流转换为字符输出流
                System.out.print("请输入密码：");//提示输入密码
                String password = scanner.next();//从键盘获取密码
                String s1 = "";//初始化验证码
                //循环4次，随机打印4个字母
                for (int i = 0; i < 4; i++) {
                    int random = (int)(Math.random()*2);//随机产生0或者1
                    switch (random) {
                        case 0:
                            s1 += (char) ('a' + (int) (Math.random() * ('z' - 'a' + 1)));//如果是0，则随机产生一个小写字母，并加到验证码中
                            break;
                        case 1:
                            s1 += (char) ('A' + (int) (Math.random() * ('Z' - 'A' + 1)));//如果是1，则随机产生一个大写字母，并加到验证码中
                            break;
                    }
                    s = s1;//把这里产生的验证码赋值给s
                }
                System.out.println("验证码:" + s);//打印随机产生的验证码
                System.out.print("输入验证码:");//提示用户输入验证码
                //创建一个t2线程
                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.currentThread().sleep(60000);//睡60秒
                            System.out.println("输入超时！");//60秒之后提示超时
                            writer.write("输入超时！\r\n");//把超时信息写入缓冲区
                            writer.flush();//把缓冲区内容发给服务器端
                            System.exit(0);//超时之后，客户端自动运行结束
                        } catch (Exception e) {
                        }
                    }
                });
                t2.start();//t2线程开始运行
                String input = scanner.next();//从键盘接收验证码信息
                t2.interrupt();//验证码输入完后，t2线程结束
                if (s.equals(input)) {
                    try {
                        writer.write("开始储存用户资料！\r\n");//如果验证码正确，则把“开始储存用户资料！”写入缓冲区
                        writer.flush();//把缓冲区内容发给服务器端
                        writer.write(account + "\r\n");//把账号写入缓冲区
                        writer.flush();//把缓冲区内容发给服务器端
                        writer.write(password + "\r\n");//把密码写入缓冲区
                        writer.flush();//把缓冲区内容发给服务器端
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("验证码错误！");//如果验证码不正确，则提示不正确
                    try {
                        writer.write("注册失败！\r\n");//把注册失败写入缓冲区
                        writer.flush();//把缓冲区内容发给服务器端
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));//把socket的字节输入流转换为字符输入流
                String msg = reader.readLine();//读取服务器端发来的信息
                System.out.println(msg);//打印服务器端发来的信息
                System.out.println("请选择：1.登陆 2.注册 3.退出");//提示注册完之后的选择
                String choice = scanner.next();//从键盘获取选择内容
                //如果选择错误，则重新选择
                if (!(choice.equals("1") || choice.equals("2") || choice.equals("3"))) {
                    System.out.println("输入错误，请重新输入 ：1.登陆 2.注册 3.退出");
                    choice = scanner.next();
                }
                writer.write(choice + "\r\n");//把选择写入缓冲区
                writer.flush();//把缓冲区内容发给服务器端
                switch (choice) {
                    case "1":
                        login();//如果选择1，则执行登陆功能
                        break;
                    case "2":
                        register();//如果选择2，则执行注册功能
                        break;
                    case "3":
                        System.out.println("再见");//如果选择3，则打印再见
                        System.exit(0);//并结束客户端
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("用户名不合法!");//如果用户名不合法则提示
            register();//重新返回注册功能
        }
    }
}
