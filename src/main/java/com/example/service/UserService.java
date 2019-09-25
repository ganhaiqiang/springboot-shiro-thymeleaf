package com.example.service;

import com.example.entity.User;

/**
 * @Desc: TODO
 * @author: GanHaiqiang
 * @date: 2019-09-24 12:50
 */
public interface UserService {

	User findUserById(Integer id);

	User findUserByName(String name);
}
