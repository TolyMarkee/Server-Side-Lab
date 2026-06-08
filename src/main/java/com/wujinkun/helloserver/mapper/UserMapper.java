package com.wujinkun.helloserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wujinkun.helloserver.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}