package com.auyen.common.vo;

import com.auyen.common.constant.HttpStatusEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wufachun
 */

@Data
public class R<T> {
    private Boolean success;
    private Integer code;
    private String message;

    private T data;

    // 把构造方法私有化
    private R() {}

    // 把构造方法私有化
    private R(T data) {
        this.data = data;
    }

    // 成功静态方法
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }


    // 失败静态方法
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(201);
        r.setMessage("失败");
        return r;
    }

    // 失败静态方法
    public static R error(HttpStatusEnum httpStatus) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(httpStatus.getCode());
        r.setMessage(httpStatus.getMsg());
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(T data){
        this.data=data;
        return this;
    }


}
