package com.example.administrator.songshuapplication.view;


import com.example.administrator.songshuapplication.modle.User;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface LoginView {

    void onError(String e);

    void onSuccess(User user) ;

    void showProgress();

    void hideProgress();

    String getName();

    String getPwd();

    void clearPwd();

}
