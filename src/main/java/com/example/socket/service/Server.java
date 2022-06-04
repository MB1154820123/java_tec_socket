package com.example.socket.service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private static Socket socket;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("*************服务端启动成功，等待客户端连接**********");
            // 阻塞监听连接的建立
            while ( true ) {
                socket = serverSocket.accept();
                String account = String.valueOf( socket.getPort() );
                ServerThread serverThread = new ServerThread( account,socket );
                serverThread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
