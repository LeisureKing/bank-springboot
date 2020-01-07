package com.kang.dao;

import com.kang.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * RoleDAO继承基类
 */
@Mapper
@Repository
public interface RoleDAO extends MyBatisBaseDao<Role, Integer> {
}