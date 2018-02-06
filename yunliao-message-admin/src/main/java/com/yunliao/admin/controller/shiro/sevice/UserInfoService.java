package com.yunliao.admin.controller.shiro.sevice;


import com.yunliao.admin.controller.shiro.entity.UserInfo;

public interface UserInfoService {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);
}