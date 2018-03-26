package com.hjy.retrofitapi.callback;

import android.support.annotation.NonNull;

import com.hjy.retrofitapi.bean.base.BaseResponseBean;
import com.hjy.retrofitapi.exceptions.NoneTipException;
import com.hjy.retrofitapi.exceptions.TipException;
import com.hjy.retrofitapi.exceptions.TokenInvalidException;
import com.hjy.retrofitapi.http.CustomParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCallbackImpl<T extends BaseResponseBean> implements Callback<T> {
    // 回调
    private ApiCallback<T> mCallback;

    public ApiCallbackImpl(@NonNull ApiCallback<T> mCallback) {
        this.mCallback = mCallback;
        this.mCallback.onBefore();
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        // Canceled or not
        if (!call.isCanceled()) {
            //Returns true if {@link #code()} is in the range [200..300)
            if (response.isSuccessful()) {

                T body = response.body();
                if (body != null) {
                    int status = body.getHeader().getStatus();
                    switch (status) {
                        case CustomParams.HttpResponseCode.SUCCESS:// 1 正常

                            mCallback.onSuccess(body);
                            break;
                        case CustomParams.HttpResponseCode.SERVER_ERROR:// 3 系统异常	后台处理异常，不显示给用户
                            mCallback.onError(new NoneTipException(body.getHeader().getMessage()));
                            break;
                        case CustomParams.HttpResponseCode.TOKEN_ERROR:// 5 token异常	超时，强制登出重新生成
                            mCallback.onError(new TokenInvalidException(body.getHeader().getMessage()));
                            break;
                        case CustomParams.HttpResponseCode.INVALIDATE_PARAMETERS:// 2 参数异常	参数错误
                        case CustomParams.HttpResponseCode.BUSINESS_ERROR:// 4 业务异常	业务逻辑异常，可直接显示给用户
                        case CustomParams.HttpResponseCode.SPECIAL_SERVER_ERROR:// 6 其他需要特殊处理的业务异常
                        case CustomParams.HttpResponseCode.UNKNOWN_EXCEPTION:// 0 其他需要特殊处理的业务异常
                        default:
                            mCallback.onError(new TipException(body.getHeader().getMessage(), status));
                            break;
                    }
                } else {// response body is null
                    mCallback.onFailure("response body is null");
                }
            } else {// http status is out of range [200, 300)
                mCallback.onError(new TipException("HTTP status code " + response.code(), response.code()));
                response.errorBody().close();
            }
        } else {// Canceled
            mCallback.onCanceled();
        }
        mCallback.onAfter();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        mCallback.onFailure(t.getLocalizedMessage());
        if (!call.isCanceled()) {
            call.cancel();
        }
        mCallback.onAfter();
    }
}
