package com.wujinkun.helloserver.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wujinkun.helloserver.common.Result;
import com.wujinkun.helloserver.common.ResultCode;
import com.wujinkun.helloserver.dto.UserDTO;
import com.wujinkun.helloserver.entity.User;
import com.wujinkun.helloserver.entity.UserInfo;
import com.wujinkun.helloserver.mapper.UserInfoMapper;
import com.wujinkun.helloserver.mapper.UserMapper;
import com.wujinkun.helloserver.security.JwtUtil;
import com.wujinkun.helloserver.service.UserService;
import com.wujinkun.helloserver.vo.UserDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 实验9：注入JWT工具类
    @Autowired
    private JwtUtil jwtUtil;

    // Redis缓存前缀
    private static final String CACHE_KEY_PREFIX = "user:detail:";

    // ===================== 注册 =====================
    @Override
    public Result<String> register(UserDTO userDTO) {
        User exist = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userDTO.getUsername()));
        if (exist != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        userMapper.insert(user);
        return Result.success("注册成功，数据已存入数据库");
    }

    // ===================== 实验9：登录生成JWT（唯一login方法） =====================
    @Override
    public Result<String> login(UserDTO userDTO) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userDTO.getUsername()));
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        if (!user.getPassword().equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }
        // 生成JWT令牌并返回
        String jwt = jwtUtil.generateToken(userDTO.getUsername());
        return Result.success(jwt);
    }

    // ===================== 根据ID查询用户 =====================
    @Override
    public Result<String> getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        return Result.success("查询用户成功：" + user.toString());
    }

    // ===================== 分页查询 =====================
    @Override
    public Result<Page<User>> getUserPage(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> userPage = userMapper.selectPage(page, null);
        return Result.success(userPage);
    }

    // ===================== 实验7：用户详情（多表+Redis） =====================
    @Override
    public Result<UserDetailVO> getUserDetail(Long userId) {
        String key = CACHE_KEY_PREFIX + userId;
        // 1. 查Redis缓存
        String json = redisTemplate.opsForValue().get(key);
        if (json != null && !json.isBlank()) {
            try {
                UserDetailVO vo = JSONUtil.toBean(json, UserDetailVO.class);
                return Result.success(vo);
            } catch (Exception e) {
                redisTemplate.delete(key);
            }
        }
        // 2. 查数据库
        UserDetailVO detail = userInfoMapper.getUserDetail(userId);
        if (detail == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        // 3. 写入缓存
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(detail), 10, TimeUnit.MINUTES);
        return Result.success(detail);
    }

    // ===================== 实验7：更新用户信息 =====================
    @Override
    @Transactional
    public Result<String> updateUserInfo(UserInfo userInfo) {
        if (userInfo == null || userInfo.getUserId() == null) {
            return Result.error(500, "参数不能为空");
        }
        userInfoMapper.updateById(userInfo);
        redisTemplate.delete(CACHE_KEY_PREFIX + userInfo.getUserId());
        return Result.success("用户信息更新成功");
    }

    // ===================== 实验7：删除用户 =====================
    @Override
    @Transactional
    public Result<String> deleteUser(Long userId) {
        userMapper.deleteById(userId);
        userInfoMapper.delete(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUserId, userId));
        redisTemplate.delete(CACHE_KEY_PREFIX + userId);
        return Result.success("用户删除成功");
    }
}