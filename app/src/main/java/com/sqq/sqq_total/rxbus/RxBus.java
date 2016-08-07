package com.sqq.sqq_total.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Administrator on 2016/8/1.
 * 发送一次在多个地方可以接收
 */
public class RxBus {
    private static RxBus rxBus = null;

    private  final Subject<Object,Object> mRxBus = new SerializedSubject<Object,Object>(PublishSubject.create());

    public synchronized static RxBus getRxBus(){
        if(rxBus==null){
            rxBus=new RxBus();
        }
        return rxBus;
    }

    public void send(Object o){
        mRxBus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> type){
        return mRxBus.ofType(type);
    }

    public boolean hasObservers(){
        return mRxBus.hasObservers();
    }
}
