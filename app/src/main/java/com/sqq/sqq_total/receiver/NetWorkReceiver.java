package com.sqq.sqq_total.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sqq.sqq_total.utils.NetWorkUtil;

/**
 * Created by Administrator on 2016/6/13.
 */
public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NetWorkUtil.getInstance().setConnected(context);
    }
}
