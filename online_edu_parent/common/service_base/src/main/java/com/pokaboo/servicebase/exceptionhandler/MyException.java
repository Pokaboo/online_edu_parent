package com.pokaboo.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pokaboo
 * 自定义异常
 * @AllArgsConstructor ： 生成有参构造方法
 * @NoArgsConstructor ： 生成无参构造方法
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;

    @Override
    public String toString() {
        return "MyException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }
}
