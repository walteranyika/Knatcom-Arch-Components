package com.walter.repository;

import android.os.Handler;
import android.util.Log;

import com.walter.User;
import com.walter.retrofit.UserService;



import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by walter on 2/5/19.
 */

public class UsersRepository {
    Retrofit mRetrofit;

    public UsersRepository() {
        OkHttpClient.Builder client= new OkHttpClient.Builder();
        mRetrofit=new Retrofit.Builder()
                .baseUrl("https://emobilis.ac.ke/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

    }
    public void makeFrequentCalls(){
        final Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
              fetchAfterXSeconds();
              handler.postDelayed(this, 5000);
            }
        };
        handler.post(runnable);
    }

    String TAG="USER_REPO_DATA";
    private void  fetchAfterXSeconds(){
        UserService userService= mRetrofit.create(UserService.class);
        Call<User> callAsync = userService.getUser();
        callAsync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user= response.body();
                Log.d(TAG, "onResponse: "+user.getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                t.printStackTrace();

            }
        });
    }
}
