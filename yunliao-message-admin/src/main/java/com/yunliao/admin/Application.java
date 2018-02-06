package com.yunliao.admin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/5
 *          Copyright 2018 by PreTang
 */
@EnableEurekaServer
@SpringBootApplication
public class Application {


    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }
}
