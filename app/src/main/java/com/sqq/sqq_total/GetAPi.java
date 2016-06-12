package com.sqq.sqq_total;

import com.sqq.sqq_total.servicedata.SimpleItem;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by sqq on 2016/6/8.
 */
public interface GetAPi {
    //一次取10条
    @GET("info.php")
    Observable<SimpleItem> getLatestItemInfo();
}
