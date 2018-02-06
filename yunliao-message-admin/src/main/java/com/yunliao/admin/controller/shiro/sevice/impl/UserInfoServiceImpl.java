package com.yunliao.admin.controller.shiro.sevice.impl;

import com.yunliao.admin.controller.shiro.dao.UserInfoDao;
import com.yunliao.admin.controller.shiro.entity.UserInfo;
import com.yunliao.admin.controller.shiro.sevice.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}