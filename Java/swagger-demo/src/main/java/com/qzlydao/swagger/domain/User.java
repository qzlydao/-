package com.qzlydao.swagger.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liuqiang
 * Date: 2021-12-22 上午9:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息", discriminator = "用户实体类")
public class User {

    @ApiModelProperty(value = "用户名", dataType = "String", required = true)
    private String username;
    @ApiModelProperty(value = "密码", dataType = "String", required = true)
    private String password;

}
