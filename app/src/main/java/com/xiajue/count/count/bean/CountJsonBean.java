package com.xiajue.count.count.bean;

import com.xiajue.count.count.biz.CountHandler;

import java.util.List;

/**
 * Created by xiaJue on 2017/12/29.
 * use create json
 */

public class CountJsonBean {

    public CountJsonBean() {
    }

    public CountJsonBean(List<CountHandler.Count> counts) {
        mCounts = counts;
    }

    private List<CountHandler.Count> mCounts;

    public List<CountHandler.Count> getCounts() {
        return mCounts;
    }

    public void setCounts(List<CountHandler.Count> counts) {
        mCounts = counts;
    }
}
