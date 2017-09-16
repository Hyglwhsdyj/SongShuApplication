package com.example.administrator.songshuapplication.biz;
import android.util.Log;
import com.example.administrator.songshuapplication.modle.ResultSet;
import com.example.administrator.songshuapplication.modle.data;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.lang.reflect.Type;
/**
 * Created by yangtong on 2017/6/7.
 */

public class MianBizImpl implements IMianBiz {
//    private final ApiServicesMian services;

    public MianBizImpl() {
////         构建 Retrofit 对象
//        Retrofit retrofit = new Retrofit.Builder()
//                // 添加url
//                .baseUrl(ApiServicesMian.BASE_API)
//                // 添加json 转换器
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        // 创建服务请求对象
//        services = retrofit.create(ApiServicesMian.class);
    }

    @Override
    public void Show(String name, final OnMainListener onMainListener) {
        Log.e("name","name"+name);
        OkHttpUtils.post().url("http://139.199.183.57/index.php/Callback/getLaceInfo")
                .addParams("lace",name)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int i) {
            }
            @Override
            public void onResponse(String s, int i) {
                Type type = new TypeToken<ResultSet<data>>(){}.getType();
                Log.e("eeee","User"+s);
                ResultSet<data> users = new Gson().fromJson(s,type);
                Log.e("eeee","User"+users.code);
                onMainListener.onSuccess(users.data);
            }
        });

//        Call<ResultSet<data>> call = services.login(name);
//        Log.e("eeee","参数"+name);
//call.enqueue(new Callback<ResultSet<data>>() {
//    @Override
//    public void onResponse(Call<ResultSet<data>> call, Response<ResultSet<data>> response) {
//        Log.e("eeee","User成功");
//        Log.e("eeee","User"+response.body().code);
//
//        data da=response.body().data;
//        onMainListener.onSuccess(da);
//    }
//    @Override
//    public void onFailure(Call<ResultSet<data>> call, Throwable throwable) {
//        Log.e("eeee","User失败"+throwable);
//    }
//});


    }
}