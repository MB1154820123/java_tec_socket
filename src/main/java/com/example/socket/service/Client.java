package com.example.socket.service;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
         Socket socket = new Socket("127.0.0.1", 9090);
         ClientWriteThread cwt = new ClientWriteThread(socket);
         ClientReadThread crt = new ClientReadThread(socket);
         cwt.start();
         crt.start();
    }

}
