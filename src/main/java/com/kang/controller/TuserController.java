package com.kang.controller;

import com.kang.domain.Money;
import com.kang.domain.TUser;
import com.kang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class TuserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/query/{id}")
    public TUser testQuery(@PathVariable("id") int id) {
        return userService.selectTuserById(id);
    }

    @PostMapping("/register")
    public String register(@RequestBody TUser tUser){
       int status = userService.register(tUser);
       if(status == -1){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"用户已存在",new Exception());
       }
       return "注册成功";
    }

    @PostMapping("/login")
    public void login(@RequestBody TUser tUser){
        int status = userService.login(tUser);
        if(status == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "密码错误", new Exception());
        }else if(status == -1){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在", new Exception());
        }else if(status == 1){
            System.out.println("登录成功");
            return;
        }
    }


    /**
     * 查询余额
     * @param tUser
     * @return
     */
    @PostMapping("/inquiry")
    public double getBalance(@RequestBody TUser tUser){
        if (userService.selectTuserById(tUser.getUserId()) == null){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在", new Exception());
        }
        return userService.getBalance(tUser.getUserId());
    }

    /**
     * 存款
     * @param money
     */
    @PostMapping("/deposit")
    public String deposit(@RequestBody Money money){
        TUser tUser = userService.selectTuserById(money.getUserId());
        double addmoney = money.getMoney();

        if (tUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在", new Exception());
        }

        int status = userService.deposit(tUser,addmoney);
        if(status == -1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"存款负数",new Exception());

        return "存款成功";
    }

    @PostMapping("/withdrawals")
    public String withdrawals(@RequestBody Money money){
        TUser tUser = userService.selectTuserById(money.getUserId());
        if(tUser == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在", new Exception());

        double withdrawlMoney = money.getMoney();
        int status = userService.withdrawals(tUser,withdrawlMoney);

        if(status == -1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "取款为负", new Exception());
        } else if (status == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "余额不足", new Exception());
        return  "取款成功";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody Map<String ,String> map){
        String fromId = map.get("fromId");
        String toId = map.get("toId");
        double money = Double.parseDouble(map.get("money"));

        TUser fromTuser = userService.selectTuserById(Integer.parseInt(fromId));
        TUser toTuser = userService.selectTuserById(Integer.parseInt(toId));

        if(fromTuser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在", new Exception());
        } else if (toTuser == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "对方用户不存在", new Exception());

        int status = userService.transfer(fromTuser,toTuser,money);
        if(status == -1){
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST,"余额不足",new Exception());
        } else if (status == 0)
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST,"转账金额异常！",new Exception());
        return "转账成功";
    }




//    @RequestMapping("/insert")
//    public List<TUser> testInsert() {
//        userService.insertService();
//        return userService.selectAllUser();
//    }
//
//
//    @RequestMapping("/changemoney")
//    public List<User> testchangemoney() {
//        userService.changemoney();
//        return userService.selectAllUser();
//    }
//
//    @RequestMapping("/delete")
//    public String testDelete() {
//        userService.deleteService(3);
//        return "OK";
//    }

}
