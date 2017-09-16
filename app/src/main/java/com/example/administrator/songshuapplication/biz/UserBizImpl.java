package com.example.administrator.songshuapplication.biz;


import com.example.administrator.songshuapplication.modle.User;

/**
 * Created by Administrator on 2017/4/11.
 */

public class UserBizImpl implements IUserBiz {


//    private final ApiServices services;

    public UserBizImpl() {
//        // 构建 Retrofit 对象
//        Retrofit retrofit = new Retrofit.Builder()
//                // 添加url
//                .baseUrl(ApiServices.BASE_API)
//                // 添加json 转换器
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        // 创建服务请求对象
//        services = retrofit.create(ApiServices.class);
    }

    @Override
    public void login(String name, String pwd, final OnLoginListener onLoginListener)  {
        // 执行登录功能
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (name.equals("aaa")&&pwd.equals("123")){
            User user=new User("aaa","123");
            onLoginListener.onSuccess(user);
        }else if(!name.equals("aaa")&&!pwd.equals("123")) {
            onLoginListener.onSuccess(null);
        }else {
            onLoginListener.onError(String.valueOf(new Throwable()));
        }
//        Call<Result<User>>  call = services.login(name, pwd);
//        call.enqueue(new Callback<Result<User>>() {
//            @Override
//            public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
//                User user = response.body().data;
//                onLoginListener.onSuccess(user);
//            }
//            @Override
//            public void onFailure(Call<Result<User>> call, Throwable throwable) {
//                onLoginListener.onError(throwable.getMessage());
//            }
//        });
    }
}
