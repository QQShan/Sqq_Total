package com.sqq.sqq_total;

import com.sqq.sqq_total.servicedata.HeadlineItem;

import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by sqq on 2016/6/8.
 */
public interface GetAPi {
    //取headlineItem
    @GET("headlineitem.php")
    Observable<HeadlineItem> getLatestItemInfo();

    /**
     * 测试缓存用的
     * @return
     */
    /*@Headers("Cache-Control:max-age=640000")
    @GET("index.php")
    Observable<String> getPassword(@Query("id") int id);

    @Headers("Cache-Control:public,max-age=10")
    @GET("index2.php")
    Observable<String> getPassword();*/

    @POST("test.php")
    Observable<Boolean> setUrl();
}
