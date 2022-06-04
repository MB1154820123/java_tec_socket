package com.example.socket.service;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable{
    private String threadName;
    private Thread thread;
    private Socket socket;

    public ClientThread(String threadName,Socket socket) {
        this.threadName = threadName;
        this.socket = socket;
    }



    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            Scanner scanner = new Scanner(System.in);
            while (true) {

                // 循环打印服务端发来的消息
                System.out.println("服务端说：" + bufferedReader.readLine());

                // 循环向服务端发消息
                if (scanner.hasNext()) {

                    bufferedWriter.write(socket.getLocalPort() + "\t说：" + scanner.next() + "\n");
                    bufferedWriter.flush();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void my_start(){
        if(null==thread){
          thread = new Thread(this,this.threadName);
          thread.start();
        }
    }

    public Socket returnSocket() throws IOException {
        if (null == socket) {
            socket = new Socket("127.0.0.1", 8080);
        }
        return  socket;

    }

}
