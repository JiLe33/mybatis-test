package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
      log.info(map.toString());
      String phone=map.get("phone").toString();
      String code=map.get("code").toString();
      Object codeInSession=session.getAttribute(phone);
      if (codeInSession !=null&&codeInSession.equals(code)){
          LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
          lambdaQueryWrapper.eq(User::getPhone,phone);
          User user=userService.getOne(lambdaQueryWrapper);
          if(user==null){
              user=new User();
             user.setPhone(phone);
             user.setStatus(1);
             userService.save(user);

          }
          session.setAttribute("user",user.getId());
          return R.success(user);
      }
       return R.error("登录失败");
    }

    @PostMapping("/loginout")
    public R<String> loginout( HttpSession session){
        session.removeAttribute("user");
        return R.success("退出成功");
    }
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);

            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("瑞吉外卖","",phone,code);

            //需要将生成的验证码保存到Session
            session.setAttribute(phone,code);

            return R.success(code);
        }

        return R.error("短信发送失败");
    }


}
