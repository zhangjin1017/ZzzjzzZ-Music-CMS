package com.zj.service.impl;


import com.zj.service.VerifyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author 1216916137
 */
@Service
public class VerifyServiceImpl implements VerifyService {
    @Resource
    JavaMailSender sender;
    @Value("${spring.mail.username}")
    String from;
    @Override
    public int sendVerifyCode(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("【ZzzjzzZ-Music】您的验证码");
        Random random = new Random();
        int code =random.nextInt(899999)+100000;
        message.setText("您的注册码为："+code+",三分钟内有效，请及时完成注册！如果不是本人操作，请忽略。");
        message.setTo(email);
        //邮件发送者，这里要与配置文件中的保持一致
        message.setFrom(from);
        //OK
        sender.send(message);
        return code;
    }


}
