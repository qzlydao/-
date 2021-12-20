package com.qzlydao._02_CreateThread;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liuqiang
 * Date: 2021-12-19 下午9:31
 */
public class _03_CreateThread_FutureTask {

    public static void main(String[] args) {
        Callable<String> callable = () -> {
            System.out.println("我是子任务");
            return "sub task done.";
        };

        FutureTask<String> task = new FutureTask<>(callable);

        Thread thread = new Thread(task);

        thread.start();

        System.out.println("子线程启动");

        try {
            String subResult = task.get(5, TimeUnit.SECONDS);
            System.out.println("子线程返回结果:" + subResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("main 结束");

    }

}
