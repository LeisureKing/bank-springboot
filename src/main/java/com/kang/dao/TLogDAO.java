package com.kang.dao;

import com.kang.domain.TLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TLogDAO继承基类
 */
@Mapper
@Repository
public interface TLogDAO extends MyBatisBaseDao<TLog, Integer> {
}