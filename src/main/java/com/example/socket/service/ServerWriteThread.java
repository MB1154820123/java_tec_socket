package com.example.socket.service;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;

public class ServerWriteThread extends  Thread {
private Set<Socket> socketSet;
private Scanner sc = new Scanner(System.in);
    public ServerWriteThread(Set<Socket> set) {
        this.socketSet = set;
    }
    @SneakyThrows
    @Override
    public void run(){
        System.out.println("当前连线人数有"+socketSet.size()+"人...");
        if ( 1 == socketSet.size() ) {
            Socket socket = socketSet.iterator().next();
            System.out.println("唯一的Client_Socket:"+socket);
            System.out.println("是否与"+ socket.getPort()+"通讯（Y/N）:");

            if (sc.hasNext() && sc.next().equals("Y")) {
                System.out.println("与" + socket.getPort() + "建立了通信通道，请输入您要发送的消息，并按Enter键发送...");
                while ( true ) {
                    // 写
                    String temp = sc.next();
                    if ( null != temp && !temp.contains("Exit")) {
                        try {
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            writer.write(temp+"\n");
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if ( temp.contains("Exit") )  {
                        System.out.println("已退出与"+socket.getPort()+"的会话，当前连线人有("+socketSet+")，请选择沟通对象：");
                        for (Socket s : socketSet) {
                            if ( sc.next() .contains( String.valueOf(s.getPort()) ) ) {
                                System.out.println("您选择的沟通对象为："+s.getPort()+"请输入消息并按Enter键发送：");
                                if ( sc.hasNext() ) {
                                    String temp_ = sc.next();
                                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                                    writer.write(temp_+"\n");
                                    writer.flush();
                                    }
                                }
                            }
                        }
                    }
                }
            }
       else {
            System.out.println("请选择发消息模式：\n 1.群发 \n 2.私发");
            while ( true ) {
                if ( sc.hasNext() && sc.next().equals("1")) {
                    System.out.println("请输入您要发送的消息：");
                    if ( sc.hasNext() ) {
                        for (Socket s : socketSet) {
                            BufferedWriter writer = null;
                            try {
                                writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                                writer.write(sc.next());
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else if ( sc.hasNext() && sc.next().equals("2")) {
                    System.out.println("当前上线的人有"+socketSet+",请选择一人:");
                    for (Socket s : socketSet) {
                        if ( sc.hasNext() && socketSet.toString().contains(sc.next()) ) {
                            System.out.println("你选择的沟通对象为："+s.getPort()+"，现在，您可以与之通信，请输入您要发送给对方的消息，并按Enter键发送：");
                            while ( sc.hasNextLine() ) {
                                BufferedWriter writer = null;
                                try {
                                    writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                                    writer.write(sc.next());
                                    writer.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            System.out.println("你选择的沟通对象"+sc.next()+"不存在...");
                        }
                    }
                } else {
                    System.out.println("你选择的模式不存在，请重新选择");
                    continue;
                }
            }
        }
    }
}
