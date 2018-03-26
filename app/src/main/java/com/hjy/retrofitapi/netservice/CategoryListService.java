package com.hjy.retrofitapi.netservice;

import com.hjy.retrofitapi.bean.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * <pre>
 *     author : HJY
 *     time   : 2018/03/13/10:34
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public interface CategoryListService {
    //?plat=1001&devid=0f607264fc6318a92b9e13c65db7cd3c&pcode=0201meizu_100021&version=1.2.7
    @GET("category/getList")
    Call<CategoryResponse> categoryList();
/*
    @Headers({
            "Accept: application/vnd.yourApi.v1.full+json",
            "User-Agent: Your-App-Name"})
    @GET("category/getList")
    Call<CategoryResponse> categoryList(@Header("contentRange") String contentRange);
*/
}

