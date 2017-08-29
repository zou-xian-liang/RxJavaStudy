package com.ds.rxandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, Thread.currentThread().getName());
        testRx();
    }

    private void testRx() {

        //创建一个上游 Observable：被观察者
        //emitter：用来发出事件的，onNext,onComplete,onError
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }


        });


        //创建一个下游 Observer：观察者
        //Disposable:用来阶段接收者，不在接收事件，如调用了该方法，则事件还是会继续发送，但是观察者不再接收
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "complete");
            }
        };

        Consumer consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        };
        //建立连接，只有调用了这个接口，被订阅者和订阅者才能开始发送和接收消息
        observable.subscribe(observer);

        observable
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);
    }
}
