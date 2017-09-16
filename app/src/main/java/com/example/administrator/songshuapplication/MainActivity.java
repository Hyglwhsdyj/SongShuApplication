package com.example.administrator.songshuapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.songshuapplication.activity.InsertActivity;
import com.example.administrator.songshuapplication.activity.RealTimeActivity;
import com.example.administrator.songshuapplication.activity.RecyclerviewActivity;
import com.example.administrator.songshuapplication.bind.Main2Activity;

import com.example.administrator.songshuapplication.modle.MyView;
import com.example.administrator.songshuapplication.presenter.ConnectionPresenter;
import com.example.administrator.songshuapplication.presenter.MianPresenter;
import com.example.administrator.songshuapplication.view.ConnectionoView;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ConnectionoView, SlidingPaneLayout.PanelSlideListener, View.OnClickListener {
    private View              mMenuContainer;
    private View              mMainContainer;
    private SlidingPaneLayout mSlidingPaneLayout;
    private boolean           mOldQQ;
    private Toolbar           mToolbar;
    private RecyclerView      recyclerView;
    private static int[]    icno    = {R.drawable.snore_setting,R.drawable.ic_scenes_need_dev_sleep,R.mipmap.ic_launcher};
    private static String[] strings = {"设置","睡眠监测","设备已连接"};
    private Intent              intent;
    private MyView              myView;
    private ConnectionPresenter connpresenter;
    private MianPresenter       mianPresenter;
    @Bind({R.id.xinlv_text, R.id.qinxing_text, R.id.huxi_text, R.id.tidong_text})
    List<TextView> textViewList;
    private TextView youxiu;
    private TextView jiankang;

    private boolean  ifclick;
    private int      mScreenWidth;
    private View     viewmenu;
    private int      turnOver;
    private TextView text1menu;
    private TextView text2menu;
    private TextView text3menu;
    private TextView text4menu;
    private TextView youxiutext;
    private int qx = 0;
    private boolean     booMenu;
    private PopupWindow popupWindow;

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(RecyclerviewActivity.SP_NAME, Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createShow1();
    }

    private void createShow1() {
        initSlideLayout();
        IntentFilter filter = new IntentFilter(RecyclerviewActivity.action);
        registerReceiver(broadcastReceiver, filter);
        layoutView();
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        recyclerView = (RecyclerView) findViewById(R.id.rcv_id);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //设置item间距
        recyclerView.addItemDecoration(new SpaceItemDecoration(30));
        recyclerView.setAdapter(new MyAdapter());
        myView = (MyView) findViewById(R.id.myview_id);
        new Thread(mRunnable).start();
        youxiu = (TextView) findViewById(R.id.youxiu_id);
        jiankang = (TextView) findViewById(R.id.jiankang_id);
        youxiutext = (TextView) findViewById(R.id.youxiu_id);
        intent = new Intent(this, RealTimeActivity.class);
//      intent = new Intent(this,RecyclerviewActivity.class);
        Toast.makeText(this, "create", Toast.LENGTH_SHORT).show();
        viewText1();
    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mSpace;
            }

        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }

    //根据每个时间段的数据显示进度
    private Runnable mRunnable = new Runnable() {
        public void run() {
            myView.setProgress(100);
        }
    };

    @Override
    public void connection(final boolean boo) {
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            // TODO Auto-generated method stub
            Bundle bun = intent.getBundleExtra("data");
            if (bun != null) {
                viewText(bun);
            } else {
                viewText1();
            }
        }
    };
   Handler mianHandler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           switch (msg.what){
               case 0:

                   break;
           }
       }
   };

    private void senddata(){

    }
    private void viewText1() {
        textViewList.get(0).setText("心率--次/分钟");
        textViewList.get(1).setText("清醒--次");
        textViewList.get(2).setText("呼吸率--次/分钟");
        textViewList.get(3).setText("体动--次");
    }

    private void viewText(Bundle bun) {

        turnOver = turnOver + Integer.parseInt(String.valueOf(bun.getByte("turnOver1")));

        if (bun.getByte("heartBeat") != 0) {
            textViewList.get(0).setText("心率" + bun.getByte("heartBeat") + "次/分钟");//心率
        } else {
            textViewList.get(0).setText("心率--次/分钟");
        }
        if (turnOver != 0) {
            if (turnOver % 5 == 0) {
                if (turnOver / 5 > 5) {
                    qx = qx;
                } else {
                    if (turnOver / 5 == 1)
                        qx = 1;
                    if (turnOver / 5 == 2)
                        qx = 2;
                    if (turnOver / 5 == 3)
                        qx = 3;
                    if (turnOver / 5 == 4)
                        qx = 4;
                    if (turnOver / 5 == 5)
                        qx = 5;
                }
            }
            textViewList.get(1).setText("清醒" + qx + "次");//清醒
        } else {
            textViewList.get(1).setText("清醒--次");
        }
        if (bun.getByte("breathe") != 0) {
            textViewList.get(2).setText("呼吸率" + bun.getByte("breathe") + "次/分钟");//呼吸
        } else {
            textViewList.get(2).setText("呼吸率--次/分钟");
        }
        if (turnOver != 0) {
            textViewList.get(3).setText("体动" + turnOver + "次");//体动
        } else {
            textViewList.get(3).setText("体动--次");
        }

    }

    class MyAdapter extends RecyclerView.Adapter<ViewHoder> {
        @Override
        public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHoder viewHoder = new ViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_itme, null));
            return viewHoder;
        }

        @Override
        public void onBindViewHolder(ViewHoder holder, final int position) {
            Log.e("aaa", "bkg1");
            holder.ref(strings[position], icno[position]);
            Log.e("aaa", "bkg2");
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (strings[position]) {
                        case "设置":
//                           startActivity(intent);
                            break;
                        case "睡眠检测":
                            startActivity(intent);
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount(){
            return strings.length;
        }
    }

    class ViewHoder extends RecyclerView.ViewHolder{
        View view;
        private final TextView  textView;
        private final ImageView image;

        public ViewHoder(View itemView) {
            super(itemView);
            this.view = itemView;
            textView = (TextView) view.findViewById(R.id.item_re);
            image = (ImageView) view.findViewById(R.id.rec_img);
        }

        public void ref(String name, int icon) {
            textView.setText(name);
            image.setBackground(getResources().getDrawable(icon));
            recyclerView.hasPendingAdapterUpdates();
        }
    }

    //用于加载菜单布局文件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 将menu文件加载成为menu对象
        getMenuInflater().inflate(R.menu.mianmenu, menu);
        // 返回 true 表示加载菜单文件
        return true;
    }

    //菜单每一个选项选择的事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menu_setting:
//                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
//            break;
//            case R.id.menu_about :
//                Toast.makeText(this,"关于", Toast.LENGTH_SHORT).show();
//            break;
//            case R.id.menu_shebei :
//                startActivity(new Intent(this, CLifeActivity.class));
//            break;
            case R.id.menu_del:
                showWeChat();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void layoutView() {
        // 将布局文件转换成 view 对象
        viewmenu = LayoutInflater.from(this).inflate(R.layout.menu_activity, null);
//       text2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        text3menu = (TextView) viewmenu.findViewById(R.id.menu_shuimian);
        text3menu.setOnClickListener(this);
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

    //region 滑动面板设置
    private void initSlideLayout() {
        mMenuContainer = findViewById(R.id.layout_menu_container);
        mMainContainer = findViewById(R.id.layout_main_container);
        mToolbar = (Toolbar) mMainContainer.findViewById(R.id.main_toolbar);
        // 将 Toolbar 设置为兼容的 ActionBar  在所有的设置之前设置
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int widthPixels = getResources().getDisplayMetrics().widthPixels;
//                widthPixels = (int) (widthPixels * 0.2);
                mSlidingPaneLayout.openPane();
                //点击打开隐藏菜单
            }
        });
        mMenuContainer.findViewById(R.id.login_id).setOnClickListener(this);
        mSlidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.layout_slide_container);
        mSlidingPaneLayout.setPanelSlideListener(this);
        // 设置 Menu 的宽度 为屏幕宽度的20%
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        widthPixels = (int) (widthPixels * 0.2);
        // 设置宽度
        SlidingPaneLayout.LayoutParams params = new SlidingPaneLayout.LayoutParams(widthPixels, SlidingPaneLayout.LayoutParams.MATCH_PARENT);
        mMenuContainer.setLayoutParams(params);
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        // 将menu的x/y进行缩放操作
//        mMenuContainer.setScaleY( slideOffset / 2 + 0.5F );
//        mMenuContainer.setScaleX(slideOffset / 2 + 0.5F );
//        // 将主界面的y轴进行缩放操作
//        mMainContainer.setScaleY(1 - slideOffset / 5 );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        popupWindow.dismiss();

        SharedPreferences.Editor editor = getSharedPreferences(this).edit();
        editor.remove("int1").commit();
        System.exit(1);
    }

    @Override
    public void onPanelOpened(View panel) {
    }

    @Override
    public void onPanelClosed(View panel) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("booMenu", booMenu);
        Log.e("booMenu", "onSaveInstanceState");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        moveTaskToBack(true);
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_shuimian:
                startActivity(new Intent(this, Main2Activity.class));
                break;
            case R.id.login_id:
                startActivity(new Intent(this, InsertActivity.class));
                break;

        }

    }

}
