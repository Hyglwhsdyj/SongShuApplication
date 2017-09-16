package com.example.administrator.songshuapplication.biz;

import android.util.Log;

import com.example.administrator.songshuapplication.mattress.MattressModel;
import com.example.administrator.songshuapplication.modle.Detection;
import com.het.open.lib.api.HetSleepBleControlApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.HandlerUtil;

/**
 * Created by Administrator on 2017/6/15.
 */

public class RealTimeDataimpl implements IRealTimeData {


    @Override
    public void realTime(DeviceModel model) {
        RealTimeData(model);

    }

    private OnLoginCallBack onLoginCallBack;

    public void setOnLoginCallBack(OnLoginCallBack onLoginCallBack) {
        this.onLoginCallBack = onLoginCallBack;
    }
    public interface OnLoginCallBack {
        void callback(Detection name);
    }
    //获取实时数据
    private void RealTimeData(DeviceModel deviceModel){

        HetSleepBleControlApi.getInstance().getRealData(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
               dealRealData(msg.getBytes());
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        },deviceModel);
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
        MattressModel mattressModel = new MattressModel();
        mattressModel.setHeartBeat(t[0]);
        mattressModel.setBreathe(t[1]);
        mattressModel.setSnore((byte) (t[2] & 0x01));
        mattressModel.setIsBed((byte) ((t[2] & 0x02) >> 1));
        mattressModel.setTurnOver(t[3]);
        mattressModel.setPower(t[4]);
        String hex = Integer.toHexString(t[5] & 0xFF);
        mattressModel.setLace(hex);
        Detection detection=new Detection();
        detection.setAwake(mattressModel.getTurnOver()+"");
        detection.setBreathing(mattressModel.getBreathe()+"");
        detection.setHeart(mattressModel.getHeartBeat()+"");
        detection.setMoving(mattressModel.getTurnOver()+"");

//        ConnectionoView connectionoView=new MainActivity();
//        connectionoView.connection(onoff);
//        MianView mianView=new MainActivity();

//        Intent intent=new Intent();
//        intent.setClass(Main2Activity.this,MainActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putString("TurnOver",mattressModel.getTurnOver()+"");
//        bundle.putString("Breathe",mattressModel.getBreathe()+"");
//        bundle.putString("HeartBeat",mattressModel.getHeartBeat()+"");
//        bundle.putString("TurnOver1",mattressModel.getTurnOver()+"");
//        intent.putExtra("detection",bundle);
//        startActivity(intent);
        Log.e("aaaaaaaaa","睡眠检测器实时数据"+mattressModel.toString());
        byte heartBeat; //心跳
        byte breathe; //呼吸
        byte snore; //打呼
        byte turnOver; //翻身
        byte isBed;//是否上床
        byte timezone;
        byte power; //电量 0-100表示电量，255表示正在充电
//        OkHttpUtils.post().url("http://192.168.31.213/index.php/Sleep/getinfo")
//                .addParams("heartBeat",mattressModel.getHeartBeat()+"")
//                .addParams("breathe",mattressModel.getBreathe()+"")
//                .addParams("snore",mattressModel.getSnore()+"")
//                .addParams("turnOver",mattressModel.getTurnOver()+"")
//                .addParams("isBed",mattressModel.getIsBed()+"")
//                .addParams("timezone",mattressModel.getTimezone()+"")
//                .addParams("power",mattressModel.getPower()+"").build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int i) {
//                Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
//                Log.e("aaaa","失败");
//            }
//            @Override
//            public void onResponse(String s, int i) {
//                Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
//                Log.e("aaaa","成功");
//            }
//        });
    }
}
