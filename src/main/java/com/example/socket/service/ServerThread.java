package com.example.socket.service;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServerThread extends  Thread {
private Set<Socket> socketSet;
private Scanner sc = new Scanner(System.in);
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public ServerThread(Set<Socket> set) {
        this.socketSet = set;
    }

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
                    Scanner scWrite = new Scanner(System.in);
                    // 写
                    if (scWrite.hasNext() && !scWrite.next().equals("Exit")) {
                        try {
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            writer.write(scWrite.next()+"\n");
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
                // 读
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if ( null != reader.readLine() ){
                        System.out.println("["+sdf.format(new Date())+"]"+socket.getPort()+"说:"+reader.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
