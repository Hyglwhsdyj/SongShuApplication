package com.example.administrator.songshuapplication.biz;


import com.example.administrator.songshuapplication.modle.Detection;
import com.example.administrator.songshuapplication.modle.data;

/**
 * Created by yangtong on 2017/6/7.
 */

public interface IMianBiz {

    void Show(String name,OnMainListener onMainListener);
    interface OnMainListener {
        void onSuccess(data tion);
        void onError(String e);
    }


}
