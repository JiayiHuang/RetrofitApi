package com.hjy.retrofitapi.utils;

import com.hjy.retrofitapi.bean.base.BaseResponseBean;
import com.hjy.retrofitapi.exceptions.NoneTipException;
import com.hjy.retrofitapi.exceptions.TipException;
import com.hjy.retrofitapi.exceptions.TokenInvalidException;
import com.hjy.retrofitapi.http.CustomParams;

/**
 * <pre>
 *     author : HJY
 *     time   : 2018/03/14/15:10
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class NetBaseResponseUtils {

    /**
     * Http响应码处理
     *
     * @param obj 响应结果, 会处理为BaseResponse子类的响应结果
     * @return 是否处理过这个响应, 当响应码异常时, 返回true, 否则返回false
     */
    public static boolean responseHandle(Object obj) {
        if (obj instanceof BaseResponseBean) {
            BaseResponseBean response = (BaseResponseBean) obj;
            int status = response.getHeader().getStatus();
            switch (status) {
                case CustomParams.HttpResponseCode.SUCCESS: // 1 正常
                    return false;
                case CustomParams.HttpResponseCode.SERVER_ERROR: // 3 系统异常	后台处理异常，不显示给用户
                    throw new NoneTipException("Server System Error!");
                case CustomParams.HttpResponseCode.TOKEN_ERROR: // 5 token异常	超时，强制登出重新生成
                    String exceptionMsg = response.getHeader().getMessage();
                    throw new TokenInvalidException(exceptionMsg);
                case CustomParams.HttpResponseCode.UNKNOWN_EXCEPTION:// 0 异常
                case CustomParams.HttpResponseCode.INVALIDATE_PARAMETERS: // 2 参数异常	参数错误
                case CustomParams.HttpResponseCode.BUSINESS_ERROR: // 4 业务异常	业务逻辑异常，可直接显示给用户
                case CustomParams.HttpResponseCode.SPECIAL_SERVER_ERROR: // 6 其他需要特殊处理的业务异常
                    throw new TipException(response.getHeader().getMessage(), status);
            }
        } else {
            return false;
        }
        return false;
    }
}
