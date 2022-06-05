package com.example.socket.service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server{
    private static Socket socket;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("*************服务端启动成功，等待客户端连接**********");
            /**
             * 说明：阻塞循环，监听accept()，以获取每一个连接上来的socket
             */
            Set<Socket> set = new HashSet<>();
            while ( true ) {
                socket = serverSocket.accept();
                set.add(socket);
                System.out.println("SET:"+set.toString());
                ServerThread serverThread = new ServerThread(set);
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
