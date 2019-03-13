package com.ma.user.service.impl;

import com.ma.thrift.user.UserInfo;
import com.ma.thrift.user.UserService;
import com.ma.user.mapper.UserMapper;
import org.apache.ibatis.javassist.bytecode.annotation.IntegerMemberValue;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by mh on 2019/3/11.
 */
@Service
public class UserServiceImpl implements UserService.Iface {
    @Autowired
    private UserMapper userMapper;


    @Override
    public UserInfo getUserById(int id) throws TException {
        UserInfo userInfo = userMapper.getUserById(id);
        return userInfo;
    }

    @Override
    public UserInfo getTeacherById(int id) throws TException {
        return null;
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        UserInfo userInfo = userMapper.getUserByName(username);
        if(userInfo!= null){
            return userInfo;
        }else{
            return null;
        }

    }

    @Override
    public void regiserUser(UserInfo userInfo) throws TException {
        userMapper.registerUser(userInfo);
    }
}