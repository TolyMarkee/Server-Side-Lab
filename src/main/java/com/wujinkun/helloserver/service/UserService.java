package com.wujinkun.helloserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wujinkun.helloserver.common.Result;
import com.wujinkun.helloserver.dto.UserDTO;
import com.wujinkun.helloserver.entity.User;
// 实验7新增导入（解决VO和实体类标红）
import com.wujinkun.helloserver.entity.UserInfo;
import com.wujinkun.helloserver.vo.UserDetailVO;

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
    // 【实验六新增】分页查询用户列表
    Result<Page<User>> getUserPage(Integer pageNum, Integer pageSize);

    // 【实验七新增】
    // 用户详情（多表+Redis）
    Result<UserDetailVO> getUserDetail(Long userId);
    // 更新用户扩展信息
    Result<String> updateUserInfo(UserInfo userInfo);
    // 删除用户（主表+扩展表+缓存）
    Result<String> deleteUser(Long userId);
}