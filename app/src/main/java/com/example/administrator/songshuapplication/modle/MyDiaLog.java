package com.example.administrator.songshuapplication.modle;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.administrator.songshuapplication.R;

/**
 * Created by yangtong on 2017/6/26.
 */

public class MyDiaLog extends Dialog {
    public MyDiaLog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog);
        setTitle("提示信息");
    }
}
