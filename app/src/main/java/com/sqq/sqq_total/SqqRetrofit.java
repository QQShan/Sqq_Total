package com.sqq.sqq_total;

import android.content.Context;
import android.util.Log;

import com.sqq.sqq_total.utils.CacheUtils;
import com.sqq.sqq_total.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CacheControl;
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

    final GetAPi getAPi;
    public SqqRetrofit(Context context){
        File baseDir = CacheUtils.getAndroidDataPath(context);
        File cacheDir = new File(baseDir,AppConfig.HTTP_RESPONSE_DISK_CACHE_PATH);
        Cache cache = new Cache(cacheDir,AppConfig.HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/
        OkHttpClient client = new OkHttpClient.Builder()
                //.addInterceptor(interceptor)  //输出网络请求和响应结果的log
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                //.addNetworkInterceptor(mTokenInterceptor)
                //.authenticator(mAuthenticator)
                .addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(cacheInterceptor)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BaseURL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getAPi = retrofit.create(GetAPi.class);
    }

    /**
     * 给所有网络请求添加头部
     * 作用是可以判断是否有登录
     */
    Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //chain.proceed(oRequest);    //存在于所有http工作发生的地方，生成满足请求的响应
            Request oRequest = chain.request();
            Log.d("sqqq","请求报文"+oRequest.headers().toString());
            if(UserConfig.Token.equals("")||oRequest.header(AppConfig.Authorization)!=null){
                //如果请求中已经带有Authorization或者还未登录（登录之后才有token）
                Response response = chain.proceed(oRequest);
                return response;
            }
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

    /**
     * 没有网使用缓存，有网根据请求头
     */
    Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!NetWorkUtil.getInstance().isConnected()) {
                //如果没有网络
                request = request.newBuilder()
                        //强制使用缓存
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Log.d("sqqq","请求"+request.headers().toString());
            Response response = chain.proceed(request);
            if (NetWorkUtil.getInstance().isConnected()) {
                //如果有网络
                String cacheControl = request.cacheControl().toString();
                Response response1 = response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")//移除干扰信息
                        .build();
                Log.d("sqqq","响应"+response1.headers().toString());
                return response1;
            }

            return response;
        }
    };

    public GetAPi getApiService() {
        return getAPi;
    }
}
