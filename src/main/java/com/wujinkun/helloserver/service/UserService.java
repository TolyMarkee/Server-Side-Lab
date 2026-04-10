package com.wujinkun.helloserver.service;

import com.wujinkun.helloserver.common.Result;
import com.wujinkun.helloserver.dto.UserDTO;

/**
 * 用户业务接口
 */
public interface UserService {
    // 注册
    Result<String> register(UserDTO userDTO);
    // 登录
    Result<String> login(UserDTO userDTO);
}