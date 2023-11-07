package com.yixue.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ContentApplication$
 *
 * @author liy
 * @date 2023/11/5$
 */
@SpringBootApplication(scanBasePackages = "com.yixue")
@EnableSwagger2
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class);
    }
}
