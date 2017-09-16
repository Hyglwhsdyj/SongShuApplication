package com.example.administrator.songshuapplication.biz;

/**
 * Created by yangtong on 2017/6/7.
 */

public interface IConnectionBiz {
    void connection(OnConnListener connListener);
    interface OnConnListener {

        void onSuccess(boolean boo);

        void onError(String e);

    }

}
