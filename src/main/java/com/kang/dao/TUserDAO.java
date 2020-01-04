package com.kang.dao;

import com.kang.domain.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TUserDAO继承基类
 */
@Mapper
@Repository
public interface TUserDAO extends MyBatisBaseDao<TUser, Integer> {
    TUser selectTuserByName(String username);
}