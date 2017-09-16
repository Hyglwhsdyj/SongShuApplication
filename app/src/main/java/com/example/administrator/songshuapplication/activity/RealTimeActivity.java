package com.example.administrator.songshuapplication.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.songshuapplication.R;
import com.example.administrator.songshuapplication.modle.ChartView;
import com.example.administrator.songshuapplication.utils.Constants;
import com.example.administrator.songshuapplication.utils.HandlerUtil;
import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetDeviceListApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.GsonUtil;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class RealTimeActivity extends Activity {

    private View rootView;
    private TextView tvwTip;
    private List<DeviceModel> deviceModels = new ArrayList<>();
    private final int INIT_DATA = 0x01;
    private final int UPDATE_ADAPTER = 0x02;
    private AdapterDeviceList mAdapter;
    private ListView mListView;
    private boolean firstFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time);
        initView();
    }

    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_DATA:
                    initData();
                    break;
                case UPDATE_ADAPTER:
                    if (mListView!=null){
                        mListView.setVisibility(View.VISIBLE);
                    }
                    if (tvwTip!=null){
                        tvwTip.setVisibility(View.GONE);
                    }
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }

        }
    };
    private HandlerUtil.StaticHandler mStableHandler = new HandlerUtil.StaticHandler(
            mMessageListener);



    private void initData() {
        if (!HetSdk.getInstance().isAuthLogin()) {
            showTipText("授权登录后查看绑定设备信息");
            return;
        }
        HetDeviceListApi.getInstance().getBindList(new IHetCallback() {
            @Override
            public void onSuccess(int code,String s) {
                if (code==0){
                    Type type = new TypeToken<List<DeviceModel>>() {
                    }.getType();
                    List<DeviceModel> models = GsonUtil.getGsonInstance().fromJson(s,type);
                    if (models != null && models.size() > 0) {
                        deviceModels.clear();
                        deviceModels.addAll(models);
                        sendMsg(UPDATE_ADAPTER);
                    }else {
                        showTipText("未绑定任何设备");
                    }
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                Toast.makeText(RealTimeActivity.this, "msg"+msg, Toast.LENGTH_SHORT).show();
                showTipText(msg);
//                showToast(msg);
            }
        });
    }
    private void showTipText(String text){
        if (mListView!=null){
            mListView.setVisibility(View.GONE);
        }
        tvwTip.setText(text);
        tvwTip.setVisibility(View.VISIBLE);
    }
    private void initView() {
        mListView = (ListView) findViewById(R.id.device_list);
        tvwTip=(TextView)findViewById(R.id.tvw_tip);
        tvwTip.setVisibility(View.GONE);
        mAdapter = new AdapterDeviceList(deviceModels,this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceModel deviceModel = deviceModels.get(position);
                int type=Integer.parseInt(deviceModel.getDeviceTypeId());
                Intent intent = null;
                if (type==28){
                    //智慧盒子
//                    intent = new Intent(getActivity(), SleepBoxActivity.class);
                }else if(type==6){
                    //睡眠带子
//                    intent = new Intent(this, MattressActivity.class);
                    intent = new Intent(RealTimeActivity.this, RecyclerviewActivity.class);
                }else {
                    //其它设备
//                    intent = new Intent(getActivity(), DeviceManagerActivity.class);

                }
                intent.putExtra(Constants.DEVICE_MODEL,deviceModel);
                startActivity(intent);
            }
        });
        if (!firstFlag){
            sendMsg(INIT_DATA);
            firstFlag=true;
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        sendMsg(INIT_DATA);
    }
    private void sendMsg(int value) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value).sendToTarget();
        }

    }
}
