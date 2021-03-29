package com.example.myapplication.SendNotification;

import com.example.myapplication.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private Retrofit retrofit;
    private NotificationAPI api;
    public RetrofitInstance() {
         retrofit = new Retrofit.Builder()
                                .baseUrl(Constants.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
         api = retrofit.create(NotificationAPI.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public NotificationAPI getApi() {
        return api;
    }

    public void setApi(NotificationAPI api) {
        this.api = api;
    }
}
