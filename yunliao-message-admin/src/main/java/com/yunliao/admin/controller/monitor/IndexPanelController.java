package com.yunliao.admin.controller.monitor;

import com.yunliao.admin.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author peter
 * @version V1.0 创建时间：18/2/11
 *          Copyright 2018 by PreTang
 */
@Controller
public class IndexPanelController extends BaseController{


    @RequestMapping({ "/monitor/indexPanel"})
    public String test(){
        return"/monitor/indexPanel";
    }
}
