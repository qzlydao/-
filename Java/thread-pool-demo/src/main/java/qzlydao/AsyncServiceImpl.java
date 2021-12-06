package qzlydao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: liuqiang
 * @Date: 2021/12/6/006 12:47
 * @Desc:
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {


    @Override
    public String execute(String message) {
        return doSomething(message);
    }

    // 指定使用beanname为doSomethingExecutor的线程池
    @Async("doSomethingExecutor")
    public String doSomething(String message) {
        log.info("do something, message={}", message);
        try {
            sleep();
        } catch (InterruptedException e) {
            log.error("do something error: ", e);
        }
        return message;
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(1000);
    }
}
