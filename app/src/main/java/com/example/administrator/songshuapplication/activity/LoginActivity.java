package com.example.administrator.songshuapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.songshuapplication.MainActivity;
import com.example.administrator.songshuapplication.R;
import com.example.administrator.songshuapplication.modle.AppConstant;
import com.example.administrator.songshuapplication.modle.MyView;
import com.example.administrator.songshuapplication.modle.User;
import com.example.administrator.songshuapplication.presenter.LoginPresenter;
import com.example.administrator.songshuapplication.view.LoginView;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private static final String TAG ="LoginActivity" ;
    @Bind({R.id.name_e1, R.id.ped_e1})
    List<EditText> mEditTexts;
    private LoginPresenter mLoginPresenter;
    private ProgressDialog mDialog;
    Toolbar toolbar;
    private Button button;
    private ImageView imageView;
    private TextView textView1;
    private TextView textView;
    public static String openidString;
    public static String nicknameString;
    public static QQToken mQQAuth;
    private static  String   mAppid;
    Bitmap bitmap = null;
private Tencent mTencent;
    private Intent intent;
    private MyView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initSlideLayout();
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("登录");
        mDialog.setMessage("请稍后。。。");
        findViewById(R.id.denglu).setOnClickListener(this);
        // 初始化 Presenter 对象
        mLoginPresenter = new LoginPresenter(this);
        button = (Button) findViewById(R.id.login);
        button.setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.user_logo);
        textView1 = (TextView) findViewById(R.id.user_nickname);
        textView = (TextView) findViewById(R.id.user_openid);
        view = (MyView) findViewById(R.id.myview_id);
        intent = new Intent(this, MainActivity.class);
    }




    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.loginmenu,menu);

        return true;
    }

    private void initSlideLayout() {
        toolbar= (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.menu_del:
                startActivity(new Intent(this,InsertActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onError(String e) {


        Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onSuccess(User user)  {
        if (user!=null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else {
            Toast.makeText(this,"登录失败", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void showProgress() {
        mDialog.show();
    }

    @Override
    public void hideProgress() {
        mDialog.hide();
    }

    @Override
    public String getName() {
        return mEditTexts.get(0).getText().toString();
    }

    @Override
    public String getPwd() {
        return mEditTexts.get(1).getText().toString();
    }

    @Override
    public void clearPwd() {
        mEditTexts.get(1).getText().clear();
    }

    @Override
    public void onClick(View view) {
switch (view.getId()){
    case R.id.denglu:
        try {
            mLoginPresenter.login();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        break;
    case R.id.login :
        LoginQQ();
    }
}
    public void LoginQQ(){
        //这里是调用QQ登录的关键代码
  //这里的APP_ID请换成你应用申请的APP_ID，我这里使用的是DEMO中官方提供的测试APP_ID 222222
            mAppid = AppConstant.APP_ID;
  //第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
            mTencent = Tencent.createInstance(mAppid,getApplicationContext());
/**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
 官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all” 
 第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类*/
        Toast.makeText(this, "登陆1", Toast.LENGTH_SHORT).show();
            mTencent.login(this,"all",new BaseUiListener());
        }
    /**当自定义的监听器实现IUiListener接口后，必须要实现接口的三个方法，

     * onComplete  onCancel onError

     *分别表示第三方登录成功，取消 ，错误。*/

    private class BaseUiListener implements IUiListener {


        public void onCancel() {
            Toast.makeText(getApplicationContext(),"取消授权",Toast.LENGTH_SHORT).show();
        }
        public void onComplete(Object response) {
            try {
                Toast.makeText(LoginActivity.this,"登陆2", Toast.LENGTH_SHORT).show();

//获得的数据是JSON格式的，获得你想获得的内容
//如果你不知道你能获得什么，看一下下面的LOG
                Log.e("aaa","授权成功");
                Log.e(TAG, "-------------"+response.toString());
                JSONObject obj = (JSONObject) response;
               String openID = obj.getString("openid");
//                textView.setText(openidString);
                Log.e("aaa","aaa"+openID);
               String Saccess_token= obj.getString("access_token");
               String  expires_in = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(Saccess_token,expires_in);
            } catch (JSONException e) {
                e.printStackTrace();
            }
/**到此已经获得OpneID以及其他你想获得的内容了
 QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
 sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
 如何得到这个UserInfo类呢？
   */
            mQQAuth = mTencent.getQQToken();
            UserInfo info = new UserInfo(getApplicationContext(),mQQAuth);
//这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON          
            info.getUserInfo(new IUiListener() {
                public void onComplete(final Object response) {

//// TODO Auto-generated method stub
//                    Message msg =new Message();
//                    msg.obj = response;
//                    msg.what = 0;
//                    mHandler.sendMessage(msg);
//                    /**由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接
//                 * 在mHandler里进行操作
//                 */
//                    new Thread(){
//                        @Override
//                        public void run() {
//                  // TODO Auto-generated method stub
//                            JSONObject json = (JSONObject)response;
//                            try {
//                                bitmap = Util.getbitmap(json.getString("figureurl_<a href='http://www.it165.net/qq/' target='_blank' class='keylink'>qq</a>_2"));
//                            } catch (JSONException e) {
//                   // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//                            Message msg = new Message();
//                            msg.obj = bitmap;
//                            msg.what = 1;
//                            mHandler.sendMessage(msg);
//                        }
//                    }.start();
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                public void onCancel() {
                    Log.e(TAG, "--------------111112");
                    Toast.makeText(getApplicationContext(),"取消登录",Toast.LENGTH_SHORT).show();
// TODO Auto-generated method stub                 
                }
                public void onError(UiError arg0) {
                    Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
// TODO Auto-generated method stub
                    Log.e(TAG, "-111113"+":"+arg0);
                }
            });



        }



        public void onError(UiError arg0) {
      // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {

                JSONObject response = (JSONObject) msg.obj;

                if (response.has("nickname")) {

                    try {

                        nicknameString=response.getString("nickname");
                        textView1.setText(nicknameString);
                        Log.e(TAG, "--"+nicknameString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else if(msg.what == 1){
                Bitmap bitmap = (Bitmap)msg.obj;
                imageView.setImageBitmap(bitmap);
            }
        }
    };

}

