package com.auto.myte.mapper;

import com.auto.myte.entity.User;

public interface UserMapper {
	 public User findByUserName(String ename);
	 
	 public int insertUser(User user);
	 public int updateUserByKey(User user);
	 public int insertUserRole(User user);
}