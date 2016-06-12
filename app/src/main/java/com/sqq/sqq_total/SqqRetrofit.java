package com.sqq.sqq_total;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sqq on 2016/6/12.
 */
public class SqqRetrofit {

    public SqqRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)  //输出网络请求和响应结果的log
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(mTokenInterceptor)
                .authenticator(mAuthenticator)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BaseURL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 给所有网络请求添加头部
     * 作用是可以判断是否有登录
     */
    Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oRequest = chain.request();
            if(UserConfig.Token==null||oRequest.header(AppConfig.Authorization)!=null){
                //如果请求中已经带有Authorization或者还未登录（登录之后才有token）
                return chain.proceed(oRequest);
            }
            //chain.proceed(oRequest);    //存在于所有http工作发生的地方，生成满足请求的响应
            Request authorised = oRequest.newBuilder()
                    .header(AppConfig.Authorization, UserConfig.Token)
                    .build();
            return chain.proceed(authorised);
        }
    };

    /**
     * 401没有权限的时候
     */
    Authenticator mAuthenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
            //刷新token
            UserConfig.getToken();
            //添加头部token属性
            return response.request().newBuilder()
                    .addHeader(AppConfig.Authorization,UserConfig.Token)
                    .build();
        }
    };
}
