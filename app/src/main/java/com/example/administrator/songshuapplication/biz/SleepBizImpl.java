package com.example.administrator.songshuapplication.biz;



/**
 * Created by yangtong on 2017/6/6.
 */

public class SleepBizImpl implements ISleepBiz {


    @Override
    public void getElectricity(OnElecListener onElecListener) {
        onElecListener.onSuccess(1);
    }



    @Override
    public void getSleep(OnSleepListener onSleepListener) {
        onSleepListener.onSuccess(null);
    }
}
