package com.wjb.reggie.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.wjb.reggie.common.Constant;
import com.wjb.reggie.common.CustomException;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.common.SmsTemplate;
import com.wjb.reggie.domain.User;
import com.wjb.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private HttpSession session;

    @Autowired
    private SmsTemplate smsTemplate;

    // 发送短信
    @PostMapping("/user/sendMsg")
    public ResultInfo sendMsg(@RequestBody Map<String, String> param) {// 接收请求参数
        // 1.取出手机号
        String phone = param.get("phone");
        // 2.生成6位随机数
         String code = RandomUtil.randomNumbers(4);
//        String code = "123"; // TODO 开发期验证写死，上线修改回来
        session.setAttribute("phone_sms:" + phone, code); // session中存储
        // 3.调用第三方接口发送
        // smsTemplate.sendSms(phone, code); // TODO 开发期不做短信发送，上线修改回来
        // 4.返回成功
        return ResultInfo.success(code);

    }

    @Autowired
    private UserService userService;

    // 登录注册
    @PostMapping("/user/login")
    public ResultInfo login(@RequestBody Map<String, String> param) {
        // 1.接收请求参数：手机号和验证码
        String phone = param.get("phone");
        String code = param.get("code");
        // TODO 在学习redis之前 我们的验证码判断 暂时在controller中解决
        String codeFromSession = (String) session.getAttribute("phone_sms:" + phone);
        if (!StrUtil.equals(code, codeFromSession)) {
            throw new CustomException("验证码不一致.....");
        }

        // 2.调用service登录
        ResultInfo resultInfo = userService.login(code, phone);
        // 3.如果登录成功
        if (resultInfo.getCode() == 1) {
            User user = (User) resultInfo.getData();
            session.setAttribute(Constant.SESSION_USER, user);
        }
        // 4.返回结果
        return resultInfo;
    }

    // 用户退出
    @PostMapping("/user/logout")
    public ResultInfo logout(){
        session.invalidate();
        return ResultInfo.success(null);
    }
}
