package com.sqq.sqq_total.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.sqq.sqq_total.App;
import com.sqq.sqq_total.R;
import com.sqq.sqq_total.utils.CacheUtils;
import com.sqq.sqq_total.utils.PreferenceUtils;
import com.sqq.sqq_total.utils.TimerUtils;

import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sqq on 2016/7/13.
 */
public class UploadPicPresenter {

    public interface  UploadPicView{
        public void uploadsuccess();
        public void uploadfail();
        public void intentToForResult(Intent intent ,int request_code);
        public void toClip(Intent intent);
    }

    UploadPicView upView;
    Uri uri;
    String picName;
    String picPath;
    public static final int CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int CAPTURE_PHOTOS_REQUEST_CODE = 150;

    public UploadPicPresenter(UploadPicView upView){
        this.upView = upView;
        picName = "";
        picPath = "";
    }

    public void takePhoto(Context con){

        /**
         * 这里选择jpg，他是有损的（png无损）所以占的内存更小一些
         * ，其实更好的做法是用webp
         */
        picName = TimerUtils.getTimeStampLong()+".jpg";
        picPath = CacheUtils.getAndroidDataPath(con)+File.separator+picName;
        File file = new File(picPath);
        if(file.exists()){
            file.delete();
        }
        uri = Uri.fromFile(file);

        // 利用系统自带的相机应用拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        /**
         * 检查有没有能处理这个intent的activity
         */
        if(intent.resolveActivity(con.getPackageManager())!=null){
            upView.intentToForResult(intent,CAPTURE_IMAGE_REQUEST_CODE);
        }
    }

    public void handlerReturn(int requestCode, int resultCode, Intent data){
        // 如果是拍照
        if (UploadPicPresenter.CAPTURE_IMAGE_REQUEST_CODE == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                //因为上面传入了文件路径 data返回参数是null
                if (data != null) {
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("uri", picPath);
                upView.toClip(intent);
            }
        }
    }

    public Subscription uploadPic(Context con,String title){
        /*File file = new File(CacheUtils.getAndroidDataPath(con)+"/1.jpg");
        Log.d("sqqqqq",CacheUtils.getAndroidDataPath(con)+"/1.jpg");*/
        File file = new File(picPath);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part pic = MultipartBody.Part.createFormData("uploadfile", picName, photoRequestBody);

        String userId = ""+PreferenceUtils.getLong(con, R.string.prefer_userId,-1L);

        Subscription s = App.getRetrofitInstance().getApiService()
                .uploadPic(pic, RequestBody.create(null, title)
                        , RequestBody.create(null, userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("sqqq","上传失败"+throwable.toString());
                        upView.uploadfail();
                    }

                    @Override
                    public void onNext(Void response) {
                        upView.uploadsuccess();
                    }
                });
        return s;
    }
}
