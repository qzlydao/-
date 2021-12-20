package com.qzlydao._02_CreateThread;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liuqiang
 * Date: 2021-12-19 下午9:27
 */
public class _02_CreateThread_Runnable {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("我是子线程"));

        thread.start();

        System.out.println("main 结束");

    }

}
