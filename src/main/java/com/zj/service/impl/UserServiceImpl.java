package com.zj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zj.dao.UserDao;
import com.zj.entity.User;
import com.zj.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
