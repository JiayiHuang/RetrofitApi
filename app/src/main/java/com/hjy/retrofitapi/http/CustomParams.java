package com.hjy.retrofitapi.http;

import com.hjy.retrofitapi.BuildConfig;

/**
 * <pre>
 *     author : HJY
 *     time   : 2018/03/13/12:34
 *     desc   : 自定义的一些网络相关配置
 *     version: version
 * </pre>
 */

public class CustomParams {
    /**
     * 接口请求成功，服务端返回的状态码<br>
     * 具体值由服务器指定status
     */
    public static class HttpResponseCode {
        public static final int SUCCESS = 1;                // 1 正常
        public static final int INVALIDATE_PARAMETERS = 2;  // 2 参数异常	参数错误
        public static final int SERVER_ERROR = 3;           // 3 系统异常	后台处理异常，不显示给用户
        public static final int BUSINESS_ERROR = 4;         // 4 业务异常	业务逻辑异常，可直接显示给用户
        public static final int TOKEN_ERROR = 5;            // 5 token异常	超时，强制登出重新生成
        public static final int SPECIAL_SERVER_ERROR = 6;   // 6 其他需要特殊处理的业务异常
        public static final int UNKNOWN_EXCEPTION = 0;   // 0 异常
    }

    // 请求URL
    public final static String REQUEST_HTTP_URL = BuildConfig.API_URL;
    public final static boolean IS_DEBUG = BuildConfig.DEBUG;
    // 接口返回结果名称
    public final static String INFO = "info";
    // 接口返回错误码
    public final static String ERROR_CODE = "errorcode";
    // 接口返回错误信息
    public final static String ERROR_MSG = "errormsg";
}