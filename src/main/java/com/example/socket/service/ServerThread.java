package com.example.socket.service;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class ServerThread extends  Thread{
    private  InputStream inputStream;
    private  OutputStream outputStream;
    private  InputStreamReader inputStreamReader;
    private  OutputStreamWriter outputStreamWriter;
    private  BufferedReader bufferedReader;
    private  BufferedWriter bufferedWriter;
    private Scanner scanner;
    private Scanner scannerChoose;
    private Scanner scannerConfirm;
    private String threadName;
    private Thread t;
    private Socket currentSocket;
    private static List<Socket> socketList = new ArrayList<>();
    private static Map<String,Socket> map = new HashMap<>();
    private static String account;
    private static List<String> accounts = new ArrayList<>();


    public ServerThread(String tn, Socket s){
        this.threadName = tn;
        this.currentSocket = s;
        this.socketList.add(s);
        this.map.put(this.threadName,s);
        this.account = String.valueOf(s.getPort());
        this.accounts.add(account);
        this.scanner = new Scanner(System.in);
        this.scannerChoose = new Scanner(System.in);
        this.scannerConfirm = new Scanner(System.in);
    }

    @Override
    public void run(){


        try {
            this.inputStream = currentSocket.getInputStream();
            this.outputStream = currentSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.inputStreamReader = new InputStreamReader(this.inputStream);
        this.outputStreamWriter = new OutputStreamWriter(this.outputStream);
        this.bufferedReader = new BufferedReader(this.inputStreamReader);
        this.bufferedWriter = new BufferedWriter(this.outputStreamWriter);

        System.out.println("当前在线人数：" + accounts.size());
        System.out.println( account + "\t加入了本Socket服务...");
        if (accounts.size()==1) {
            System.out.println( "当前在线人数有1人，其账号为：\t"+ account + "\t您可以在下边直接输入您想发给他的消息，并按Enter键发送..." );
        } else if(accounts.size()>1){
            System.out.println("当前在线人数有多人，分别是:\t"+accounts+"\t请输入一个账号，然后按Enter键，以继续与其聊天...");
            if(scannerChoose.hasNext()){
                for (Socket s:socketList) {
                    if(s.getPort()==Integer.valueOf(scannerChoose.next())){
                        System.out.println("您当前选择的聊天对象是：\t" + s.getPort() + "是否选择与其聊天(Y/N)?");
                        if(scannerConfirm.hasNext()){
                            if("Y".equals(scannerConfirm.next())){
                                System.out.println("好的，下边是你与好友\t" + s.getPort() + "的聊天行，请使用...");
                            } else if ("N".equals(scannerConfirm.next())){
                                System.out.println("好的，我们将取消与好友\t" + s.getPort() + "的聊天...");
                            }
                        }
                    } else {
                        System.out.println("抱歉，您输入的账号不正确，请重试！");
                    }
                }

            }
        }

        while (true) {
            // 循环发送消息到客户端
            if ( scanner.hasNext() && accounts.size() <= 1 ) {
                try {
                    bufferedWriter.write( scanner.next() + "\n");
                    bufferedWriter.flush();
                    // 循环打印客户端发来的一行行消息，有多少发就要有多少收
                    System.out.println(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if ( scanner.hasNext() ){
                    try {
                        connectWithOneSocket(scanner.next(),socketList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }



     }

    @Override
    public void start(){
        if ( null==t ) {
            t = new Thread(this,this.threadName);
            t.start();
        }
    }

    /**
     * 打印出用户发来的消息
     */
    public void print(Socket socket,String message){
        System.out.println(socket.getPort()+"说："+message);
    }

    public Scanner connectWithOneSocket(String socketId,List<Socket> sockets) throws IOException {
        Scanner s = null;
        for (Socket socket : sockets) {
            if ( String.valueOf(socket.getPort()).equals(socketId) ) {
                System.out.println("与"+socket.getPort()+"建立了连接...");
                System.out.println("请输入您需要发送给"+socket.getPort()+"的消息：");
                s = new Scanner(System.in);
                socket.getInputStream();
                break;
            } else {
                continue;
            }
        }

        if ( null == s ) {
             System.out.println("你选则的聊天对象："+socketId+"不存在...");
        }
        return s;
    }
    public void closeWithOneSocket(Socket socket) throws InterruptedException {
        System.out.println("与"+socket.getPort()+"断开了连接...");
        socket.wait(10000);
    }
}
