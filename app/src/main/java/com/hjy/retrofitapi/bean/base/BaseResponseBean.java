package com.hjy.retrofitapi.bean.base;

import com.hjy.retrofitapi.bean.HeaderResponse;

/**
 * <pre>
 *     @author : HJY
 *     @time   : 2018/03/13/11:09
 *     @desc   : 接口成功返回时，用于获取返回内容中的头信息，便于进行一些判断处理
 *     @version: 当前版本号
 * </pre>
 */
public abstract class BaseResponseBean {
    protected HeaderResponse header;

    public HeaderResponse getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return "BaseResponseBean{" +
                "header=" + header +
                '}';
    }
}
