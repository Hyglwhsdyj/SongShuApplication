package com.example.administrator.songshuapplication.modle;



/**
 * Created by Administrator on 2017/4/11.
 */

public class User {
    private String name;
    private String pwd;
    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
