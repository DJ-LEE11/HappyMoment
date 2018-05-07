package com.uwork.librx.rx.http;


import com.uwork.librx.rx.BaseResult;

/**
 * @author 李栋杰
 * @time 2017/7/29  10:24
 * @desc ${TODD}
 */
public class ServerException extends Exception {
    private String code;
    private String msg;
    Object value;

    public ServerException(String code, String msg) {
        this(code, msg, null);
    }

    public ServerException(String code, String msg, Object value) {
        this.code = code;
        this.msg = msg;
        this.value = value;
    }

    public ServerException(BaseResult baseResult) {
        this.code = baseResult.code+"";
        this.msg = baseResult.exception;
        this.value = baseResult.data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
