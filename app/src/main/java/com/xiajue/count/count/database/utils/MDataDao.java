package com.xiajue.count.count.database.utils;

import android.content.Context;

import com.xiajue.count.count.bean.CountDataBean;

import java.util.List;

/**
 * Created by xiaJue on 2017/12/28.
 */

public class MDataDao {
    CountDataBeanDao mCBDao;
    private static MDataDao mMDataDao;

    public static MDataDao getInstance(Context context) {
        if (mMDataDao == null) {
            synchronized (MDataDao.class) {
                if (mMDataDao == null) {
                    mMDataDao = new MDataDao(context);
                }
            }
        }
        return mMDataDao;
    }

    public MDataDao(Context context) {
        DaoSession daoSession = DaoMaster.newDevSession(context, "count.db");
        mCBDao = daoSession.getCountDataBeanDao();
    }

    public void insert(CountDataBean bean) {
        mCBDao.insert(bean);
    }

    public void delete(CountDataBean bean) {
        mCBDao.delete(bean);
    }

    /*public void update(CountDataBean bean) {
        mCBDao.update(bean);
    }*/
    public List<CountDataBean> selectAll() {
        return mCBDao.queryBuilder().build().list();
    }
}
