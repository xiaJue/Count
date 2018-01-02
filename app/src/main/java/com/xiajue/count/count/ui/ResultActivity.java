package com.xiajue.count.count.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiajue.count.count.R;
import com.xiajue.count.count.bean.CountDataBean;
import com.xiajue.count.count.bean.CountJsonBean;
import com.xiajue.count.count.bean.ResultCountListBean;
import com.xiajue.count.count.biz.CountHandler;
import com.xiajue.count.count.biz.ResultCountAdapter;
import com.xiajue.count.count.constant.TypeModel;
import com.xiajue.count.count.database.utils.MDataDao;
import com.xiajue.count.count.utils.StringUtils;
import com.xiajue.count.count.view.NoKeyboardEditText;

import java.util.ArrayList;
import java.util.List;

import static com.xiajue.count.count.constant.ConfigHelper.setFractionTextColor;
import static com.xiajue.count.count.constant.TypeModel.MODEL_COUNT_TIME;
import static com.xiajue.count.count.constant.TypeModel.MODEL_UN_COUNT_TIME;

/**
 * Created by xiaJue on 2017/12/23.
 */

public class ResultActivity extends BaseActivity {
    private CountHandler mHandler;
    //view
    private TextView mFractionTextView;
    private RadioButton mRadioType;
    private NoKeyboardEditText mNumberEditFrom;
    private NoKeyboardEditText mNumberEditTo;
    private RadioButton mRadioModel;
    private SeekBar mSeekBar;
    private TextView mSeeBarText;
    private TextView mTimeTextView;
    private ListView mListView;
    private List<ResultCountListBean> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mHandler = (CountHandler) getIntent().getSerializableExtra("count_handler");
        bindViews();
        setUiData();
    }

    private void setUiData() {
        //设置返回键
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.jg);
        //....
        int fraction = (int) (((float) mHandler.getCorrectCount()) /
                ((float) mHandler.getCounts().size()) * 100);
        mFractionTextView.setText(fraction + getString(R.string.f));
        setFractionTextColor(mFractionTextView, fraction);
        mRadioType.setText(mHandler.mType == 1 ? getString(R.string.jj) : mHandler.mType == 2 ?
                getString(R.string.cc) : getString(R.string.zh));
        mRadioModel.setText(mHandler.mModel == 1 ? getString(R.string.js) : mHandler.mModel == 2
                ? getString(R.string.djs) : getString(R.string.bjs));
        mNumberEditFrom.setText(String.valueOf(mHandler.mNumbers[0]));
        mNumberEditTo.setText(String.valueOf(mHandler.mNumbers[1]));
        mSeekBar.setProgress(mHandler.getCounts().size());
        mSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mSeeBarText.setText(String.valueOf(mHandler.getCounts().size()));
        switch (mHandler.mModel) {
            case MODEL_COUNT_TIME:
                mTimeTextView.setVisibility(View.VISIBLE);
                mTimeTextView.setText(getString(R.string.use_time) + StringUtils.formatDate
                        (mHandler.mUseTime));
                break;
            case MODEL_UN_COUNT_TIME:
                mTimeTextView.setVisibility(View.VISIBLE);
                mTimeTextView.setText(getString(R.string.djs_s) + StringUtils.formatDate(mHandler
                        .mMaxTime) + getString(R.string.use_time)
                        + StringUtils.formatDate(mHandler.mUseTime));
                break;
        }
        //listView显示每一条题目的情况
        mList = new ArrayList<>();
//        mList.add(new ResultCountListBean("标准答案", "回答", Color.BLACK));
        for (CountHandler.Count c :
                mHandler.getCounts()) {
            ResultCountListBean bean = new ResultCountListBean();
            if (c.getType() == TypeModel.COUNT_ADDITION || c.getType() == TypeModel
                    .COUNT_SUBTRACTION) {
                int a = (int) c.getAnswer();
                bean.setSubject(c.getSubject() + a);
                bean.setAnswer(c.getUserAnswer() == -1 ? getString(R.string.wd) : String.valueOf(
                        (int) c
                                .getUserAnswer()));
            } else {
                bean.setSubject(c.getSubject() + c.getAnswer());
                bean.setAnswer(c.getUserAnswer() == -1 ? getString(R.string.wd) : String.valueOf
                        (c.getUserAnswer()));
            }
            bean.setAnswerColor(c.isCorrect() ? Color.BLACK : Color.RED);
            mList.add(bean);
        }
        mListView.setAdapter(new ResultCountAdapter(this, mList));
        boolean isInsert = getIntent().getBooleanExtra("isInsert", true);
        if (isInsert) {
            //保存数据到数据库--排行榜
            String countJsonStr = new Gson().toJson(new CountJsonBean(mHandler.getCounts()),
                    CountJsonBean.class);
            CountDataBean bean = new CountDataBean(mHandler.mType, mHandler.mModel, mHandler
                    .mNumbers[0], mHandler.mNumbers[1], mHandler.mCountNumber, ((int) mHandler
                    .mUseTime), ((int) mHandler.mMaxTime), fraction, countJsonStr, System
                    .currentTimeMillis());

            MDataDao.getInstance(this).insert(bean);
        }
    }

    private void bindViews() {
        mFractionTextView = (TextView) findViewById(R.id.result_fraction);
        mRadioType = (RadioButton) findViewById(R.id.result_radio_type);
        mRadioModel = (RadioButton) findViewById(R.id.result_radio_model);
        mNumberEditFrom = (NoKeyboardEditText) findViewById(R.id.result_number_edit_from);
        mNumberEditTo = (NoKeyboardEditText) findViewById(R.id.result_number_edit_to);
        mSeekBar = (SeekBar) findViewById(R.id.result_count_number_seekBar);
        mSeeBarText = (TextView) findViewById(R.id.result_count_number_text);
        mTimeTextView = (TextView) findViewById(R.id.result_time_text);
        mListView = (ListView) findViewById(R.id.result_listView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
