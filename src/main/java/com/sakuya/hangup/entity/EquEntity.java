package com.sakuya.hangup.entity;

public class EquEntity {
    private int id;
    private String name;
    private int lv;
    private String pz;
    private String type;
    private boolean isbind;
    private int baseAttr;
    private String entry_attr;

    public EquEntity(int id, String name, int lv, String pz, String type, boolean isbind, int baseAttr, String entry_attr) {
        this.id = id;
        this.name = name;
        this.lv = lv;
        this.pz = pz;
        this.type = type;
        this.isbind = isbind;
        this.baseAttr = baseAttr;
        this.entry_attr = entry_attr;
    }

    @Override
    public String toString() {
        return "EquEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lv=" + lv +
                ", pz='" + pz + '\'' +
                ", type='" + type + '\'' +
                ", isbind=" + isbind +
                ", baseAttr=" + baseAttr +
                ", entry_attr='" + entry_attr + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public String getPz() {
        return pz;
    }

    public void setPz(String pz) {
        this.pz = pz;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIsbind() {
        return isbind;
    }

    public void setIsbind(boolean isbind) {
        this.isbind = isbind;
    }

    public int getBaseAttr() {
        return baseAttr;
    }

    public void setBaseAttr(int baseAttr) {
        this.baseAttr = baseAttr;
    }

    public String getEntry_attr() {
        return entry_attr;
    }

    public void setEntry_attr(String entry_attr) {
        this.entry_attr = entry_attr;
    }
}
