package com.example.dao;

import com.example.entity.User;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface UserMapper extends Mapper<User>, IdsMapper<User>, MySqlMapper<User> {
}