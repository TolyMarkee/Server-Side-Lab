package com.wujinkun.helloserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
// 👇 新增分页导入包
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wujinkun.helloserver.common.Result;
import com.wujinkun.helloserver.common.ResultCode;
import com.wujinkun.helloserver.dto.UserDTO;
import com.wujinkun.helloserver.entity.User;
import com.wujinkun.helloserver.mapper.UserMapper;
import com.wujinkun.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID; // 导入UUID，用于生成Token

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO userDTO) {
        // 1. 判断用户名是否存在
        User exist = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userDTO.getUsername()));
        if (exist != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }
        // 2. 写入数据库
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        userMapper.insert(user);
        return Result.success("注册成功，数据已存入数据库");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        // 1. 查询数据库验证
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userDTO.getUsername()));
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        if (!user.getPassword().equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }
        // 2. 登录成功，返回Token
        return Result.success("登录成功，Token: Bearer-" + UUID.randomUUID());
    }

    // 【实验五新增】实现根据ID查询用户
    @Override
    public Result<String> getUserById(Long id) {
        // 调用MyBatis-Plus的selectById查询数据库
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        return Result.success("查询用户成功：" + user.toString());
    }

    // 第六次实验新增：分页查询
    @Override
    public Result<Page<User>> getUserPage(Integer pageNum, Integer pageSize) {
        // 构建分页对象：当前页、每页条数
        Page<User> page = new Page<>(pageNum, pageSize);
        // 执行分页查询（无查询条件，查询全部用户）
        Page<User> userPage = userMapper.selectPage(page, null);
        // 返回分页结果
        return Result.success(userPage);
    }
}