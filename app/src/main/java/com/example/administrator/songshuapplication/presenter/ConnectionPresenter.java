package com.example.administrator.songshuapplication.presenter;


import com.example.administrator.songshuapplication.biz.ConnectionBizImpl;
import com.example.administrator.songshuapplication.biz.IConnectionBiz;
import com.example.administrator.songshuapplication.view.ConnectionoView;

/**
 * Created by yangtong on 2017/6/7.
 */

public class ConnectionPresenter {
    ConnectionoView connview;
    IConnectionBiz conbiz;
    public ConnectionPresenter(ConnectionoView connview) {
    this.connview=connview;
        conbiz=new ConnectionBizImpl();
    }
    public void getConnection(){
       conbiz.connection(new IConnectionBiz.OnConnListener() {
           @Override
           public void onSuccess(boolean boo) {
               connview.connection(boo);
           }
           @Override
           public void onError(String e) {

           }
       });

    }

}
