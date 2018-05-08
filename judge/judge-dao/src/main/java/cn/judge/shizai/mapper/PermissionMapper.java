package cn.judge.shizai.mapper;

import java.util.List;

import cn.judge.shizai.entity.Permission;

public interface PermissionMapper {
	public List<Permission> findAll();
	public List<Permission> findByAdminUserId(int eid);
}