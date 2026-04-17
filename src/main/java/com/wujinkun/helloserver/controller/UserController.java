package com.wujinkun.helloserver.controller;

import com.wujinkun.helloserver.common.Result;
import com.wujinkun.helloserver.dto.UserDTO;
import com.wujinkun.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping
    public Result<String> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    /**
     * 查询用户（公开接口）【实验五修改：改为调用真实数据库】
     */
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable Long id) {
        // 原来的假返回：return Result.success("查询用户成功，ID: " + id);
        // 现在改成调用Service的真实查询方法
        return userService.getUserById(id);
    }

    /**
     * 删除用户（需要Token）
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        return Result.success("删除用户成功，ID: " + id);
    }
}