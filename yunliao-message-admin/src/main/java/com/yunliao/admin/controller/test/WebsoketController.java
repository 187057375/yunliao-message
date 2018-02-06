package com.yunliao.admin.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebsoketController {
    @RequestMapping({ "/websocket/test"})
    public String test(){
        return"/websocket/test";
    }



}