package com.wujinkun.helloserver.controller;

import com.wujinkun.helloserver.common.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口层（实验要求：所有接口统一返回Result<T>格式）
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    // 登录接口（全局放行，无需Token）：POST /api/users/login
    @GetMapping("/login") // 改为支持GET请求，适配浏览器直接访问
    public Result<String> login(@RequestParam String username, @RequestParam String password) {
        // 原有逻辑完全不变
        if ("admin".equals(username) && "123456".equals(password)) {
            return Result.success("登录成功，令牌：admin-token-123");
        }
        return Result.success("用户名或密码错误");
    }

    // 新增用户接口（公开放行，无需Token）：POST /api/users
    @PostMapping
    public Result<String> createUser() {
        return Result.success("新增用户成功，用户ID：1001");
    }

    // 查询用户接口（公开放行，无需Token）：GET /api/users/{id}
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable Long id) {
        String data = "查询成功，正在返回ID为" + id + "的用户信息";
        return Result.success(data);
    }

    // 删除用户接口（敏感操作，需Token鉴权）：DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        return Result.success("删除成功，ID为" + id + "的用户已移除");
    }

    // 修改用户接口（敏感操作，需Token鉴权）：PUT /api/users/{id}
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable Long id) {
        return Result.success("修改成功，ID为" + id + "的用户信息已更新");
    }
}