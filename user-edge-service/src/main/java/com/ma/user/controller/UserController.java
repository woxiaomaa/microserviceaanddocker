package com.ma.user.controller;

import com.google.gson.Gson;
import com.ma.thrift.user.UserInfo;
import com.ma.user.redis.RedisClient;
import com.ma.user.result.Result;
import com.ma.user.thrift.ThrifyClient;
import com.ma.user.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

/**
 * Created by mh on 2019/3/11.
 */
@RestController
public class UserController {

    @Autowired
    private ThrifyClient client;

    @Autowired
    private RedisClient redisClient;

    @GetMapping("/login")
    public Result login(@RequestParam String username,@RequestParam String password){
        try {
            UserInfo user = client.getUserService().getUserByName(username);
            if(user != null && user.getPassword().equals(password)){
                String token = UUID.randomUUID().toString().replaceAll("-", "");
                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(user,userVo);
//                Gson gson = new Gson();
                redisClient.set(token,userVo);
                return Result.ok(token);
            }else{
                return Result.errorMsg("用户名密码错误");
            }
        } catch (TException e) {
            System.out.println("用户名密码错误");
            return Result.errorMsg("用户名密码错误");
        }
    }

    @GetMapping("regist")
    public Result regist() throws TException {
        //注册功能设计
        //先进行手机或者邮箱校验，将验证码保存到redis
        //验证登录的验证码是否和redis里的一致
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("qq");
        userInfo.setPassword("2223q232");
        client.getUserService().regiserUser(userInfo);
        return Result.ok(userInfo);

    }

    @GetMapping("verify")
    public Result verify(@RequestParam(value = "mobile",required = false) String mobile,
                         @RequestParam(value = "email",required = false) String email) throws TException {
        String message = "Verify code is:";
        Random random = new Random(1000);
        boolean flag = false;
        if(StringUtils.isNotEmpty(mobile)){
            flag = client.getMessasgeService().sendMobileMessage(mobile,message+random.toString());
        }
        if(StringUtils.isNotEmpty(email)){
            flag = client.getMessasgeService().sendEmailMessage(email,message+random.toString());
        }
        if(flag) return Result.ok();
        else return Result.errorMsg("验证失败");
    }

    @GetMapping("getbiid")
    public Result getbiid(@RequestParam int id) throws TException {
        UserInfo userInfo = client.getUserService().getUserById(id);
        return Result.ok(userInfo);

    }
}
