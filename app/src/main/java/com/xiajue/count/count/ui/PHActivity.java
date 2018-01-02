package com.xiajue.count.count.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.xiajue.count.count.R;
import com.xiajue.count.count.bean.CountDataBean;
import com.xiajue.count.count.bean.CountJsonBean;
import com.xiajue.count.count.biz.CountHandler;
import com.xiajue.count.count.biz.PHAdapter;
import com.xiajue.count.count.database.utils.MDataDao;

import java.util.List;

/**
 * Created by xiaJue on 2017/12/28.
 */
public class PHActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private List<CountDataBean> mCountDataBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph);
        bindViews();
        setData();
    }

    private void setData() {
        mCountDataBeanList = MDataDao.getInstance(this).selectAll();
        mListView.setAdapter(new PHAdapter(this, mCountDataBeanList));
        //设置返回键
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView.setOnItemClickListener(this);
    }

    private void bindViews() {
        mListView = (ListView) findViewById(R.id.ph_lv);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CountDataBean bean = mCountDataBeanList.get(position);
        String json = mCountDataBeanList.get(position).getCountListJson();
        CountJsonBean jsonBean = new Gson().fromJson(json, CountJsonBean.class);
        List<CountHandler.Count> counts = jsonBean.getCounts();
        CountHandler handler = new CountHandler(bean.type, bean.model, bean.countNumber, new
                int[]{bean.getNumbers_from(), bean.getNumbers_to()}, bean.useTime, bean
                .unCountTimeMax);
        handler.setCounts(counts);
        handler.mUseTime = bean.getUseTime();
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("count_handler", handler);
        intent.putExtra("isInsert", false);//告诉页面不要储存内容到数据库
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
