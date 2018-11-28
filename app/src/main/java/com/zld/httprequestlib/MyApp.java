package com.zld.httprequestlib;

import android.app.Application;

import com.zld.httplib.HttpUtil;
import com.zld.httplib.interfaze.IHttpLoader;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lingdong on 2018/11/28.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initHttpLib();

    }

    private void initHttpLib() {
        HttpUtil.getInstance().init(this, true, new IHttpLoader() {
            @Override
            public Retrofit getRetrofit() {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(loggingInterceptor)
                        .retryOnConnectionFailure(true);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://gank.io")
                        .client(builder.build())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                return retrofit;
            }
        });
    }
}
