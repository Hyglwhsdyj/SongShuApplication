package com.example.administrator.songshuapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.administrator.songshuapplication.R;
import com.example.administrator.songshuapplication.mattress.MattressModel;
import com.example.administrator.songshuapplication.modle.ChartView;
import com.example.administrator.songshuapplication.modle.ChartView1;
import com.example.administrator.songshuapplication.modle.MyGroupView;
import com.example.administrator.songshuapplication.modle.SinView;
import com.example.administrator.songshuapplication.modle.SleepContext;
import com.example.administrator.songshuapplication.presenter.OkhttpPresenter;
import com.example.administrator.songshuapplication.utils.Constants;
import com.example.administrator.songshuapplication.utils.HandlerUtil;
import com.example.administrator.songshuapplication.view.ConnectionoView;
import com.example.administrator.songshuapplication.view.ShuiMianView;
import com.het.open.lib.api.HetDeviceManagerApi;
import com.het.open.lib.api.HetSleepBleControlApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

public class RecyclerviewActivity extends AppCompatActivity implements ShuiMianView, ConnectionoView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView    eleview;
    private TextView    conview;
    private Button      button;
    private boolean     ifclick;
    private boolean     boo;
    private int         ele;
    private ChartView   mChartView;
    private Byte        xv;
    private int         xv1;
    private TextView    textViewxv;
    private int         bre;
    private TextView    textViewhx;
    private MyGroupView mSinView;
    private DeviceModel deviceModel;
    private final int SHOW_REAL_DATA    = 0x01;
    private final int SHOW_HISTROY_DATA = 0x02;
    public static String  Lace;
    private       boolean ifis;
    private       boolean ifis2;
    public final static String SP_NAME = "share_data";
    public static final String action  = "jason.broadcast.action";
    private PopupWindow   popupWindow;
    private MattressModel mattressModel;
    private boolean       whie;

    private int ii      = 0;
    private int mPointY = 0;
    private OkhttpPresenter presenter;



    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_REAL_DATA:
                    byte[] datas = (byte[]) msg.obj;
                    dealRealData(datas);
                    break;
                case SHOW_HISTROY_DATA:
                    break;
                default:
                    break;
            }

        }
    };
    Handler MyNewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    timedata();
                    break;
                case 1:
                    Toast.makeText(RecyclerviewActivity.this, "获取设备数据失败，请重新连接！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private HandlerUtil.StaticHandler mStableHandler = new HandlerUtil.StaticHandler(
            mMessageListener);
    private Toolbar mToolbar;
    private View    viewmenu;
    private int     mScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        eleview = (TextView) findViewById(R.id.elec_id);
        conview = (TextView) findViewById(R.id.conn_id);
        textViewxv = (TextView) findViewById(R.id.text_xl);
        textViewhx = (TextView) findViewById(R.id.text_xl1);
        mSinView = (MyGroupView) findViewById(R.id.sin_id);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar1);
        mattressModel = new MattressModel();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mChartView = (ChartView) findViewById(R.id.ziding_view);
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);
        SharedPreferences editor = getSharedPreferences(this);
        ifis = Boolean.valueOf(editor.getBoolean("int1", false));
        layoutView();
        Toast.makeText(this, "createifis" + ifis, Toast.LENGTH_SHORT).show();
        presenter = new OkhttpPresenter();
        if (deviceModel != null) {
            eleview.setText("正在获取电量");
            conview.setText("正在连接...");
            mChartView.AddPointToList(100);
            initData(ifis);
        } else {
            eleview.setText("未获取电量");
            conview.setText("未连接");
        }

    }


    private void initData(boolean isif) {
        ifis2 = isif;
        if (!ifis2) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Log.e("deviceModel", "run" + 0);
                    if (deviceModel != null) {
                        Lace = deviceModel.getMacAddress();
                        realDtate();
                    } else {
                        setmChartView();
                    }
                }
            }.start();
        } else {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (ifis2) {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (deviceModel != null) {
                            Lace = deviceModel.getMacAddress();
                            realDtate();
                        } else {
                            setmChartView();
                        }
                    }
                }
            }.start();
        }
    }


    private synchronized void realDtate() {
        Log.e("deviceModel", "deviceModel" + deviceModel.toString());
        HetSleepBleControlApi.getInstance().getRealData(new IHetCallback() {
            @Override
            public void onSuccess(int i, String s) {
             sendMsg(SHOW_REAL_DATA, s.getBytes());
//              dealRealData(s.getBytes());
//            Log.e("null",String.valueOf(s));
            }

            @Override
            public void onFailed(int i, String s) {
                MyNewHandler.sendEmptyMessage(1);
            }
        }, deviceModel);
    }
    private void timedata() {
        if (ifis2) {
            xv1 = mattressModel.getHeartBeat();
            bre = mattressModel.getBreathe();
            mattressModel.setLace(deviceModel.getMacAddress());
            presenter.select(mattressModel);
        } else {
            xv1 = 0;
            bre = 0;
        }
        setmChartView1(mattressModel.getPower());
    }
    /**
     * 处理实时数据
     *
     * @param t
     */
    private void dealRealData(byte[] t) {
        if (t == null || t.length < 5) {
            return;
        }
        mattressModel.setHeartBeat(t[0]);
        mattressModel.setBreathe(t[1]);
        mattressModel.setSnore((byte) (t[2] & 0x01));
        mattressModel.setIsBed((byte) ((t[2] & 0x02) >> 1));
        mattressModel.setTurnOver(t[3]);
        mattressModel.setPower(t[4]);
        //byte heartBeat; //心跳
//        byte breathe; //呼吸
//        byte snore; //打呼
//        byte turnOver; //翻身
//        byte isBed;//是否上床
//        byte timezone;
//        byte power; //电量 0-100表示电量，255表示正在充电
        Bundle bundle = new Bundle();
        bundle.putByte("turnOver", mattressModel.getTurnOver());
        bundle.putByte("breathe", mattressModel.getBreathe());
        bundle.putByte("heartBeat", mattressModel.getHeartBeat());
        bundle.putByte("turnOver1", mattressModel.getTurnOver());
        bundle.putByte("power", mattressModel.getPower());
        bundle.putBoolean("ifclick", ifclick);
        if (ifis2) {
            xv1 = mattressModel.getHeartBeat();
            bre = mattressModel.getBreathe();
            mattressModel.setLace(deviceModel.getMacAddress());
            presenter.select(mattressModel);
        } else {
            xv1 = 0;
            bre = 0;
            bundle = null;
        }
        Intent intent = new Intent(action);
        intent.putExtra("data", bundle);
        sendBroadcast(intent);
        setmChartView1(mattressModel.getPower());
    }
    private void sendMsg(int value, byte[] datas) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value, datas).sendToTarget();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recy_menu_activity, menu);
        // 返回 true 表示加载菜单文件
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_recyc:
                showWeChat();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void layoutView() {
        // 将布局文件转换成 view 对象
        viewmenu = LayoutInflater.from(this).inflate(R.layout.recycle_menu_activity, null);
//       text2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewmenu.findViewById(R.id.menu_shezhi).setOnClickListener(this);
        viewmenu.findViewById(R.id.menu_zaichuang).setOnClickListener(this);
        viewmenu.findViewById(R.id.menu_guanyu).setOnClickListener(this);
    }

    private void showWeChat() {
        // 创建 PopupWindow 对象
        popupWindow = new PopupWindow(viewmenu, mScreenWidth / 3, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 点击其他区域关闭
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
//        popupWindow点击消失时的监听
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                if(popupWindow != null && popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                }
//            }
//        });
        // 初始化 PopupWindow 的常用属性
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x92000000));
        // 设置动画
        popupWindow.setAnimationStyle(android.R.style.Animation_Activity);
        // 显示 PopupWindow
        popupWindow.showAsDropDown(mToolbar, mScreenWidth, 0);
    }

    private void setmChartView() {
        eleview.setText("未获取到电量");
        conview.setText("未连接");
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                mHandler.sendMessage(msg);
            }
        };
        timer.schedule(task, 0, 100);

    }

    private void setmChartView1(int ele) {
        conview.setText("已连接");
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                mHandler.sendMessage(msg);
            }
        };
        timer.schedule(task, 0, 100);
        if (ele < 0) {
            eleview.setText("正在充电");
        }
        if (ele == 0) {
            eleview.setText("正在获取电量");
        }else{
            eleview.setText(ele + "%");
            isEle(ele);
        }
    }

    public void isEle(int ele) {
        if (ele < 10 && ele > 0) {
            dialog2(new AlertDialog.Builder(this));
        }
        if (ele < 0) {
            eleview.setText("正在充电");
        }
    }

    protected void dialog2(AlertDialog.Builder builder) {
        builder.setTitle("电量不足！！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setIcon(R.drawable.timg);
        builder.setMessage("电量不足,请及时充电！！");
        builder.show();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (xv1 == 0) {
                textViewxv.setText("心率--次/分钟");
                mChartView.AddPointToList(100);
            } else {
                textViewxv.setText("心率" + xv1 + "次/分钟");
                mChartView.AddPointToList((int) (Math.random() * 100));
            }
            if (bre == 0) {
                mSinView.onisif(1);
                textViewhx.setText("呼吸--次/分钟");
            } else {
                mSinView.onisif(0);
                textViewhx.setText("呼吸" + bre + "次/分钟");
            }
        }
    };

    @Override
    public void getElectricity(int ele) {
    }

    @Override
    public void connection(boolean boo) {
    }

    @Override
    public void getSleep(SleepContext sleepContext) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences ref = getSharedPreferences(this);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("int1", ifis2);
        if (ifis2) {
            finish();
        } else {
            System.exit(0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences ref = getSharedPreferences(this);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("int1", ifis2);
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_shezhi:
                initData(false);
                Toast.makeText(this, "停止睡眠监测", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_zaichuang:
                initData(true);
                Toast.makeText(this, "开启睡眠监测", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_guanyu:
                initData(false);
                jiebang();
                Toast.makeText(this, "解除绑定", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void jiebang() {
        HetDeviceManagerApi.getInstance().unBind(deviceModel, new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                Toast.makeText(RecyclerviewActivity.this, "成功解除绑定", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                Toast.makeText(RecyclerviewActivity.this, "解除绑定失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void onClickBut(View view) {
//        if (ifis2) {
//            initData(false);
//            toggleButton.setText("离   床");
//            Toast.makeText(this, "停止睡眠监测", Toast.LENGTH_SHORT).show();
//        } else {
//            initData(true);
//            toggleButton.setText("在   床");
//            Toast.makeText(this, "开启睡眠监测", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    }
}
