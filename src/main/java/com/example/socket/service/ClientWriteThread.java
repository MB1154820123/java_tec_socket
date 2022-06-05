package com.example.socket.service;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientWriteThread extends Thread {
    private Socket socket;
    Scanner sc = new Scanner(System.in);
    public ClientWriteThread(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {
        while ( true ) {
            try {
                String temp = sc.next();
                    // 写
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write( temp + "\n");
                    writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
