package qzlydao.service;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: liuqiang
 * @Date: 2021/12/6/006 13:43
 * @Desc:
 */
public interface AsyncService {

    String execute(String message);

    String doSomething(String message);

    CompletableFuture<String> doSomething1(String message) throws InterruptedException;

    CompletableFuture<String> doSomething2(String message) throws InterruptedException;

    CompletableFuture<String> doSomething3(String message) throws InterruptedException;

}
