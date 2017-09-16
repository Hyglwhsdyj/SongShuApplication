package com.example.administrator.songshuapplication.presenter;

import com.example.administrator.songshuapplication.biz.IOkhttpbiz;
import com.example.administrator.songshuapplication.biz.Okhttpbizimpl;
import com.example.administrator.songshuapplication.mattress.MattressModel;

/**
 * Created by yangtong on 2017/7/18.
 */

public class OkhttpPresenter {
    IOkhttpbiz okhttpbiz;
    public OkhttpPresenter() {
    okhttpbiz= new Okhttpbizimpl();
    }
    public void select(MattressModel mattressModel1){
        okhttpbiz.selectbiz(mattressModel1);
    }
}
