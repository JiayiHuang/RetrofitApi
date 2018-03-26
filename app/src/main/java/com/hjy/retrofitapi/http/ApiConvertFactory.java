package com.hjy.retrofitapi.http;

import com.google.gson.Gson;
import com.hjy.retrofitapi.bean.base.BaseResponseBean;
import com.hjy.retrofitapi.utils.StringUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ApiConvertFactory extends Converter.Factory {
    private static final String TAG = "ApiConvertFactory";

    public static ApiConvertFactory create() {
        return create(new Gson());
    }

    public static ApiConvertFactory create(Gson gson) {
        return new ApiConvertFactory(gson);
    }

    private ApiConvertFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new GsonResponseBodyConverter<>(type);
    }

    final class GsonResponseBodyConverter<T extends BaseResponseBean> implements Converter<ResponseBody, T> {
        private final Type type;

        GsonResponseBodyConverter(Type type) {
            this.type = type;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            BaseResponseBean baseResponse;
            String reString;
            try {
                reString = value.string();
                // 解析Json数据返回TransData对象
/*
                TransData transData = TransUtil.getResponse(reString);
                try {
                    if (transData.getErrorcode().equals("0") && !TextUtils.isEmpty(transData.getInfo())) {
                        baseResponse = new Gson().fromJson(transData.getInfo(), type);
                        baseResponse.setSuccess(transData.getErrorcode().equals("0"));
                        baseResponse.setInfo(transData.getInfo());
                        baseResponse.setInfo(transData.getInfo());
                    } else {
                        baseResponse = (BaseResponse) StringUtil.getObject(((Class) type).getName());
                        baseResponse.setSuccess(transData.getErrorcode().equals("0"));
                        baseResponse.setInfo(transData.getInfo());
                        baseResponse.setInfo(transData.getInfo());
                    }
                    return (T) baseResponse;
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            //从不返回一个空的Response.
            baseResponse = (BaseResponseBean) StringUtil.getObject(((Class) type).getName());
            try {
/*
                baseResponse.setSuccess(false);
                //ApiConvertFactory can not recognize the response!
                baseResponse.setErrormsg("");
*/
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return (T) baseResponse;
            }
        }
    }
}