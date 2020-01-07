package com.kang.service;

import com.kang.dao.UserRoleDAO;
import com.kang.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleDAO userRoleDAO;

    public List<UserRole> listByUserId(Integer userId) {
        return userRoleDAO.selectByUserId(userId);
    }

    public void insert(UserRole userRole) {
        userRoleDAO.insert(userRole);
    }
}
