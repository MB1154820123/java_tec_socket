package com.example.socket.service;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientReadThread extends Thread {
    private Socket socket;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public ClientReadThread(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {
        while ( true ) {
            try {
                // 读
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("[" + sdf.format(new Date()) + "]服务器说:" + reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }}
}
