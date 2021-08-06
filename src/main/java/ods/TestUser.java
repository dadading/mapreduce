package ods;

public class TestUser {
    private String uid;//主播ID
    private int gold;//金币数量
    private int watchnumpv;//观看总PV
    private int follower;//粉丝关注数量
    private int length;//视频刊播总时长

    public TestUser(String uid, int gold, int watchnumpv, int follower, int length) {
        this.uid = uid;
        this.gold = gold;
        this.watchnumpv = watchnumpv;
        this.follower = follower;
        this.length = length;
    }

    public TestUser() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getWatchnumpv() {
        return watchnumpv;
    }

    public void setWatchnumpv(int watchnumpv) {
        this.watchnumpv = watchnumpv;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
