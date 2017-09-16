package com.example.administrator.songshuapplication.presenter;


import com.example.administrator.songshuapplication.biz.ISleepBiz;
import com.example.administrator.songshuapplication.biz.SleepBizImpl;
import com.example.administrator.songshuapplication.modle.SleepContext;
import com.example.administrator.songshuapplication.view.ShuiMianView;

/**
 * Created by yangtong on 2017/6/6.
 */

public class ShuiMianPresenter {
    ShuiMianView smv;
    ISleepBiz iSleepBiz;
    public ShuiMianPresenter(ShuiMianView smv) {
         this.smv=smv;
        iSleepBiz=new SleepBizImpl();
    }
    public  void getElectricity(){
            iSleepBiz.getElectricity(new ISleepBiz.OnElecListener() {
           @Override
           public void onSuccess(int elec) {
             smv.getElectricity(elec);
    }
            @Override
            public void onError(String e) {

    }
    });
    }
    public  void getSleep(){
        iSleepBiz.getSleep(new ISleepBiz.OnSleepListener() {
            @Override
            public void onSuccess(SleepContext sleepContext) {
               smv.getSleep(sleepContext);
    }
            @Override
            public void onError(String e) {

    }
});

    }

}
