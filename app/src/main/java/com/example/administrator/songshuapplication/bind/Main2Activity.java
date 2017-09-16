package com.example.administrator.songshuapplication.bind;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.songshuapplication.MainActivity;
import com.example.administrator.songshuapplication.R;
import com.example.administrator.songshuapplication.adapter.AdapterBleDeviceList;
import com.example.administrator.songshuapplication.biz.IRealTimeData;
import com.example.administrator.songshuapplication.biz.RealTimeDataimpl;
import com.example.administrator.songshuapplication.mattress.MattressModel;
import com.example.administrator.songshuapplication.mattress.SleepDataModel;
import com.example.administrator.songshuapplication.modle.Detection;
import com.example.administrator.songshuapplication.modle.Mattress;
import com.example.administrator.songshuapplication.presenter.MainBed;
import com.example.administrator.songshuapplication.utils.Constants;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.reflect.TypeToken;
import com.het.open.lib.api.HetAuthApi;
import com.het.open.lib.api.HetBleBindApi;
import com.het.open.lib.api.HetDeviceManagerApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.api.HetSleepBleControlApi;
import com.het.open.lib.api.HetSleepLaceReportApi;
import com.het.open.lib.callback.AuthCallback;
import com.het.open.lib.callback.IBleBind;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.open.lib.utils.HandlerUtil;
import com.het.open.lib.utils.LogUtils;
import com.het.open.lib.utils.StringUtils;
import com.het.open.lib.utils.TimeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
public class Main2Activity  extends BaseActivity implements
        View.OnClickListener, MainBed.OnLoginCallBack, MainBed1.OnLoginCallBack1 {
    private static final String TAG = "蓝牙扫描设备界面";
    private static DeviceModel deviceModel1;
    private ImageView bind_circle_iv;
    private Animation animation;
    private RelativeLayout search_rl;
    private TextView point;
    private TextView tvwTip;
    private final int UPDATE_ADAPTER= 0x01;
    private final int CONNECT_DEVICE=0x02;
    private final int STOP_ANIM=0x03;
    private List<DeviceModel> deviceModels=new ArrayList<>();
    private RelativeLayout relStopBind;
    private ShimmerFrameLayout container;
    private DeviceModel deviceModel;
    private ListView listViewDevice;
    private AdapterBleDeviceList adapterDeviceList;
    private Context mContext;
    private String macAddress;
    private boolean ifclick;
    private final int SHOW_REAL_DATA = 0x01;
    private int  id1;

    public static final String action1 = "jason.broadcast.action.app";

    public static Mattress mattress=null;
    public static  String Lace;
    private HandlerUtil.MessageListener mMessageListener1 = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_ADAPTER:
                    if (search_rl!=null){
                        search_rl.setVisibility(View.GONE);
                    }
                    listViewDevice.setVisibility(View.VISIBLE);
                    if (adapterDeviceList != null) {
                        Toast.makeText(mContext, "更新+Adapter", Toast.LENGTH_SHORT).show();
                        adapterDeviceList.notifyDataSetChanged();
                    }
                    break;
                case CONNECT_DEVICE:
                    break;
                case STOP_ANIM:
                    stopAnim();
                    break;
                default:
                    break;
            }
        }
    };
    private HandlerUtil.StaticHandler mStableHandler1 = new HandlerUtil.StaticHandler(mMessageListener1);
    private BluetoothAdapter mBluetoothAdapter;
    public static Detection detection=new Detection();
    private TextView textClick;
    private static  boolean th=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main2);
        this.mContext=this;
        MainBed mainBed=new MainBed();
        mainBed.setOnLoginCallBack(this);
        MainBed1 mainBed1=new MainBed1();
        mainBed1.setOnLoginCallBack1(this);
        bluetooth();
    }


    public void bluetooth(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "本机没有找到蓝牙硬件或驱动！", Toast.LENGTH_SHORT).show();
            finish();
        }
        // 判读；蓝牙是否开启
        if (!mBluetoothAdapter.isEnabled()) {
            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(mIntent, 1);

        }

        initView();
        initData();
    }
    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_REAL_DATA:
                    byte[] datas = (byte[]) msg.obj;
                   dealRealData(datas);
                    break;
                default:
                    break;
            }
        }
    };
    private HandlerUtil.StaticHandler mStableHandler = new HandlerUtil.StaticHandler(
            mMessageListener);
    private void initData() {
        deviceModel= (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        Log.e("aaa","deviceModel"+deviceModel);
//        getSupportActionBar().setTitle(deviceModel.getDeviceName()+"绑定");
        int ret= HetBleBindApi.getInstance().init();
        if (ret==0){
            //设备型号标识 deviceModel.getProductId()
            HetBleBindApi.getInstance().startBind(2378+"",new IBleBind() {
                @Override
                public void onScanDevices(String devices, String msg) {
                    LogUtils.d(TAG,devices);
                    Type type = new TypeToken<List<DeviceModel>>() {
                    }.getType();
                    List<DeviceModel> models = GsonUtil.getGsonInstance().fromJson(devices, type);
                    if (models != null && models.size() > 0) {
                        deviceModels.clear();
                        deviceModels.addAll(models);
                        sendMsg(UPDATE_ADAPTER);
                    } else {
                        showToast("未绑定任何设备");
                    }
                }
                @Override
                public void onFailed(int errId, String msg) {
                    LogUtils.e(TAG,errId+msg);
                    Log.e("aaa","msg"+msg+"errId"+errId);
                    showStatus(errId+msg);
                    sendMsg(STOP_ANIM);
                    ifclick=false;
                }
                @Override
                public void onSuccess(final DeviceModel deviceModel) {
                    String msg="成功绑定设备["+deviceModel.getMacAddress()+"]";
                    Log.e("aaa",msg);
                    showStatus(msg);
                    sendMsg(STOP_ANIM);
                    textClick.setText("确定");
                    mattress=new Mattress();
                    mattress.setOnoff(true);
                    mattress.setHeartBeat(Byte.valueOf("60"));
                    mattress.setBreathe(Byte.valueOf("20"));
                    mattress.setTurnOver(Byte.valueOf("1"));
                    Lace=deviceModel.getMacAddress();
                    macAddress=deviceModel.getMacAddress();
                    deviceModel1 = deviceModel;
                    th = true;
//                    mThread(2,th);
                }
            });
        }else if (ret==1){
            sendMsg(STOP_ANIM);
            showToast("请打开蓝牙");
        }else if (ret==2){
            sendMsg(STOP_ANIM);
            showToast("该手机不支持ble蓝牙功能");
        }
    }
    @Override
    public void callback1(final int name, final boolean b) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                if (deviceModel1!=null) {
                    Log.e("aaa",deviceModel1.getMacAddress()+"");
                    HetDeviceManagerApi.getInstance().unBind(deviceModel1, new IHetCallback() {
                        @Override
                        public void onSuccess(int code,String msg){
                            Log.e("aaa","成功解除绑定");
                            Intent tent=new Intent(action1);
                            tent.putExtra("bool",false);
                            sendBroadcast(tent);
                            Lace=null;
                            mThread(name,b);
                        }
                        @Override
                        public void onFailed(int code,String msg){
                            Log.e("aaa","解除绑定失败");
                        }
                    });
                }else {
                    Log.e("aaa","你还未绑定设备");
                }
            }
        }.start();
    }
    @Override
    public void callback(int name, boolean b) {
        if(deviceModel1!=null) {
            mThread(name, b);
        }else {
            Toast.makeText(mContext,"还未绑定设备", Toast.LENGTH_SHORT).show();
        }
    }
    public void mThread(int id,  boolean th1){
        Log.e("idid","idi"+id);
        id1=id;
        th=th1;
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (th) {
                    try {
                        Intent tent=new Intent(action1);
                        tent.putExtra("bool",true);
                        sendBroadcast(tent);
                        Thread.sleep(5000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    //  timeData.realTime(deviceModel);
                    ifclick = true;
                    if (th) {
                        RealTimeData(deviceModel1);
                    }
                    if (id1 == 1) {
                        Log.e("aaa","id"+id1);
                        if (th == false){
                            mattress = null;
//                            Intent intent = new Intent(action);
//                            intent.putExtra("data",(byte[]) null);
//                            sendBroadcast(intent);
                        }
                    }
                }
                if (id1 == 2) {
                    Log.e("aaa","id"+id1);
                    if (th == false) {
                        mattress = null;
                        deviceModel1 = null;
//                        Intent intent = new Intent(action);
//                        intent.putExtra("data", (byte[]) null);
//                        sendBroadcast(intent);
                        mBluetoothAdapter.disable();
                    }
                }
            }
        }.start();
    }
    //获取实时数据
    private void RealTimeData(DeviceModel deviceModel){

        HetSleepBleControlApi.getInstance().getRealData(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg){
                sendMsg(SHOW_REAL_DATA, msg.getBytes());
            }
            @Override
            public void onFailed(int code, String msg){
            }
        },deviceModel);
    }

    private void sendMsg(int value, byte[] datas) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value, datas).sendToTarget();
        }
    }


//    private void goToFailedUi(String msg){
//        Intent intent=new Intent(this,SmartLinkScanFailActivity.class);
//        intent.putExtra(Constants.BIND_ERROR_MSG,msg);
//        startActivity(intent);
//        finish();
//    }
    /**
     * 处理实时数据
     *
     * @param t
     */
    private void dealRealData(byte[] t) {
        if (t == null || t.length < 5) {
            return;
        }
        MattressModel mattressModel = new MattressModel();
        mattressModel.setHeartBeat(t[0]);
        mattressModel.setBreathe(t[1]);
        mattressModel.setSnore((byte) (t[2] & 0x01));
        mattressModel.setIsBed((byte) ((t[2] & 0x02) >> 1));
        mattressModel.setTurnOver(t[3]);
        mattressModel.setPower(t[4]);
        mattressModel.setLace(macAddress);
        mattress=new Mattress();
        mattress.setHeartBeat(mattressModel.getHeartBeat());
        mattress.setBreathe(mattressModel.getBreathe());
        mattress.setSnore(mattressModel.getSnore());
        mattress.setIsBed(mattressModel.getIsBed());
        mattress.setTurnOver(mattressModel.getTurnOver());
        mattress.setPower(mattressModel.getPower());
        mattress.setLace(macAddress);
        mattress.setOnoff(ifclick);
        Log.e("aaaaaaaaa","睡眠检测器实时数据"+mattressModel.toString());
//        Bundle bundle=new Bundle();
//        bundle.putByte("turnOver",mattressModel.getTurnOver());
//        bundle.putByte("breathe",mattressModel.getBreathe());
//        bundle.putByte("heartBeat",mattressModel.getHeartBeat());
//        bundle.putByte("turnOver1",mattressModel.getTurnOver());
//        bundle.putByte("power",mattressModel.getPower());
//        bundle.putBoolean("ifclick",ifclick);
//        Intent intent = new Intent(action);
//        intent.putExtra("data",bundle);
//        sendBroadcast(intent);
        Log.e("aaaaaaaaa","睡眠检测器实时数据"+mattressModel.toString());
//        byte heartBeat; //心跳
//        byte breathe; //呼吸
//        byte snore; //打呼
//        byte turnOver; //翻身
//        byte isBed;//是否上床
//        byte timezone;
//        byte power; //电量 0-100表示电量，255表示正在充电
          OKHttp(mattressModel);
    }
    private void OKHttp(MattressModel mattressModel){
        OkHttpUtils.post().url("http://139.199.183.57/index.php/Sleep/getinfo")
                .addParams("heartBeat",mattressModel.getHeartBeat()+"")
                .addParams("breathe",mattressModel.getBreathe()+"")
                .addParams("snore",mattressModel.getSnore()+"")
                .addParams("turnOver",mattressModel.getTurnOver()+"")
                .addParams("isBed",mattressModel.getIsBed()+"")
                .addParams("timezone",mattressModel.getTimezone()+"")
                .addParams("power",mattressModel.getPower()+"")
                .addParams("lace",mattressModel.getLace()+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                Log.e("aaaa","失败"+call.hashCode());
            }
            @Override
            public void onResponse(String s, int i) {
                Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
                Log.e("aaaa","成功"+s);
            }
        });
    }
    private void showStatus(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvwTip.setText(msg);
                container.removeView(tvwTip);
                container.addView(tvwTip);
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (bind_circle_iv!=null){
            bind_circle_iv.startAnimation(animation);
        }
    }

    /**
     * 停止动画
     */
    private void stopAnim(){
        setPoint(0);
        if (bind_circle_iv!=null){
            bind_circle_iv.clearAnimation();
        }
    }
    private void initView() {
//        getSupportActionBar().setTitle("扫描绑定设备");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmerAnimation();
        tvwTip = (TextView) findViewById(R.id.tv_tips);
        textClick =(TextView)findViewById(R.id.text_click);
        relStopBind = (RelativeLayout) findViewById(R.id.rel_cancle);
        relStopBind.setOnClickListener(this);
        search_rl = (RelativeLayout) findViewById(R.id.search_rl);
        bind_circle_iv = (ImageView) findViewById(R.id.bind_circle_iv);
        point = (TextView) findViewById(R.id.point);
        search_rl.setVisibility(View.VISIBLE);
        animation= AnimationUtils.loadAnimation(this, R.anim.lock_round);
        bind_circle_iv=(ImageView)findViewById(R.id.bind_circle_iv);
        listViewDevice=(ListView)findViewById(R.id.lvw_devices);
        adapterDeviceList=new AdapterBleDeviceList(deviceModels,mContext) ;
        listViewDevice.setAdapter(adapterDeviceList);
        listViewDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceModel deviceModel=deviceModels.get(position);
                if (deviceModel!=null){
                    if (search_rl!=null){
                        search_rl.setVisibility(View.VISIBLE);
                    }
                    listViewDevice.setVisibility(View.GONE);
                    showStatus("绑定设备中");
                    HetBleBindApi.getInstance().connect(deviceModel);
                }
            }
        });
    }
    private void setPoint(final int value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_cancle:
                if(textClick.getText().equals("取消绑定")) {
                    finish();
                }
                if(textClick.getText().equals("确定")) {
                    finish();
                }
                break;
        }
    }
    private void sendMsg(int value) {
        if (mStableHandler1 != null) {
            mStableHandler1.obtainMessage(value).sendToTarget();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



}
