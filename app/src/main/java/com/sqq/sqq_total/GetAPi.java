package com.sqq.sqq_total;

import com.sqq.sqq_total.servicedata.HeadlineItem;
import com.sqq.sqq_total.servicedata.PicItem;
import com.sqq.sqq_total.servicedata.SlideviewItem;
import com.sqq.sqq_total.servicedata.TextItem;
import com.sqq.sqq_total.servicedata.VideoItem;

import java.sql.Timestamp;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sqq on 2016/6/8.
 * 不指定Cache-Control也缓存了，但是过期时间很短
 */
public interface GetAPi {

    ///////////////////headlineitem///////////////////
    /**
     * 加载最新的count条数据，用于下拉刷新和一开始加载的时候
     *
     * @param count
     * @return
     */
    @Headers("Cache-Control:max-age=640000")
    @GET("headlineitem.php")
    Observable<List<HeadlineItem>> getLatestItemInfo(@Query("count") int count);

    /**
     * 取小于这个id的count条数据，用于加载更多
     * @param count
     * @param id
     * @return
     */
    @GET("headlineitem.php")
    Observable<List<HeadlineItem>> getItemInfo(@Query("count") int count,@Query("id") int id);

    /**
     * 加载最新的count条数据，用于下拉刷新和一开始加载的时候,默认是4
     * @param count
     * @return
     */
    @Headers("Cache-Control:max-age=640000")
    @GET("slideviewitem.php")
    Observable<List<SlideviewItem>> getLatestSlideviewInfo(@Query("count") int count);

    /**
     * 取小于这个id的count条数据，用于加载更多
     * @param count
     * @param id
     * @return
     */
    @GET("slideviewitem.php")
    Observable<List<SlideviewItem>> getSlideviewInfo(@Query("count") int count,@Query("id") int id);

///////////////////textitem///////////////////
    /**
     * 加载最新的count条数据，用于下拉刷新和一开始加载的时候,默认是4
     * @param count
     * @return
     */
    @Headers("Cache-Control:max-age=640000")
    @GET("textitem.php")
    Observable<List<TextItem>> getLatestTextItemInfo(@Query("count") int count);

    /**
     * 取小于这个id的count条数据，用于加载更多
     * @param count
     * @param id
     * @return
     */
    @GET("textitem.php")
    Observable<List<TextItem>> getTextItemInfo(@Query("count") int count,@Query("id") int id);

    ///////////////////picitem///////////////////
    /**
     * 加载最新的count条数据，用于下拉刷新和一开始加载的时候,默认是4
     * @param count
     * @return
     */
    @Headers("Cache-Control:max-age=640000")
    @GET("picitem.php")
    Observable<List<PicItem>> getLatestPicItemInfo(@Query("count") int count);

    /**
     * 取小于这个id的count条数据，用于加载更多
     * @param count
     * @param id
     * @return
     */
    @GET("picitem.php")
    Observable<List<PicItem>> getPicItemInfo(@Query("count") int count,@Query("id") int id);

    ///////////////////videoitem///////////////////
    /**
     * 加载最新的count条数据，用于下拉刷新和一开始加载的时候,默认是4
     * @param count
     * @return
     */
    @Headers("Cache-Control:max-age=640000")
    @GET("videoitem.php")
    Observable<List<VideoItem>> getLatestVideoItemInfo(@Query("count") int count);

    /**
     * 取小于这个id的count条数据，用于加载更多
     * @param count
     * @param id
     * @return
     */
    @GET("videoitem.php")
    Observable<List<VideoItem>> getVideoItemInfo(@Query("count") int count,@Query("id") int id);



    /**
     * 测试缓存用的
     * @return
     */
    /*@Headers("Cache-Control:max-age=640000")
    @GET("index.php")
    Observable<String> getPassword(@Query("id") int id);

    @Headers("Cache-Control:public,max-age=10")
    @GET("index2.php")
    Observable<String> getPassword();

    @FormUrlEncoded
    @POST("test.php")
    Observable<Boolean> setUrl(@Field("url") String url);
    @FormUrlEncoded
    @POST("test.php")
    Observable<Boolean> setDate(@Field("date") long date);*/
}
