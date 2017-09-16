package com.example.administrator.songshuapplication.api;




import com.example.administrator.songshuapplication.modle.Result;
import com.example.administrator.songshuapplication.modle.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface ApiServices {


    String BASE_API = "http://192.168.3.100:8080/life/";


    /**
     * POST 表示请求方式
     * <p>
     * FormUrlEncoded 表示 将URL 进行 POST 编码
     * <p>
     * Field 服务器需要获取的字段信息
     *
     * @param name
     * @param pwd
     * @return
     */
    @POST("api/login")
    @FormUrlEncoded
    Call<Result<User>> login(@Field("name") String name, @Field("pwd") String pwd);

}
