package com.sakuya.hangup.entity;

import org.bukkit.Material;

public class GoodsEntity {
    private int goods_id;
    private String goods_name;
    private int goods_lv;
    private String goods_pz;
    private String goods_type;
    private int type_id;
    private String goods_ctx;
    private Material img;

    public GoodsEntity(int goods_id, String goods_name, int goods_lv, String goods_pz, String goods_type, int type_id, String goods_ctx, Material img) {
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.goods_lv = goods_lv;
        this.goods_pz = goods_pz;
        this.goods_type = goods_type;
        this.type_id = type_id;
        this.goods_ctx = goods_ctx;
        this.img = img;
    }

    public Material getImg() {
        return img;
    }

    public void setImg(Material img) {
        this.img = img;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getGoods_lv() {
        return goods_lv;
    }

    public void setGoods_lv(int goods_lv) {
        this.goods_lv = goods_lv;
    }

    public String getGoods_pz() {
        return goods_pz;
    }

    public void setGoods_pz(String goods_pz) {
        this.goods_pz = goods_pz;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getGoods_ctx() {
        return goods_ctx;
    }

    public void setGoods_ctx(String goods_ctx) {
        this.goods_ctx = goods_ctx;
    }
}
