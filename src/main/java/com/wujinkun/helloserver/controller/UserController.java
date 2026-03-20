package com.wujinkun.helloserver.controller;

import com.wujinkun.helloserver.common.Result;
import com.wujinkun.helloserver.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // GET-查询用户：返回统一Result
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        // 触发异常，测试全局拦截

        String res = "查询成功，正在返回ID为" + id + "的用户信息";
        return Result.success(res);
    }

    // POST-新增用户：返回统一Result
    @PostMapping
    public Result<String> createUser(@RequestBody User user) {
        String res = "新增成功，接收到用户:" + user.getName() + ",年龄:" + user.getAge();
        return Result.success(res);
    }

    // PUT-更新用户：返回统一Result
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        String res = "更新成功，ID" + id + "的用户已修改为:" + user.getName();
        return Result.success(res);
    }

    // DELETE-删除用户：返回统一Result
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        String res = "删除成功，已移除ID为" + id + "的用户";
        return Result.success(res);
    }
}