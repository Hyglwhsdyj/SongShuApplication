package com.example.administrator.songshuapplication.biz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yangtong on 2017/6/2.
 */

public class Util {
        public static String TAG="UTIL";
        public static Bitmap getbitmap(String imageUri) {
            Log.v(TAG, "getbitmap:" + imageUri);
            // 显示网络上的图片
            Bitmap bitmap = null;
            try{
                URL myFileUrl = new URL(imageUri);
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
                Log.v(TAG, "image download finished." + imageUri);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.v(TAG, "getbitmap bmp fail---");
                return null;

            }

            return bitmap;

        }

    }

