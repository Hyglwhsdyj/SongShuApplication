package com.example.administrator.songshuapplication.modle;

/**
 * Created by Administrator on 2017/6/22.
 */

public class Score {
    private Byte xv;
    private Byte hx;
    private int td;
    private Byte qx;

    public Score(Byte xv, int td) {
        this.xv = xv;
        this.td = td;
    }

    public Byte getXv() {
        return xv;
    }

    public void setXv(Byte xv) {
        this.xv = xv;
    }

    public Byte getHx() {
        return hx;
    }

    public void setHx(Byte hx) {
        this.hx = hx;
    }


    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public Byte getQx() {
        return qx;
    }

    public void setQx(Byte qx) {
        this.qx = qx;
    }

    public String setScore(Score score){
        if(score.getXv()<=55&&score.getTd()>20){
            return "差";
        }
        if((score.getXv()<=75&&score.getXv()>55)&&score.getTd()<=10){
            return "优秀";
        }
        if(score.getXv()>90&&score.getTd()>20){
            return "差";
        }
        return "中";
    }
}
