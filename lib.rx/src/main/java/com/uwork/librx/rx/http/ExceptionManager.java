package com.uwork.librx.rx.http;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李栋杰
 * @time 2017/7/29  10:15
 * @desc ${TODD}
 */
public class ExceptionManager {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    public static ApiException handle(Throwable e) {
        ApiException ex;
        if (e instanceof ApiException) {
            ex = (ApiException) e;
        } else if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;

            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                    ex = new ApiException(ApiException.ERROR.UNAUTHORIZED, e);
                    ex.setDisplayMessage("权限受限");  //均视为权限受限
                    break;
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex = new ApiException(ApiException.ERROR.HTTP_ERROR, e);
                    ex.setDisplayMessage("网络错误");  //均视为网络错误
                    break;
            }
        } else if (e instanceof ServerException) {   //服务器返回的错误
            ServerException resultException = (ServerException) e;
            if (codes.contains(resultException.getCode())) {
                ex = new ApiException(ApiException.ERROR.UNAUTHORIZED, e);
                ex.setDisplayMessage(e.getMessage());  //均视为权限受限
            } else {
                ex = new ApiException(resultException.getCode(), resultException);
                ex.setDisplayMessage(resultException.getMsg());
                ex.setData(resultException.getValue());
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(ApiException.ERROR.PARSE_ERROR, e);
            ex.setDisplayMessage("解析错误");            //均视为解析错误
        } else if (e instanceof ConnectException || e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            ex = new ApiException(ApiException.ERROR.NETWORD_ERROR, e);
            ex.setDisplayMessage("连接失败,网络可能出问题了~\n请您查看网络设置");  //均视为网络错误
        } else {
            ex = new ApiException(ApiException.ERROR.UNKNOWN, e);
            ex.setDisplayMessage("未知错误");          //未知错误
        }
        return ex;
    }

    static List<String> codes = new ArrayList<String>() {{
        add("5");//默认token失效返回5
    }};//授权失效

    public static void addUnauthorizedCodes(List<String> codes) {
        for (String code : codes) {
            if (!ExceptionManager.codes.contains(code)) {
                ExceptionManager.codes.add(code);
            }
        }
    }

    public static void setUnauthorizedCodes(List<String> codes) {
        ExceptionManager.codes = codes;
    }

    public static void clearUnauthorizedCodes(List<String> codes) {
        ExceptionManager.codes.clear();
    }
}
