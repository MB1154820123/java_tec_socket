package com.example.socket.service;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ClientThread extends Thread {
    private Socket socket;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Client_Socket:"+socket);
        while ( true ) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("["+sdf.format(new Date())+"]服务器说:"+reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 读消息
    public void read () throws IOException {

    }
    // 写消息
    public void write () throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        System.out.println("请输入您要发送给服务器的消息：");
        Scanner scanner = new Scanner(System.in);
        while ( scanner.hasNext() ) {
            if ( "Exit" != scanner.next() ) {
                writer.write(scanner.next() + "\n");
                writer.flush();
            }
        }
    }

}
