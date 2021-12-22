package com.qzlydao.swagger.controller;

import com.qzlydao.swagger.domain.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liuqiang
 * Date: 2021-12-22 上午8:26
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello swagger";
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getUser")
    public User getUser(@ApiParam("用户名") @RequestParam String username) {
        return new User(username, "12345");
    }

}
