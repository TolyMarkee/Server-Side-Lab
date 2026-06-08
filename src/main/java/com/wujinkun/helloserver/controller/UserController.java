package com.wujinkun.helloserver.controller;

import com.wujinkun.helloserver.common.Result;
import com.wujinkun.helloserver.dto.UserDTO;
import com.wujinkun.helloserver.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wujinkun.helloserver.entity.UserInfo;
import com.wujinkun.helloserver.service.UserService;
import com.wujinkun.helloserver.vo.UserDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 原有注册
    @PostMapping
    public Result<String> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    // 原有登录
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    // 原有根据ID查询
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // 原有分页
    @GetMapping("/page")
    public Result<Page<User>> getUserPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return userService.getUserPage(pageNum, pageSize);
    }

    // 实验7 新增3个接口
    // 用户详情（多表+Redis）
    @GetMapping("/{id}/detail")
    public Result<UserDetailVO> getUserDetail(@PathVariable("id") Long userId) {
        return userService.getUserDetail(userId);
    }

    // 更新用户扩展信息
    @PutMapping("/{id}/detail")
    public Result<String> updateUserInfo(@PathVariable("id") Long userId,
                                         @RequestBody UserInfo userInfo) {
        userInfo.setUserId(userId);
        return userService.updateUserInfo(userInfo);
    }

    // 删除用户（修复冲突：加 /delete 后缀）
    @DeleteMapping("/{id}/delete")
    public Result<String> deleteUser(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }
}