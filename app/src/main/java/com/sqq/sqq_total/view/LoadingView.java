package com.sqq.sqq_total.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.sqq.sqq_total.R;


/**
 * Created by sqq on 2016/6/18.
 */
public class LoadingView {
    public interface LoadExit{
        public void exit();
    }
    private Context context;
    AlertDialog dialog;
    LoadExit le;
    public LoadingView(Context con){
        context = con;
    }

    public LoadingView showDialog(String mess){
        dialog = new AlertDialog.Builder(context).create();
        Window mWindow = dialog.getWindow();
        mWindow.setGravity(Gravity.CENTER);
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        Window window = dialog.getWindow();
        window.setContentView(R.layout.doinb_dialog);
        TextView v = (TextView) window.findViewById(R.id.d_tip);
        v.setText(mess);
        TextView tv = (TextView) window.findViewById(R.id.d_title);
        tv.setText("结束");
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sqqq","点击了");
                dialog.dismiss();
                if(le!=null){
                    le.exit();
                }
            }
        });
        return this;
    }

    public void dismissDialog(){
        if(dialog.isShowing())
            dialog.dismiss();
    }

    public void setLoadExitListener(LoadExit le){
        this.le = le;
    }

}

