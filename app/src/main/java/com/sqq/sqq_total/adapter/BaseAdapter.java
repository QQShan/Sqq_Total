package com.sqq.sqq_total.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.viewholder.BaseViewHolder;

/**
 * Created by sqq on 2016/5/28.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public static int TYPE_VIEWPAGER = 119;

    OnItemClickListener onItemClickListener;
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_VIEWPAGER){
            return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(getViewpagerLayoutID(), parent, false));
        }
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutID(), parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        if (onItemClickListener!=null) {
            holder.getmConvertView().setClickable(true);
            holder.getmConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        onBindView(holder, position);

    }

    protected abstract void onBindView(BaseViewHolder holder, int position);

    protected abstract int getLayoutID();

    public int getViewpagerLayoutID(){
        return -1;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
