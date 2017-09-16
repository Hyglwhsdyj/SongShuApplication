package com.example.administrator.songshuapplication.bind;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by yangtong on 2017/6/9.
 */

public class BaseActivity extends AppCompatActivity {
    private Context mContext;
    private CommonLoadingDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示提示信息
     *
     * @param msg
     */
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showDialog(String text) {
        if (mDialog == null) {
            mDialog = new CommonLoadingDialog(this);
            mDialog.setText(text);
            mDialog.show();
        }
    }


    public void hideDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
