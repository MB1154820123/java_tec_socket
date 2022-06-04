package com.example.socket.service;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
         Socket socket = new Socket("127.0.0.1", 8080);
         ClientThread ct = new ClientThread(String.valueOf(socket.getLocalPort()),socket);
         ct.my_start();
    }

}
