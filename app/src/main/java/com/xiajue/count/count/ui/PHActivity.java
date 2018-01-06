package com.xiajue.count.count.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.Menu;
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
import com.xiajue.count.count.utils.DialogManager;

import java.util.List;

/**
 * Created by xiaJue on 2017/12/28.
 */
public class PHActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final int CONTEXT_MENU_DELETE = 557;
    private ListView mListView;
    private List<CountDataBean> mCountDataBeanList;
    private PHAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph);
        bindViews();
        setData();
    }

    private void bindViews() {
        mListView = (ListView) findViewById(R.id.ph_lv);
    }

    private void setData() {
        mCountDataBeanList = MDataDao.getInstance(this).selectAll();
        mAdapter = new PHAdapter(this, mCountDataBeanList);
        mListView.setAdapter(mAdapter);
        //设置返回键
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView.setOnItemClickListener(this);
        //长按删除--上下文菜单
        registerForContextMenu(mListView);
    }

    /**
     * 创建上下文菜单
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        menu.setHeaderTitle(R.string.headerTitle);
        menu.add(0, CONTEXT_MENU_DELETE, Menu.NONE, R.string.delete);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * 当选择上下文菜单时
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case CONTEXT_MENU_DELETE:
                showDeleteInquiry(menuInfo.position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteInquiry(final int position) {
        DialogManager.showInquiry(this, getString(R.string.delete_inquiry), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //从数据库和listView中移除条目
                MDataDao.getInstance(PHActivity.this).delete(mCountDataBeanList.get(position));
                mCountDataBeanList.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
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
