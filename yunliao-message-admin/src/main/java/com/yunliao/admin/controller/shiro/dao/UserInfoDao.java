package com.yunliao.admin.controller.shiro.dao;

import com.yunliao.admin.controller.shiro.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoDao extends CrudRepository<UserInfo, Long> {
    /**
     * 通过username查找用户信息;
     */
    public UserInfo findByUsername(String username);
}