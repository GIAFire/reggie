package com.wjb.reggie.handler;

import com.wjb.reggie.common.CustomException;
import com.wjb.reggie.common.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 全局异常处理类
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 捕获unique重名异常
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public ResultInfo duplicateKeyExceptionHandler(DuplicateKeyException dke) {
        // 将异常信息记录日志
        log.error(dke.getMessage());
        // 客户端友情提示
        return ResultInfo.error("名称重复了，请更换一个");
    }

    // 非预期异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultInfo exceptionHandler(Exception ex) {
        // 将异常信息记录日志
        log.error(ex.getMessage());
        // 客户端友情提示
        return ResultInfo.error("服务器忙，请稍后重试");
    }

    // 预期异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResultInfo customeExceptionHandler(CustomException ce){
        // 1.将异常信息记录到日志
        log.error(ce.getMessage());
        // 2.客户端友情提示
        return ResultInfo.error(ce.getMessage());
    }
}
