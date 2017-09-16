package com.example.administrator.songshuapplication.presenter;


import com.example.administrator.songshuapplication.biz.IUserBiz;
import com.example.administrator.songshuapplication.biz.UserBizImpl;
import com.example.administrator.songshuapplication.modle.User;
import com.example.administrator.songshuapplication.view.LoginView;

/**
 * Created by Administrator on 2017/4/11.
 */

public class LoginPresenter {


    private IUserBiz mUserBiz;
    private LoginView mLoginView;

    public LoginPresenter(LoginView loginView) {
        mUserBiz = new UserBizImpl();
        mLoginView = loginView;
    }

    public void login() throws InterruptedException {
        mLoginView.showProgress();
        mUserBiz.login(mLoginView.getName(), mLoginView.getPwd(), new IUserBiz.OnLoginListener() {
            @Override
            public void onSuccess(User user) {
                mLoginView.hideProgress();
                mLoginView.onSuccess(user);
            }

            @Override
            public void onError(String e) {
                mLoginView.hideProgress();
                mLoginView.onError(e);
                mLoginView.clearPwd();
            }
        });
    }
}
