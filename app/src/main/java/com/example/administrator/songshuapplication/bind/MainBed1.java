package com.example.administrator.songshuapplication.bind;

import com.example.administrator.songshuapplication.presenter.MainBed;

/**
 * Created by Administrator on 2017/6/20.
 */

public class MainBed1 {
    public interface OnLoginCallBack1 {
        void callback1(int name,boolean b);
    }
    public static OnLoginCallBack1 onLoginCallBack1;
    public void setOnLoginCallBack1(OnLoginCallBack1 onLoginCallBack) {
        this.onLoginCallBack1 = onLoginCallBack;
    }
}
