package com.qzlydao._02_CreateThread;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liuqiang
 * Date: 2021-12-19 下午9:16
 */
public class _01_CreateThread_Run {

    public static void main(String[] args) {
        Thread thread = new Thread() {
            /**
             * When an object implementing interface <code>Runnable</code> is used
             * to create a thread, starting the thread causes the object's
             * <code>run</code> method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method <code>run</code> is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("我是子线程");
            }
        };
        thread.start();
        System.out.println("main 结束");

    }
}
