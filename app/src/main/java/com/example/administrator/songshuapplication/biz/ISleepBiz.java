package com.example.administrator.songshuapplication.biz;


import com.example.administrator.songshuapplication.modle.SleepContext;

/**
 * Created by yangtong on 2017/6/6.
 */

public interface ISleepBiz {
    void getElectricity(OnElecListener onElecListener);

    void getSleep(OnSleepListener onSleepListener);

    interface OnSleepListener {

        void onSuccess(SleepContext sleepContext);

        void onError(String e);
    }
    interface OnElecListener {

        void onSuccess(int elec);

        void onError(String e);


    }


}
