package com.itheima.reggie.test;

import io.lettuce.core.ScriptOutputType;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LOCK extends Thread{
        //5个筷子
        static ReentrantLock[] tools={
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock()};
        private int i;
        public LOCK (int  i){
            this.i=i;
            this.setName("哲学家"+i);
        }
        @Override
        public void run() {
            System.out.println(getName()+"准备吃饭了");
            synchronized (tools[i-1]){
            try{
                Thread.sleep(50);
                synchronized (tools[i==5?0:i]){
                    Thread.sleep(500);
                    System.out.println(getName()+"吃完了");
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }}

        public static void main(String[] args) throws InterruptedException {
            com.itheima.reggie.test.DeadLock deadLock = new com.itheima.reggie.test.DeadLock(1);
            com.itheima.reggie.test.DeadLock deadLock1 = new com.itheima.reggie.test.DeadLock(2);
            com.itheima.reggie.test.DeadLock deadLock2 = new com.itheima.reggie.test.DeadLock(3);
            com.itheima.reggie.test.DeadLock deadLock3 = new com.itheima.reggie.test.DeadLock(4);
            com.itheima.reggie.test.DeadLock deadLock4 = new com.itheima.reggie.test.DeadLock(5);
            deadLock.start();
            deadLock1.start();
            deadLock2.start();
            deadLock3.start();
            deadLock4 .start();
            Thread.sleep(1000);

        }
    }


