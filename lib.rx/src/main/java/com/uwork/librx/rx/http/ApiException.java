package com.uwork.librx.rx.http;

/**
 * @author 李栋杰
 * @time 2017/7/29  10:08
 * @desc 将HTTP错误以及API错误统一成此类信息
 */
public class ApiException extends Exception {


    /**
     * @LINK(ApiException.ERROR)
     */
    private String code;
    private String displayMessage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Object data;

    public ApiException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }
    public ApiException(String code, String msg) {
        super(msg);
        this.code = code;
        this.displayMessage = msg;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }


    public class ERROR {
        /**
         * 未知错误
         */
        public static final String UNKNOWN = "1000";
        /**
         * 解析错误
         */
        public static final String PARSE_ERROR = "1001";
        /**
         * 网络错误
         */
        public static final String NETWORD_ERROR = "1002";
        /**
         * 协议出错
         */
        public static final String HTTP_ERROR = "1003";
        /**
         * 权限受限
         */
        public static final String UNAUTHORIZED = "1004";
        /**
         * 参数异常
         */
        public static final String PARAM_ERROR = "1005";
    }
}