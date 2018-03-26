package com.hjy.retrofitapi.http;

import com.hjy.retrofitapi.bean.CategoryResponse;
import com.hjy.retrofitapi.callback.ApiCallback;
import com.hjy.retrofitapi.callback.ApiCallbackImpl;
import com.hjy.retrofitapi.netservice.CategoryListService;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * <pre>
 *     author : HJY
 *     time   : 2018/03/13/13:44
 *     desc   : 所有的网络请求
 *     version: 当前版本号
 * </pre>
 */
public class HttpApis {
    private static RequestManager requestManager;

    static {
        requestManager = RequestManager.getInstance();
    }

    /**
     * 用来取消请求
     *
     * @param calls
     */
    public static void cancelRequest(Call... calls) {
        for (Call call : calls) {
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
        }
    }

    public static Call categoryList(ApiCallback<CategoryResponse> callback) {
        Call<CategoryResponse> listCall = requestManager
                .create(CategoryListService.class)
                .categoryList();
        requestManager.enqueue(
                listCall,
                new ApiCallbackImpl<>(callback)
        );

//        listCall.cancel(); // 取消请求
        return listCall;
    }

    public static Call categoryListOriginal(Callback<CategoryResponse> callback) {
        CategoryListService service = requestManager.create(CategoryListService.class);
        Call<CategoryResponse> listCall = service.categoryList();
        listCall.enqueue(callback);
        return listCall;
    }
}
