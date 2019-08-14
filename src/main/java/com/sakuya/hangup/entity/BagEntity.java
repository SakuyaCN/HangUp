package com.sakuya.hangup.entity;

import java.util.List;

public class BagEntity {
    private String uuid;
    private int bagMax;
    private List<BagGoods> goods;

    public BagEntity() {
    }

    public BagEntity(String uuid, int bagMax, List<BagGoods> goods) {
        this.uuid = uuid;
        this.bagMax = bagMax;
        this.goods = goods;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getBagMax() {
        return bagMax;
    }

    public void setBagMax(int bagMax) {
        this.bagMax = bagMax;
    }

    public List<BagGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<BagGoods> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "BagEntity{" +
                "uuid='" + uuid + '\'' +
                ", bagMax=" + bagMax +
                ", goods=" + goods +
                '}';
    }
}

