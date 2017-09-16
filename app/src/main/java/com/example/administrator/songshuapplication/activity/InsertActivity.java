package com.example.administrator.songshuapplication.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.songshuapplication.R;
import com.example.administrator.songshuapplication.bind.Main2Activity;
import com.example.administrator.songshuapplication.modle.data;
import com.example.administrator.songshuapplication.presenter.MianPresenter;
import com.example.administrator.songshuapplication.view.MianView;
import com.necer.ndialog.NDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class InsertActivity extends AppCompatActivity  implements MianView {
@Bind({R.id.user_name,R.id.user_institutions,R.id.user_gender,R.id.user_theroom,R.id.user_theroomnumber,R.id.user_devicenumber})
List<TextView> textViewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        ButterKnife.bind(this);
        if(RecyclerviewActivity.Lace!=null) {
            MianPresenter mianPresenter=new MianPresenter(this);
            mianPresenter.show();
        }else {
            dialog1();
        }
    }
    @Override
    public String getLace() {
        return RecyclerviewActivity.Lace;
     }
    protected void dialog1() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.dialogstyle);
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setIcon(R.mipmap.timg_4);
        builder.setMessage("设备未连接，请先连接设备！！");
        builder.setCancelable(false);
        builder.show();
    }
    @Override
    public void show(data da) {
        textViewList.get(0).setText(da.getName());
        textViewList.get(1).setText(da.getAccount());
        textViewList.get(2).setText(da.getSex());
        textViewList.get(3).setText(da.getRoom());
        textViewList.get(4).setText(da.getBad());
        textViewList.get(5).setText(da.getLace());
    }
}
