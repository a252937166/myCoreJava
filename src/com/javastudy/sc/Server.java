package com.javastudy.sc;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by OyDn on 2016/1/26/026.
 * 服务器端
 */
public class Server {
    Map userMap = new HashMap<>();

    /**
     * 启动服务器
     */
    public void start() {
        userMap.put("a","1");//假设已经有一个a用户，用于测试
        int count = 1;//客户端代号，初始值为1
        ServerSocket serverSocket;//服务器Socket
        Socket socket = null;//客户端Socket
        BufferedWriter writer = null;//写入流，初始值为空
        BufferedReader reader = null;//输出流，初始值为空
        try {
            System.out.println("在端口10001启动服务器.......\n");//提示服务器开启
            //开启端口的监听
            serverSocket = new ServerSocket(10001);//开启端口为10001
            //每接收到一个客户端的连接，就开启一个对应的服务器线程
            while (true) {
                socket = serverSocket.accept();//创建Socket
                System.out.println("接收到客户端" + count + "的连接.......\n");//提示客户端已连接
                BizThread biz = new BizThread(socket,count,(HashMap) userMap);//创服务器线程
                count++;//对应的服务器线程创建好后，客户端代号就加一
                biz.start();//启动服务器线程
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                   writer.close();//关闭写入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 主函数
     * @param args
     */
    public static void main(String[] args) {
        new Server().start();//启动服务器
    }
}