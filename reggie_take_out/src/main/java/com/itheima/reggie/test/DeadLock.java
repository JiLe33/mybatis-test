package com.itheima.reggie.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;



public class DeadLock extends Thread{
    //5个筷子
    static ReentrantLock[] tools={
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock()};
    private int i;
    public DeadLock (int  i){
        this.i=i;
        this.setName("哲学家"+i);
    }
    @Override
    public void run() {
         try{
             while (true){
                 System.out.println(getName()+"准备吃饭");
                 tools[i-1].lock();
                 Thread.sleep(5);
                 if (tools[i == 5 ? 0:i].tryLock(2, TimeUnit.SECONDS)){
                     Thread.sleep(50);
                     System.out.println(getName()+"吃完了");
                     tools[i-1].unlock();
                     tools[i==5?0:i].unlock();
                     break;
                 } else {
                     System.out.println(getName()+"没有取到右筷子，故释放左筷子");
                     tools[i-1].unlock();
                 }
             }
//             System.out.println(getName()+"准备吃饭了");
//             synchronized (tools[i-1]){
//                 try{
//                     Thread.sleep(50);
//                     synchronized (tools[i==5?0:i]){
//                         Thread.sleep(500);
//                         System.out.println(getName()+"吃完了");
//                     }
//                 }catch (InterruptedException e){
//                     e.printStackTrace();
//                 }
         }catch (InterruptedException e){
             e.printStackTrace();
         }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLock deadLock = new DeadLock(1);
        DeadLock deadLock1 = new DeadLock(2);
        DeadLock deadLock2 = new DeadLock(3);
        DeadLock deadLock3 = new DeadLock(4);
        DeadLock deadLock4 = new DeadLock(5);
        deadLock.start();
        deadLock1.start();
        deadLock2.start();
        deadLock3.start();
        deadLock4 .start();
        Thread.sleep(1000);

    }
}
