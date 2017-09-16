package com.example.administrator.songshuapplication.api;




import com.example.administrator.songshuapplication.modle.ResultSet;
import com.example.administrator.songshuapplication.modle.data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface ApiServicesMian {

    String BASE_API = "http://192.168.31.213/index.php/";
    /**
     * POST 表示请求方式
     * <p>
     * FormUrlEncoded 表示 将URL 进行 POST 编码
     * <p>
     * Field 服务器需要获取的字段信息
     *
     * @param lace
     * @param
     * @return
     */
    @POST("Callback/getLaceInfo")
    @FormUrlEncoded
    Call<ResultSet<data>> login(@Field("lace") String lace);
}
