package qzlydao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qzlydao.service.AsyncService;
import qzlydao.service.GeneralService;

/**
 * @Author: liuqiang
 * @Date: 2021/12/6/006 13:47
 * @Desc:
 */
@Service
public class GeneralServiceImpl implements GeneralService {

    @Autowired
    private AsyncService asyncService;


    @Override
    public String execute(String mesg) {
        return asyncService.doSomething(mesg);
    }
}
