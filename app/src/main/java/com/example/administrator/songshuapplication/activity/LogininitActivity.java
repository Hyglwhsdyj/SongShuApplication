package com.example.administrator.songshuapplication.activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.songshuapplication.MainActivity;
import com.example.administrator.songshuapplication.R;
import com.het.open.lib.api.HetAuthApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.callback.AuthCallback;

public class LogininitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logininit);
        if (!HetSdk.getInstance().isAuthLogin()) {
            HetAuthApi.getInstance().authorize(new AuthCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    Intent intent=new Intent(LogininitActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                @Override
                public void onFailed(int code, String msg) {
                }
            });
        }else {
            Intent intent=new Intent(LogininitActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(1);
    }
}
