package com.qzlydao.swagger.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liuqiang
 * Date: 2021-12-22 上午8:35
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment) {

        Profiles profiles = Profiles.of("dev", "test");

        boolean flag = environment.acceptsProfiles(profiles);

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("智能推荐后端接口查询")
                .description("查询验证推荐效果")
                .version("1.0.0")
                .contact(new Contact("liuqiang", "qzlydao@126.com", "qzlydao@126.com"))
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(flag)
                .groupName("rs-backen")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qzlydao.swagger.controller"))
                .paths(PathSelectors.any())
                .build();

    }

}
