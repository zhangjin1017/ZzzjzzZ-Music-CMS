package com.zj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
