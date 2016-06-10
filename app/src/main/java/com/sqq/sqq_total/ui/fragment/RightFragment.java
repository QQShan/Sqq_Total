package com.sqq.sqq_total.ui.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.utils.FileLoader;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class RightFragment extends BaseFragment {

    @Override
    protected void ifNotNUll(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_right, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.files);
        String path = Environment.getExternalStorageDirectory().getPath();
        String[] suffix={"txt"};
        List ret = FileLoader.GetFileWithSuffix(path + File.separator/*+"360"*/, suffix);
        String xx="";
        for(int  i=0;i<ret.size();i++){
            xx+=ret.get(i);
            Log.d("xxx",ret.get(i)+"");
        }
        tv.setText(xx);
    }
}
