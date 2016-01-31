package com.javastudy.sc;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/1/30.
 * 服务器线程
 */
public class BizThread extends Thread {
    private Socket socket;
    int count;
    HashMap userMap;

    /**
     * 构造函数
     * @param socket 对应的客户端socket
     * @param count 客户端代号
     * @param userMap 服务器中的用户账号集合
     */
    public BizThread(Socket socket,int count,HashMap userMap) {
        //对应数据初始化
        this.socket = socket;
        this.count = count;
        this.userMap = userMap;
    }

    @Override
    public void run() {
        InputStream in = null; //字节输入流
        BufferedReader reader = null; //字符输入流
        BufferedWriter writer = null;//字符输出流
        OutputStream out = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));//把socket的字节输入流转换为字符输入流
            String choice = reader.readLine();//读取客户端的选择
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));//把socket的字节输出流转换为字符输出流
            switch (choice) {
                case "1":
                    login(writer, reader);//如果选择1，则执行登陆方法
                    break;
                case "2":
                    register(reader, writer);//如果选择2，则执行注册方法
                    break;
                case "3":
                    System.out.println("客户端" + count + "已退出！\n");//如果选择3，则提示客户端已退出
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册
     * @param reader 写入流
     * @param writer 输出流
     * @throws IOException 抛出异常
     */
    private void register(BufferedReader reader, BufferedWriter writer) throws IOException {
        String msg = reader.readLine(); //获得客户端输出的信息
        if (msg.equals("输入超时！")) {
            System.out.println("客户端由于超时" + count + "已退出！\n");//如果信息提示超时，则打印超时，并提示客户端已退出
        }else {
            //如果没有超时，则打印对应信息
            System.out.println("客户端" + count + msg +"\n");
            if (msg.equals("开始储存用户资料！")) {
                //如果客户端输出的信息是储存资料
                String account = reader.readLine();//读取客户端输出的账号
                String password = reader.readLine();//读取客户端输出的密码
                if (userMap.containsKey(account)) {
                    writer.write("该用户已存在！注册失败！\r\n");//如果账号已存在，则在缓冲区写入账号已存在，注册失败的信息
                    System.out.println(account + "用户名已存在，" + "客户端" + count + "注册失败！\n");//打印注册失败信息
                    writer.flush();//把缓冲区写入的内容发给客户端
                } else {
                    userMap.put(account, password);//如果账号不存在，则把账号内容写入userMap
                    System.out.println("客户端" + count + "用户" + account + "注册成功！\n");//打印注册成功
                    writer.write("注册成功！\r\n");//把注册成功写入缓冲区
                    writer.flush();//把缓冲区写入的内容发给客户端
                }
            } else {
                writer.write("注册失败！\r\n");//如果不是"客户端传来的信息不是"开始储存用户资料！",则把"注册失败"写入缓冲区
                writer.flush();//把缓冲区写入的内容发给客户端
            }
            String choice = reader.readLine();//读取客户端注册完成后的选择
            switch (choice) {
                case "1":
                    login(writer,reader);//如果是1，则执行登陆方法
                    break;
                case "2":
                    register(reader,writer);//如果是2，则执行注册方法
                    break;
                case "3":
                    System.out.println("客户端" + count + "已退出！\n");//如果是三，打印提示客户端已退出
            }
        }
        return;
    }

    /**
     * 登陆
     * @param writer 输出流
     * @param reader 写入流
     * @throws IOException 抛出异常
     */
    private void login(BufferedWriter writer, BufferedReader reader) throws IOException {
        String msg = "";//初始化信息为空
        String account  = reader.readLine();//读取客户端输出的账户
        String password = reader.readLine();//读取客户端输出的密码
        if (userMap.containsKey(account)) {//判断账号是否存在
            if (userMap.get(account).equals(password)) {
                msg = "登陆成功！";//如果账号存在，并且对应密码与客户端输出的密码相同，则信息为登陆成功
            } else {
                msg = "登陆失败！";//如果密码不匹配，则信息为登陆失败
            }
        } else {
            msg = "账号不存在！";
        }
        System.out.println("客户端" + count + msg + "\n");//打印对应的 的信息
        writer.write(msg + "\r\n");//把信息写入缓冲区
        writer.flush();//把缓冲区写入的内容发给客户端
        String choiceAgain = reader.readLine();//读取客户端登陆之后的选择
        switch (choiceAgain) {
            case "1" : login(writer,reader);break;//如果选择1，则执行登陆功能
            case "2" :
                System.out.println("客户端" + count + "已退出！\n");//如果选择2，则打印客户端已退出
                break;
        }
        return;
    }
}
