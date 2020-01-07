package com.kang.controller;

import com.kang.domain.Money;
import com.kang.domain.TUser;
import com.kang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class TuserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/query/{id}")
    public TUser testQuery(@PathVariable("id") int id) {
        return userService.selectTuserById(id);
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        TUser tUser = new TUser();
        tUser.setUserPassword(password);
        tUser.setUserName(username);

       int status = userService.register(tUser);
       if(status == -1){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"用户已存在",new Exception());
       }
       return "注册成功";
    }

    @PostMapping("/doLogin")
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("userpassword") String password, Model model) {

        System.out.println("username:" + username + ",userpassword:" + password);

        TUser tUser = new TUser();
        tUser.setUserName(username);
        tUser.setUserPassword(password);

        int status = userService.login(tUser);
        if(status == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "密码错误", new Exception());
        }else if(status == -1){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在", new Exception());
        }else if(status == 1){
            System.out.println("登录成功");

        }
        return new ModelAndView("login1", "userModel", model);
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


//    /**
//     * 获取 form 表单页面
//     * @return
//     */
//    @GetMapping("/login")
//    public ModelAndView createForm(Model model) {
//        return new ModelAndView("login1", "userModel", model);
//    }

    @RequestMapping("/login")
    public ModelAndView showLogin(Model model) {
        return new ModelAndView("login", "userModel", model);
    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin() {
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }

    @RequestMapping("/")
    public ModelAndView showHome(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("当前登陆用户：" + name);

        return new ModelAndView("home", "userModel", model);
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
