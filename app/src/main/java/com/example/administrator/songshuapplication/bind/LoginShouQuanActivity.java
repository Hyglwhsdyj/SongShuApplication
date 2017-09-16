package com.example.administrator.songshuapplication.bind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.songshuapplication.R;
import com.het.open.lib.api.HetAuthApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.callback.AuthCallback;


public class LoginShouQuanActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginsq);
        mBtnAuth = (Button)findViewById(R.id.btn_auth);
        mBtnAuth.setOnClickListener(this);
    }

    private void auth(){

        if (!HetSdk.getInstance().isAuthLogin()) {
            HetAuthApi.getInstance().authorize(new AuthCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    startActivity(new Intent(LoginShouQuanActivity.this, Main2Activity.class));
                    Toast.makeText(LoginShouQuanActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailed(int code, String msg) {
                    Toast.makeText(LoginShouQuanActivity.this, "" + code + msg, Toast.LENGTH_SHORT).show();


                }
            });}else {

            startActivity(new Intent(LoginShouQuanActivity.this, Main2Activity.class));

        }

    }
    @Override
    public void onClick(View v) {
        auth();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
