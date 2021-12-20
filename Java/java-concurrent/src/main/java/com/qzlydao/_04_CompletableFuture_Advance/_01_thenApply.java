package com.qzlydao._04_CompletableFuture_Advance;

import com.qzlydao.SmallTool;

import java.util.concurrent.CompletableFuture;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liuqiang
 * Date: 2021-12-19 下午10:43
 */
public class _01_thenApply {

    public static void main(String[] args) {
        // 场景: 小白吃完饭结账，并要求开发票，结账和开发票的是两个服务员同时进行
        SmallTool.printTimeAndThread("小白吃好了");
        SmallTool.printTimeAndThread("小白 结账、要求开发票");

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("服务员收款 500元");
            SmallTool.sleepMillis(100);
            return "500";
            // thenApplyAsync接受前一个线程的输出结果作为入参，再开启一个异步线程处理任务
        }).thenApplyAsync(money -> {
            SmallTool.printTimeAndThread(String.format("服务员开发票 面额 %s元", money));
            SmallTool.sleepMillis(200);
            return String.format("%s元发票", money);
        });

        SmallTool.printTimeAndThread("小白 接到朋友的电话，想一起打游戏");
        SmallTool.printTimeAndThread(String.format("小白拿到%s，准备回家", cf.join()));

    }

}
