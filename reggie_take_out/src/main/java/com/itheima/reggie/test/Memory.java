package com.itheima.reggie.test;

//查看内存情况
public class Memory {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 method();
        System.out.println("程序运行结束");
         method();

	}
    public static void method(){
        Runtime run=Runtime.getRuntime();
        long max=run.maxMemory();//最大内存
        long total=run.totalMemory();//总内存
        long free=run.freeMemory();//空闲内存
        long usable=max-total+free;//可用内存
        long using=total-free;//已用内存
        System.out.println("总内存"+total+"M");
        System.out.println("已用内存"+using+"M");
        System.out.println("可用内存"+usable+"M");
        System.out.println("空闲内存"+free+"M");
    }
}

