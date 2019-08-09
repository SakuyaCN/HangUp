package com.sakuya.hangup.entity;

public class LvEntity {
    private int lv;
    private long exp;

    public LvEntity(int lv, long exp) {
        this.lv = lv;
        this.exp = exp;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }
}
