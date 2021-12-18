package qzlydao.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import qzlydao.service.AsyncService;
import qzlydao.service.GeneralService;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: liuqiang
 * @Date: 2021/12/6/006 12:47
 * @Desc:
 */
@RestController
public class AsyncController {

    @Autowired
    private GeneralService generalService;

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/open/something")
    public String something() {
        int count = 10;
        for (int i = 0; i < count; i++) {
            // 在一个方法中调用另一个异步方法是不会生效的！！！！！！
            // asyncService.execute("index = " + i);
            asyncService.doSomething("index = " + i);
        }
        return "success";
    }

    @SneakyThrows
    @GetMapping("/open/somethings")
    public String somethings() {
        CompletableFuture<String> createOrder = asyncService.doSomething1("create order");
        CompletableFuture<String> reduceAccount = asyncService.doSomething2("reduce account");
        CompletableFuture<String> saveLog = asyncService.doSomething3("save log");

        // 等待所有任务都执行完
        CompletableFuture.allOf(createOrder, reduceAccount, saveLog).join();

        // 获取每个任务的返回结果
        String result = createOrder.get() + reduceAccount.get() + saveLog.get();
        return result;
    }
}
