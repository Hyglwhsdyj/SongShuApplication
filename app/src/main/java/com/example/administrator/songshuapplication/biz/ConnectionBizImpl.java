package com.example.administrator.songshuapplication.biz;

import com.example.administrator.songshuapplication.bind.Main2Activity;

/**
 * Created by yangtong on 2017/6/7.
 */

public class ConnectionBizImpl implements IConnectionBiz {
    @Override
    public void connection(OnConnListener connListener) {
       connListener.onSuccess(false);
    }
}
