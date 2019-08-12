package com.sakuya.hangup.entity;

import java.util.Date;

public class QuestEntity {
    private int quest_id;
    private String quest_name;
    private int quest_lv;
    private int quest_time; //0不限时 秒为单位
    private String quest_ctx; //任务说明
    private String quest_from;//插件任务 游戏任务
    private String quest_type;//任务类型 杀怪，升级，挖矿
    private int quest_goal;
    private boolean isTake; //是否可接受
    private boolean isShow; //是否显示
    private Date[] time;//可接受时间段
    private boolean isLoop; //是否每天重置
    private String info; // 任务配置

    public QuestEntity(int quest_id, String quest_name, int quest_lv, int quest_time, String quest_ctx, String quest_from, String quest_type, int quest_goal, boolean isTake, boolean isShow, Date[] time, boolean isLoop) {
        this.quest_id = quest_id;
        this.quest_name = quest_name;
        this.quest_lv = quest_lv;
        this.quest_time = quest_time;
        this.quest_ctx = quest_ctx;
        this.quest_from = quest_from;
        this.quest_type = quest_type;
        this.quest_goal = quest_goal;
        this.isTake = isTake;
        this.isShow = isShow;
        this.time = time;
        this.isLoop = isLoop;
    }

    public int getQuest_id() {
        return quest_id;
    }

    public void setQuest_id(int quest_id) {
        this.quest_id = quest_id;
    }

    public String getQuest_name() {
        return quest_name;
    }

    public void setQuest_name(String quest_name) {
        this.quest_name = quest_name;
    }

    public int getQuest_lv() {
        return quest_lv;
    }

    public void setQuest_lv(int quest_lv) {
        this.quest_lv = quest_lv;
    }

    public int getQuest_time() {
        return quest_time;
    }

    public void setQuest_time(int quest_time) {
        this.quest_time = quest_time;
    }

    public String getQuest_ctx() {
        return quest_ctx;
    }

    public void setQuest_ctx(String quest_ctx) {
        this.quest_ctx = quest_ctx;
    }

    public String getQuest_from() {
        return quest_from;
    }

    public void setQuest_from(String quest_from) {
        this.quest_from = quest_from;
    }

    public String getQuest_type() {
        return quest_type;
    }

    public void setQuest_type(String quest_type) {
        this.quest_type = quest_type;
    }

    public int getQuest_goal() {
        return quest_goal;
    }

    public void setQuest_goal(int quest_goal) {
        this.quest_goal = quest_goal;
    }

    public boolean isTake() {
        return isTake;
    }

    public void setTake(boolean take) {
        isTake = take;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public Date[] getTime() {
        return time;
    }

    public void setTime(Date[] time) {
        this.time = time;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }
}
