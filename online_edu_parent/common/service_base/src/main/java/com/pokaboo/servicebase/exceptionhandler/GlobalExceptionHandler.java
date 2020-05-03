package com.pokaboo.servicebase.exceptionhandler;

import com.pokaboo.commonutils.ExceptionUtil;
import com.pokaboo.commonutils.Result;
import com.pokaboo.servicebase.handler.MyMetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常处理类
 * @author pokab
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 指定出现什么异常执行此方法
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理");
    }

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result error(MyException e) {
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

    /**
     * NullPointerException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result error(NullPointerException e) {
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message("执行了NullPointerException");
    }
}