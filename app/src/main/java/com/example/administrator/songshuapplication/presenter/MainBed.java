package com.example.administrator.songshuapplication.presenter;

/**
 * Created by Administrator on 2017/6/20.
 */

public class MainBed {


    public interface OnLoginCallBack {
        void callback(int name,boolean b);
    }
    public static  OnLoginCallBack onLoginCallBack;
    public void setOnLoginCallBack(OnLoginCallBack onLoginCallBack) {
        this.onLoginCallBack = onLoginCallBack;
    }


}
