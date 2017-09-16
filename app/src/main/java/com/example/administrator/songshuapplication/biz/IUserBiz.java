package com.example.administrator.songshuapplication.biz;


import com.example.administrator.songshuapplication.modle.User;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface IUserBiz {

    void login(String name, String pwd, OnLoginListener onLoginListener) throws InterruptedException;


    interface OnLoginListener {

        void onSuccess(User user);

        void onError(String e);


    }

}
