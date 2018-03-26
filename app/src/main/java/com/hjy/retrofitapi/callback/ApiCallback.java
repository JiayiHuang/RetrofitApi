package com.hjy.retrofitapi.callback;

import com.hjy.retrofitapi.bean.base.BaseResponseBean;
import com.hjy.retrofitapi.http.CustomParams.HttpResponseCode;
import com.hjy.retrofitapi.utils.NetBaseResponseUtils;

public interface ApiCallback<T extends BaseResponseBean> {
    /**
     * 接口请求前，可进行显示loading等操作,<b>必定会被调用<b/>
     */
    void onBefore();

    /**
     * 接口请求后，可进行显示loading等操作,<b>必定会被调用<b/>
     */
    void onAfter();

    /**
     * 接口请求成功，Http 返回状态码在 [200, 300)<br>
     * 并且返回数据中的status状态码{@link HttpResponseCode#SUCCESS}为成功
     *
     * @param response 解析好的数据对象
     */
    void onSuccess(T response);

    /**
     * 接口请求失败，http 返回状态码不在 [200, 300)<br>
     * 或 <br>
     * 请求数据成功，但是返回数据的 status 状态码 不是成功的状态码{@link HttpResponseCode#SUCCESS}
     *
     * @param exception 可能是自定义的一些Exception或系统Exception，可以调用{@link NetBaseResponseUtils#responseHandle(Object)}进行处理
     */
    void onError(Exception exception);

    /**
     * 接口请求失败，可能是请求超时，或response 返回消息为null
     *
     * @param message 失败信息
     */
    void onFailure(String message);

    /**
     * 请求被取消
     */
    void onCanceled();
}