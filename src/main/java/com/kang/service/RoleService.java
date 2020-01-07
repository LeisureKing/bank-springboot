package com.kang.service;

import com.kang.dao.RoleDAO;
import com.kang.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDAO roleDAO;

    public Role selectById(int id) {
        return roleDAO.selectByPrimaryKey(id);
    }


}
