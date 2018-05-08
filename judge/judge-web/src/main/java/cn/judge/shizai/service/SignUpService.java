package cn.judge.shizai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.judge.shizai.entity.User;
import cn.judge.shizai.mapper.UserMapper;

@Component
public class SignUpService {
	private static Logger logger = LoggerFactory.getLogger(SignUpService.class);
	@Autowired
	private UserMapper userMapper;
	
	public int singUp(User user) {
		int insertFlg = userMapper.insertUser(user);
		if (insertFlg > 0) {
			insertFlg = userMapper.insertUserRole(user);
		}
		return insertFlg;
	}
	
	public int updateUser(User user) {
		int insertFlg = userMapper.updateUserByKey(user);
		return insertFlg;
	}
	


	public User findByEid(String eName) {

		User user = userMapper.findByUserName(eName);

		return user;
	}
}
