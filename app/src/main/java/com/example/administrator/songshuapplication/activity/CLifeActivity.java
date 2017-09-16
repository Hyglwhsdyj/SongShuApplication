package com.example.administrator.songshuapplication.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import com.example.administrator.songshuapplication.R;

import java.util.Iterator;
import java.util.Set;

public class CLifeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clife);
        init();
    }



    public void init(){
        Toolbar toolbar= (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onClickClife(View view){
        BluetoothSearch();
    }
    public void BluetoothSearch(){
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        //判断当前设备中是否有蓝牙设备
        Log.e("aaa","bubu");
                   if(bluetoothAdapter!=null){
                       //判断蓝牙是否打开和打开蓝牙
                       if(!bluetoothAdapter.isEnabled()){
                           Log.e("aaa","bubu");
                          Intent in=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                          startActivity(in);
                       }
                   }

        //得到所有已经配对蓝牙设备地址
        Set<BluetoothDevice> devices=bluetoothAdapter.getBondedDevices();
        if(devices.size()>0){
            for (Iterator iterator=devices.iterator();iterator.hasNext();){
                BluetoothDevice device= (BluetoothDevice) iterator.next();
                Log.e("aaa","device"+device.getAddress());
                       }
                 }
        //扫描周围的蓝牙设备
        bluetoothAdapter.startDiscovery();
        //设置蓝牙的可见性
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
       //设置蓝牙可见性的时间，方法本身规定最多可见300秒  
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(intent);

        registerBr();
//        BlutetoothReceiver bu=new BlutetoothReceiver();

    }


    private void registerBr() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        // 以下两个都是在查找蓝牙设备
        filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
        // 这个广播有可能收不到
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        Log.e("aaa","bbbbbbbbbbbb");
    }
    //android把扫描到的蓝牙设备通过广播的形式发出去，所以想接收扫描结果就必须写个广播接收器类。
//    class BlutetoothReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //从收到的intent对象中将代表远程蓝牙设配器的对象取出  
//            BluetoothDevice devices = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//            Log.e("aaa","Sousuodevice"+devices.getAddress());
//        }
//    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                // 表示找到一个蓝牙设备
                case BluetoothDevice.ACTION_NAME_CHANGED:
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String s = device.getName() + "#" + device.getAddress();
                    Log.e("aaa", "onReceive: " + s);
                    // 修改ListView的数据源
//                    mDevices.add(s);
//                    // 通知ListView更新
//                    mAdapter.notifyDataSetChanged();
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    setTitle("开始查找....");
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    setTitle("查找完成");
                    break;
            }
        }
    };

}
