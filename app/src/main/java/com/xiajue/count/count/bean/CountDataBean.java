package com.xiajue.count.count.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xiaJue on 2017/12/28.
 */

@Entity
public class CountDataBean {
    public int type;
    public int model;
    public int numbers_from;
    public int numbers_to;
    public int countNumber;
    public int useTime;
    public int unCountTimeMax;
    public int fraction;
    public String countListJson;
    @Id
    @Unique
    public long lastTime;//作为每一条数据的唯一标识符
    @Generated(hash = 1476658489)
    public CountDataBean(int type, int model, int numbers_from, int numbers_to,
            int countNumber, int useTime, int unCountTimeMax, int fraction,
            String countListJson, long lastTime) {
        this.type = type;
        this.model = model;
        this.numbers_from = numbers_from;
        this.numbers_to = numbers_to;
        this.countNumber = countNumber;
        this.useTime = useTime;
        this.unCountTimeMax = unCountTimeMax;
        this.fraction = fraction;
        this.countListJson = countListJson;
        this.lastTime = lastTime;
    }
    @Generated(hash = 1395812325)
    public CountDataBean() {
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getModel() {
        return this.model;
    }
    public void setModel(int model) {
        this.model = model;
    }
    public int getNumbers_from() {
        return this.numbers_from;
    }
    public void setNumbers_from(int numbers_from) {
        this.numbers_from = numbers_from;
    }
    public int getNumbers_to() {
        return this.numbers_to;
    }
    public void setNumbers_to(int numbers_to) {
        this.numbers_to = numbers_to;
    }
    public int getCountNumber() {
        return this.countNumber;
    }
    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }
    public int getUseTime() {
        return this.useTime;
    }
    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }
    public int getUnCountTimeMax() {
        return this.unCountTimeMax;
    }
    public void setUnCountTimeMax(int unCountTimeMax) {
        this.unCountTimeMax = unCountTimeMax;
    }
    public int getFraction() {
        return this.fraction;
    }
    public void setFraction(int fraction) {
        this.fraction = fraction;
    }
    public long getLastTime() {
        return this.lastTime;
    }
    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }
    public String getCountListJson() {
        return this.countListJson;
    }
    public void setCountListJson(String countListJson) {
        this.countListJson = countListJson;
    }
}
