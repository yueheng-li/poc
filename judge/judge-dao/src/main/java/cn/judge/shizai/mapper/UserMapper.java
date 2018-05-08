package cn.judge.shizai.mapper;

import cn.judge.shizai.entity.User;

public interface UserMapper {
	 public User findByUserName(String ename);
	 
	 public int insertUser(User user);
	 public int updateUserByKey(User user);
	 public int insertUserRole(User user);
}