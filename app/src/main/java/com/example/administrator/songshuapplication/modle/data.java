package com.example.administrator.songshuapplication.modle;

/**
 * Created by Administrator on 2017/6/23.
 */

public class data {
public  String getAccount() {
    return account;
}public void   setAccount(String account) {
    this.account = account;
}public String getAge() {
    return age;
}public void   setAge(String age) {
    this.age = age;
}
    public String getBad() {
    return bad;
}public void
    setBad(String bad) {
    this.bad = bad;
}
    public String getId() {
    return id;
}
    public void   setId(String id) {
    this.id = id;
}public String getLace() {
    return lace;
}public void   setLace(String lace) {
    this.lace = lace;
}public String getName() {
    return name;
}public void   setName(String name) {
    this.name = name;
}public String getRoom() {
    return room;
}public void   setRoom(String room) {
    this.room = room;
}public String getSex() {
    return sex;
}public void   setSex(String sex) {
    this.sex = sex;
}public String getUfag() {
    return ufag;
}public void   setUfag(String ufag) {
    this.ufag = ufag;
}
    private String account;
    private String age;
    private String bad;
    private String id;
    private String lace;
    private String name;
    private String room;
    private String sex;
    private String ufag;
    public data() {
    }

    public data(String account, String age, String bad, String lace, String id, String name, String room, String sex, String ufag) {
        this.account = account;
        this.age = age;
        this.bad = bad;
        this.lace = lace;
        this.id = id;
        this.name = name;
        this.room = room;
        this.sex = sex;
        this.ufag = ufag;
    }
}
