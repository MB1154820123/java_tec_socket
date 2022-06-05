package com.example.socket.service;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class ServerReadThread extends  Thread {
    private Set<Socket> socketSet;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ServerReadThread(Set<Socket> set) {
        this.socketSet = set;
    }

    @SneakyThrows
    @Override
    public void run() {
        while ( true ) {
            for (Socket socket : socketSet) {
                // 读
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    System.out.println("[" + sdf.format(new Date()) + "]" + socket.getPort() + "说:" + reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
