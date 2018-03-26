package com.hjy.retrofitapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hjy.retrofitapi.bean.CategoryResponse;
import com.hjy.retrofitapi.callback.ApiCallback;
import com.hjy.retrofitapi.http.HttpApis;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        HttpApis.categoryListOriginal(new Callback<CategoryResponse>() {
//            @Override
//            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
//                Log.i(TAG, "onResponse: >>>>>>> " + response.body().toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<CategoryResponse> call, Throwable t) {
//
//            }
//        });

        HttpApis.categoryList(new ApiCallback<CategoryResponse>() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onAfter() {

            }

            @Override
            public void onSuccess(CategoryResponse response) {
                CategoryResponse.CategoryBody body = response.getBody();
                List<CategoryResponse.CategoryBean> data = body.getData();

//                Log.i(TAG, "onSuccess: >>>>>>>> " + response.toString());
            }

            @Override
            public void onError(Exception exception) {

            }


            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onCanceled() {

            }
        });
    }
}
