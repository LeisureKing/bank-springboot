package com.kang.dao;

import com.kang.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRoleDAO继承基类
 */
@Mapper
@Repository
public interface UserRoleDAO extends MyBatisBaseDao<UserRole, UserRole> {
    List<UserRole> selectByUserId(int userId);
}