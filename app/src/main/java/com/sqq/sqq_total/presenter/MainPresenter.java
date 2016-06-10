package com.sqq.sqq_total.presenter;

/**
 * Created by sqq on 2016/5/28.
 */
public class MainPresenter {
    public interface MainView{
        public void setText(String text);
    }

    private MainView mView;

    public MainPresenter(MainView view){
        mView = view;
    }

    public void onClick(){
        mView.setText("不知道什么");
    }
}
