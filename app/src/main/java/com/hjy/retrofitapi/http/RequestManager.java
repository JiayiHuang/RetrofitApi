package com.hjy.retrofitapi.http;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.hjy.retrofitapi.callback.ApiCallbackImpl;
import com.hjy.retrofitapi.utils.HttpsUtils;

import java.io.File;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : HJY
 *     time   : 2018/03/14/14:15
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class RequestManager {
    public static final int DEFAULT_TIMEOUT = 10;
    private static RequestManager instance;
    private Retrofit retrofit;

    private RequestManager() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File httpCacheDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/v1video", "cacheData");
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        // 域名验证
//                        return false;
//                    }
//                })
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)// Https 证书认证相关
//                .cache(cache)
                .addInterceptor(new OkHttpInterceptor());
        if (!CustomParams.IS_DEBUG) {
            builder.proxy(Proxy.NO_PROXY);
        }
        OkHttpClient client = builder
                // 日志拦截器
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomParams.REQUEST_HTTP_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ApiConvertFactory.create())
                .client(client)
                .build();
    }

    public static RequestManager getInstance() {
        if (instance == null)
            synchronized (RequestManager.class) {
                if (instance == null)
                    synchronized (RequestManager.class) {
                        return instance = new RequestManager();

                    }
                return instance;
            }
        return instance;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    public void enqueue(@NonNull Call call, @NonNull ApiCallbackImpl callback) {
        call.enqueue(callback);
    }
}
