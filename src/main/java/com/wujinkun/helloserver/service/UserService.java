package com.wujinkun.helloserver.service;

import com.wujinkun.helloserver.common.Result;
import com.wujinkun.helloserver.dto.UserDTO;

import com.wujinkun.helloserver.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * 用户业务接口
 */
public interface UserService {
    // 注册
    Result<String> register(UserDTO userDTO);
    // 登录
    Result<String> login(UserDTO userDTO);
    // 【实验五新增】根据ID查询用户
    Result<String> getUserById(Long id);
    // 【新增】分页查询用户列表
    Result<Page<User>> getUserPage(Integer pageNum, Integer pageSize);
}
