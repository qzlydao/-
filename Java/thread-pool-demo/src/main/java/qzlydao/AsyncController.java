package qzlydao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
