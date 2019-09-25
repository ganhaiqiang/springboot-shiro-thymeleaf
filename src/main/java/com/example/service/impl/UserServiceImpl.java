package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserMapper;
import com.example.entity.User;
import com.example.service.UserService;

/**
 * @Desc: TODO
 * @author: GanHaiqiang
 * @date: 2019-09-24 12:50
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User findUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public User findUserByName(String name) {
		User record = new User();
		record.setName(name);
		return userMapper.selectOne(record);
	}

}
