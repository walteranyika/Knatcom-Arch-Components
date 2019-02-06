package com.walter.retrofit;

import com.walter.User;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by walter on 2/5/19.
 */

public interface UserService {
    @GET("index.php")
    Call<User> getUser();
}
