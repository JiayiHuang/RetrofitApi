package com.hjy.retrofitapi.http;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @className: OkHttpInterceptor
 * @classDescription: Http拦截器
 */
public class OkHttpInterceptor implements Interceptor {
    private static final String TAG = "OkHttpInterceptor";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取请求
        Request originalRequest = chain.request();
        // 统一添加Headers
        Request modifyHeadersRequest = originalRequest.newBuilder()
                .addHeader("User-Agent", "Your-App-Name")
                .addHeader("Accept", "application/vnd.yourapi.v1.full+json")
                .addHeader("HeaderName1", "HeaderValue1")
                .build();
        // Get Url 统一添加参数
        HttpUrl url = modifyHeadersRequest.url().newBuilder()
                .addQueryParameter("plat", "1001")
                .addQueryParameter("devid", "0f607264fc6318a92b9e13c65db7cd3c")
                .addQueryParameter("pcode", "0201meizu_100021")
                .addQueryParameter("version", "1.2.7")
                .build();
        Request modifyUrlRequest = modifyHeadersRequest.newBuilder()
                .url(url)
                .build();

        // 获得Connection，内部有route、socket、handshake、protocol方法
        Connection connection = chain.connection();
        // 如果Connection为null，返回HTTP_1_1，否则返回connection.protocol()
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        // 比如: --> POST http://121.40.227.8:8088/api http/1.1
        String requestStartMessage = "--> " + modifyUrlRequest.method() + ' ' + modifyUrlRequest.url() + ' ' + protocol;

//        Log.i(TAG, "intercept: >>>>>>> request = " + requestStartMessage);

        // 打印 Response
        Response response;
        try {
            response = chain.proceed(modifyUrlRequest);
        } catch (Exception e) {
            throw e;
        }
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            long contentLength = responseBody.contentLength();
            if (bodyEncoded(response.headers())) {

            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = UTF8;
                if (contentLength != 0) {
                    // 获取Response的body的字符串 并打印
//                    Log.i(TAG, "intercept: >>>>>>>  response = " + buffer.clone().readString(charset));
                }
            }
        } else {
            Log.e(TAG, "intercept: responseBody == null");
        }
        return response;
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
