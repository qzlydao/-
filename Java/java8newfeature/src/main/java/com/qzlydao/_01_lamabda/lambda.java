package com.qzlydao._01_lamabda;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liuqiang
 * Date: 2021-12-18 上午11:19
 */
public class lambda {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("新线程中方法被执行了...\n");
            }
        }).start();

        System.out.println("================================");
        // 使用lambda表达式改写
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("使用lambda函数实现Runnable接口");
        }).start();
    }

}
