package com.xiajue.count.count.database.utils;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.xiajue.count.count.bean.CountDataBean;

import com.xiajue.count.count.database.utils.CountDataBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig countDataBeanDaoConfig;

    private final CountDataBeanDao countDataBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        countDataBeanDaoConfig = daoConfigMap.get(CountDataBeanDao.class).clone();
        countDataBeanDaoConfig.initIdentityScope(type);

        countDataBeanDao = new CountDataBeanDao(countDataBeanDaoConfig, this);

        registerDao(CountDataBean.class, countDataBeanDao);
    }
    
    public void clear() {
        countDataBeanDaoConfig.clearIdentityScope();
    }

    public CountDataBeanDao getCountDataBeanDao() {
        return countDataBeanDao;
    }

}
