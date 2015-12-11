package com.itspeed.naidou.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import org.kymjs.kjframe.utils.KJLoger;

/**
 * Created by jafir on 15/12/8.
 */
public class MyService extends Service {

    private MyBinder binder = new MyBinder();
    private class  MyBinder extends Binder{
        public  void method(){
            KJLoger.debug("binder _method");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        KJLoger.debug("onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KJLoger.debug("onCreate");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KJLoger.debug("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        KJLoger.debug("onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        KJLoger.debug("onUnbind");
        return super.onUnbind(intent);
    }
}
