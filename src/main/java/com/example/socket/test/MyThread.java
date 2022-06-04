package com.example.socket.test;

public class MyThread extends Thread{
    public static void main(String[] args) {
        MyThread d = new MyThread();
        d.start();
    }
    @Override
    public void run() {
        System.out.println("JVM调用了继承自Thread的方法...");
    }
}
