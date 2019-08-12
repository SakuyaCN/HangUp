package com.sakuya.hangup.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class PlayerEntity {
    private String uuid;
    private String job;
    private String uName;
    private int lv;
    private int exp;
    private int[] attr;//力量 智力 敏捷 精神
    private int[] otherAttr;//力量 智力 敏捷 精神
    private int[] skill;
    private PlayerAttr playerAttr;
    private int[] playerEqu;

    public int[] getPlayerEqu() {
        return playerEqu;
    }

    public void setPlayerEqu(int[] playerEqu) {
        this.playerEqu = playerEqu;
    }

    public PlayerAttr getPlayerAttr() {
        return playerAttr;
    }

    public int[] getOtherAttr() {
        return otherAttr;
    }

    public void setOtherAttr(int[] otherAttr) {
        this.otherAttr = otherAttr;
    }

    public void setPlayerAttr(PlayerAttr playerAttr) {
        this.playerAttr = playerAttr;
    }

    private HashMap<String,Integer> money;

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getUuid() {
        return uuid;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public int[] getAttr() {
        return attr;
    }

    public void setAttr(int[] attr) {
        this.attr = attr;
    }

    public int[] getSkill() {
        return skill;
    }

    public void setSkill(int[] skill) {
        this.skill = skill;
    }

    public HashMap<String, Integer> getMoney() {
        return money;
    }

    public void setMoney(HashMap<String, Integer> money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "PlayerEntity{" +
                "uuid='" + uuid + '\'' +
                ", job='" + job + '\'' +
                ", uName='" + uName + '\'' +
                ", lv=" + lv +
                ", exp=" + exp +
                ", attr=" + Arrays.toString(attr) +
                ", otherAttr=" + Arrays.toString(otherAttr) +
                ", skill=" + Arrays.toString(skill) +
                ", playerAttr=" + playerAttr +
                ", money=" + money +
                '}';
    }
}
