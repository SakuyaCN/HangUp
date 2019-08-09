package com.sakuya.hangup.entity;

public class BagGoods{
    private int bg_id;
    private GoodsEntity goodsEntity;
    private int count;

    public BagGoods(int bg_id, GoodsEntity goodsEntity, int count) {
        this.bg_id = bg_id;
        this.goodsEntity = goodsEntity;
        this.count = count;
    }

    public GoodsEntity getGoodsEntity() {
        return goodsEntity;
    }

    public void setGoodsEntity(GoodsEntity goodsEntity) {
        this.goodsEntity = goodsEntity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
