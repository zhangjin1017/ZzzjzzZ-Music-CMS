package com.zj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zj.entity.User;
import com.zj.entity.resp.RestBean;
import com.zj.service.UserService;
import com.zj.service.VerifyService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/User")
@CrossOrigin
public class UserController {

    @Resource
    UserService userService;
    @Resource
    VerifyService verifyService;
    @Resource
    RedisTemplate redisTemplate;

    @RequestMapping("/sendVerifyCode/{email}")
    public RestBean<Void> sendVerifyCode(@PathVariable String email) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getEmail, email);
        List<User> list = userService.list(lqw);
        if (list.size() == 1) {

            //先判断是否发送过验证码
            if (redisTemplate.hasKey("ZzzjzzZ-Music:"+email)) {
                //如果发送过验证码
                return new RestBean<>(500, "邮件已发送！请查看QQ邮箱！");
            } else {
                int code = verifyService.sendVerifyCode(email);
                //将code存到redis中，超时时间为3分钟
                redisTemplate.opsForValue().set("ZzzjzzZ-Music:"+email, code+"", 180, TimeUnit.SECONDS);
            }
            return new RestBean<>(200, "邮件发送成功！");
        } else {
            return new RestBean<>(500, "邮件发送失败！该邮箱不存在，请先注册！");
        }

    }

    @RequestMapping("/doVerifyCode/{email}/{code}")
    public RestBean<Void> doVerifyCode(@PathVariable String email, @PathVariable String code) {

            if (redisTemplate.opsForValue().get("ZzzjzzZ-Music:"+email).equals(code)) {
                //验证码正确
                //删除redis中的验证码
                redisTemplate.delete("ZzzjzzZ-Music:"+email);
                return new RestBean<>(200, "验证码正确！");

            } else {
                //验证码错误
                return new RestBean<>(500, "验证码错误！");
            }

    }
}

