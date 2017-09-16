package com.example.administrator.songshuapplication.modle;

/**
 * Created by yangtong on 2017/6/7.
 */

public class Detection {
    private String Heart;//心率
    private String Awake;//清醒
    private String breathing;//呼吸
    private String Moving;//体动

    public String getHeart() {
        return Heart;
    }

    public void setHeart(String heart) {
        Heart = heart;
    }

    public String getAwake() {
        return Awake;
    }

    public void setAwake(String awake) {
        Awake = awake;
    }

    public String getBreathing() {
        return breathing;
    }

    public void setBreathing(String breathing) {
        this.breathing = breathing;
    }

    public String getMoving() {
        return Moving;
    }

    public void setMoving(String moving) {
        Moving = moving;
    }
}
