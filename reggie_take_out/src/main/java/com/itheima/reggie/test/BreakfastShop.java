package com.itheima.reggie.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BreakfastShop {
    private int balance = 10; // 老板有一张20元的钞票
    private Lock lock = new ReentrantLock();
    private Condition enoughMoney = lock.newCondition();
    public void buyBreakfast(String name, int money) {
        lock.lock();
        try {
            while (balance < money) {
                enoughMoney.await(); // 没有足够的零钱，等待
            }
            System.out.println(name + " 准备购买早餐");
            balance += money;
            System.out.println(name + " 购买早餐成功，老板余额：" + balance);
            enoughMoney.signalAll(); // 唤醒其他等待的线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }   
    public static void main(String[] args) {
        BreakfastShop breakfastShop = new BreakfastShop();

        Thread zhangSan = new Thread(() -> {
            breakfastShop.buyBreakfast("张三", 5);
        });
        Thread liSi = new Thread(() -> {
            breakfastShop.buyBreakfast("李四", 5);
        });
        Thread wangwu = new Thread(() -> {
            breakfastShop.buyBreakfast("王五", 50);
        });

        zhangSan.start();
        liSi.start();
        wangwu.start();
    }
}

