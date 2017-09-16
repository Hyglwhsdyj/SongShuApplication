package com.example.administrator.songshuapplication.biz;

import android.util.Log;
import android.widget.Toast;

import com.example.administrator.songshuapplication.activity.RecyclerviewActivity;
import com.example.administrator.songshuapplication.mattress.MattressModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by yangtong on 2017/7/18.
 */

public class Okhttpbizimpl implements IOkhttpbiz {
    @Override
    public void selectbiz(final MattressModel mattressModel1) {
                OkHttpUtils.post().url("http://139.199.183.57/index.php/Sleep/getinfo")
                        .addParams("heartBeat",String.valueOf(mattressModel1.getHeartBeat()))
                        .addParams("breathe",String.valueOf(mattressModel1.getBreathe()))
                        .addParams("snore",String.valueOf(mattressModel1.getSnore()))
                        .addParams("turnOver",String.valueOf(mattressModel1.getTurnOver()))
                        .addParams("isBed",String.valueOf(mattressModel1.getIsBed()))
                        .addParams("timezone",String.valueOf(mattressModel1.getTimezone()))
                        .addParams("power",String.valueOf(mattressModel1.getPower()))
                        .addParams("lace",String.valueOf(mattressModel1.getLace()))
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.e("aaaa","失败"+call.hashCode());
                    }
                    @Override
                    public void onResponse(String s, int i) {
                        Log.e("aaaa","成功"+mattressModel1.toString());
                    }
                });


    }
}
