package com.example.administrator.songshuapplication.modle;

/**
 * Created by yangtong on 2017/6/6.
 */

public class SleepContext {
    public float[] getBroken() {
        return broken;
    }

    public void setBroken(float[] broken) {
        this.broken = broken;
    }

    public int[] getBrokenTimes() {
        return brokenTimes;
    }

    public void setBrokenTimes(int[] brokenTimes) {
        this.brokenTimes = brokenTimes;
    }

    private float[] broken;
    private int[] brokenTimes;

}
