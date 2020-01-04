package com.kang.service;

import com.kang.controller.TuserController;
import com.kang.dao.TUserDAO;
import com.kang.domain.TUser;
import com.kang.exception.InvalidDepositException;
import com.kang.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {
    @Autowired
    private TUserDAO tUserDao;


    /**
     * 根据id查找用户
     */
    public TUser selectTuserById(int id) {
        return tUserDao.selectByPrimaryKey(id);
    }

    /**
     * 获取余额
     * @param id 用户id
     * @return
     */
    public double getBalance(int id){
        return  tUserDao.selectByPrimaryKey(id).getBalance();
    }

    /**
     * 登录
     * @param realTuser 前台传来的tuser
     * @return -1：不存在该用户 0：密码错误 1：匹配成功
     */
    public int login(TUser realTuser){
        TUser tUser = tUserDao.selectTuserByName(realTuser.getUserName());
        if(tUser == null){
            return -1;
        } else if(!tUser.getUserPassword().equals(MD5Utils.md5(realTuser.getUserPassword()))){
            return 0;
        }
        return  1;
    }

    /**
     * 注册
     * @param tUser 前台传来的tuser
     */
    public int register(TUser tUser){
        if(tUserDao.selectTuserByName(tUser.getUserName()) != null){
            return -1;
        }
        //md5加密
        String password = tUser.getUserPassword();
        tUser.setUserPassword(MD5Utils.md5(password));

        tUser.setBalance(0.00);
        tUser.setUserFlag(0);

        tUserDao.insert(tUser);
        return 1;
    }
    /**
     * 修改
     * @param tuser
     * @return
     */
    public int updateTuser(TUser tuser){
        return tUserDao.updateByPrimaryKey(tuser);
    }

    /**
     * 存钱
     * @param user
     * @param money
     * @return
     */
    public int deposit(TUser user , double money){
        if(money < 0){
            return -1;
        }
        user.setBalance(user.getBalance() + money);
        return tUserDao.updateByPrimaryKey(user);
    }

    /**
     * 取钱
     * @param user
     * @param money
     * @return
     */
    public int withdrawals(TUser user, double money){
        if(money < 0){
            return  -1;
        }else if (user.getBalance() - money < 0){
            return  0;
        }
        user.setBalance(user.getBalance() - money);
        return  tUserDao.updateByPrimaryKey(user);
    }

    /**
     * 转账
     * @param from
     * @param to
     * @param money
     * @return
     */
    @Transactional
    public int transfer(TUser from , TUser to , double money){
        if(from.getBalance() - money < 0){
            return -1;
        } else if (money < 0)
            return 0;
        from.setBalance(from.getBalance() - money);
        to.setBalance(to.getBalance() + money);
        tUserDao.updateByPrimaryKey(to);
        return tUserDao.updateByPrimaryKey(from);
    }


    /**
     * 根据id 删除用户
     */

    public void deleteService(int id) {
        tUserDao.deleteByPrimaryKey(id);
    }

    /**
     * 模拟事务。由于加上了 @Transactional注解，如果转账中途出了意外 SnailClimb 和 Daisy 的钱都不会改变。
     */
//    @Transactional
//    public void transfor(TUser from , TUser to , double money) {
//        if (from.getBalance()-money < 0)
//
//        tUserDao.updateByPrimaryKey(from);
//        tUserDao.updateByPrimaryKey(to);
//    }
}
