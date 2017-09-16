package com.example.administrator.songshuapplication.presenter;


import com.example.administrator.songshuapplication.biz.IMianBiz;
import com.example.administrator.songshuapplication.biz.MianBizImpl;
import com.example.administrator.songshuapplication.modle.Detection;
import com.example.administrator.songshuapplication.modle.data;
import com.example.administrator.songshuapplication.view.MianView;

/**
 * Created by yangtong on 2017/6/7.
 */

public class MianPresenter {
    MianView mianView;
    IMianBiz iMianBiz;
    public MianPresenter(MianView mianView) {
    this.mianView=mianView;
        iMianBiz= new MianBizImpl();
    }
    public void show(){
        iMianBiz.Show(mianView.getLace(),new IMianBiz.OnMainListener() {
            @Override
            public void onSuccess(data tion) {
                mianView.show(tion);
            }
            @Override
            public void onError(String e) {
            }
        });


    }



}
