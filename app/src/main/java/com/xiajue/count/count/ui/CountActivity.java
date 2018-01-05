package com.xiajue.count.count.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiajue.count.count.R;
import com.xiajue.count.count.biz.CountBuild;
import com.xiajue.count.count.biz.CountHandler;
import com.xiajue.count.count.biz.CountTime;
import com.xiajue.count.count.constant.TypeModel;
import com.xiajue.count.count.listener.MOnKeyboardListener;
import com.xiajue.count.count.utils.DialogManager;
import com.xiajue.count.count.utils.StringUtils;
import com.xiajue.count.count.view.NumberKeyboardView;

import static com.xiajue.count.count.R.string.cf_result;
import static com.xiajue.count.count.constant.TypeModel.COUNT_DIVISION;

/**
 * Created by xiaJue on 2017/12/21.
 */

public class CountActivity extends BaseActivity implements View.OnClickListener {

    private static final int MENU_ITEM_ID_PAUSE = 557;
    private NumberKeyboardView mKeyboardView;
    private EditText mEditText;
    private Button mNextButton;
    private TextView mNumberSurplusText;
    private TextView mCountInfoText;
    private CountHandler mHandler;
    private TextView mTimeTextView;
    private TextView mCfTsTextView;
    private LinearLayout mUpStateLl;
    private TextView mUpState;
    //...
    private CountTime mCountTime;
    private MenuItem mPauseMenuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        bindView();
        getData();
        setEvents();
    }

    private int mType, mModel, mCountNumber, mNumberSurplus;
    private int[] mNumbers = new int[2];
    private int[] mNumbers2 = new int[2];

    private void getData() {
        //get intent data
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 1);
        mModel = intent.getIntExtra("model", 1);
        mNumbers[0] = intent.getIntExtra("from", 1);
        mNumbers[1] = intent.getIntExtra("to", 100);
        mNumbers2[0] = intent.getIntExtra("from2", 1);
        mNumbers2[1] = intent.getIntExtra("to2", 100);
        mCountNumber = intent.getIntExtra("count_number", 1);
        int saveDecimal = intent.getIntExtra("saveDecimal", 1);
        //build data list
        mHandler = new CountBuild().initialize(mNumbers, mNumbers2, mType, mModel, mCountNumber,
                saveDecimal)
                .build();
        mCfTsTextView.setText(getString(cf_result) +saveDecimal + getString(R.string
                .cf_result_end));
        mHandler.mCountNumber = mCountNumber;
        mHandler.mModel = mModel;
        mHandler.mType = mType;
        mHandler.mNumbers = mNumbers;
    }

    private void setEvents() {
        //设置返回键
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.dt_ing);
        mKeyboardView.setIOnKeyboardListener(new MOnKeyboardListener(mEditText));
        mNextButton.setOnClickListener(this);
        mKeyboardView.setShowDecimalPoint(true);//键盘显示小数点
        //设置剩余题目数量
        mNumberSurplus = mCountNumber;
        mNumberSurplusText.setText(String.valueOf(mNumberSurplus));
        //设置题目
        mCount = mHandler.nextCount();
        mCountInfoText.setText(mCount.getSubject());
        if (mCount.getType() == COUNT_DIVISION) {
            mCfTsTextView.setVisibility(View.VISIBLE);
        }
        //计时、倒计时、不计时
        setCountTime();
    }

    private void setCountTime() {
        switch (mModel) {
            case TypeModel.MODEL_COUNT_TIME:
                //计时
                mCountTime = new CountTime(mTimeTextView);
                mCountTime.startCountTime();
                break;
            case TypeModel.MODEL_UN_COUNT_TIME:
                //倒计时
                int time = getIntent().getIntExtra("djs_time", 60);
                mHandler.mMaxTime = time;
                mTimeTextView.setText(StringUtils.formatDate(time));
                mCountTime = new CountTime(mTimeTextView);
                mCountTime.startUnCountTime(time, new CountTime.UnTimeListener() {
                    @Override
                    public void onUnTimeFinish() {
                        //倒计时一结束立即结束
                        finishCount();
                    }
                });
                break;
            case TypeModel.MODEL_NO_COUNT_TIME:
                //不计时
                mTimeTextView.setVisibility(View.GONE);
                break;
        }
    }

    private void bindView() {
        mKeyboardView = (NumberKeyboardView) findViewById(R.id.count_key_view);
        mEditText = (EditText) findViewById(R.id.count_number_edit);
        mNextButton = (Button) findViewById(R.id.count_next_but);
        mNumberSurplusText = (TextView) findViewById(R.id.count_number_surplus_text);
        mCountInfoText = (TextView) findViewById(R.id.count_info_text);
        mTimeTextView = (TextView) findViewById(R.id.count_time_text);
        mCfTsTextView = (TextView) findViewById(R.id.count_cf_ts_text);
        mUpStateLl = (LinearLayout) findViewById(R.id.count_up_state_ll);
        mUpState = (TextView) findViewById(R.id.count_up_state);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.count_next_but:
                if (!testing()) {
                    return;
                }
                if (mNextButton.getText().toString().equals(getString(R.string.ok_finish))) {
                    finishCount();
                    return;
                }
                //神特么的切换之术
                next();
                break;

            default:
                break;
        }
    }

    private void finishCount() {
        mNextButton.setEnabled(false);//防止多次点击
        if (mModel != TypeModel.MODEL_NO_COUNT_TIME) {
            mHandler.mUseTime = mCountTime.getTime();
        }
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("count_handler", mHandler);
        startActivity(intent);
        finish();
    }

    private CountHandler.Count mCount = null;//当前题目

    /**
     * 保存这次结果
     */
    private boolean testing() {
        String text = mEditText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, R.string.pleass_hd, Toast.LENGTH_SHORT).show();
            return false;
        }
        float answer = 0;
        try {
            answer = Float.valueOf(text);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "答案填写错误", Toast.LENGTH_SHORT).show();
            return false;
        }
//        mCount.setCorrect(answer == mCount.getAnswer());
        mCount.setUserAnswer(answer);
        return true;
    }

    /**
     * 切换到下一题
     */
    private void next() {
        mUpStateLl.setVisibility(View.VISIBLE);
        mUpState.setText(mCount.isCorrect() ? "✔" : "✖");
        mUpState.setTextColor(mCount.isCorrect() ? Color.BLACK : Color.RED);
        //....
        int index = mHandler.getNowIndex();
        mCount = mHandler.nextCount();//切换下一题
        mCountInfoText.setText(mCount.getSubject());
        mNumberSurplusText.setText(String.valueOf(--mNumberSurplus));
        mEditText.setText("");
        if (index >= mCountNumber - 1) {
            mNextButton.setText(R.string.ok_finish);
        }
        if (mCount.getType() == COUNT_DIVISION) {
            mCfTsTextView.setVisibility(View.VISIBLE);
        } else {
            mCfTsTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void inquiry() {
        stopCountTime();
        DialogManager.showInquiry(this, getString(R.string
                .zt_ing_out), new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reStartCountTime();
            }
        }, false);
    }

    private void stopCountTime() {
        if (mModel != TypeModel.MODEL_NO_COUNT_TIME) {
            mCountTime.stop();
        }
    }

    private void reStartCountTime() {
        if (mModel != TypeModel.MODEL_NO_COUNT_TIME) {
            mCountTime.reStart();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mPauseMenuItem = menu.add(0, MENU_ITEM_ID_PAUSE, 0, R.string.pause);
        mPauseMenuItem.setIcon(R.mipmap.pause);
        mPauseMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                inquiry();
                break;
            case MENU_ITEM_ID_PAUSE:
                mPauseMenuItem.setIcon(R.mipmap.continue_icon);
                pauseDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pauseDialog() {
        stopCountTime();
        DialogManager.showInquiry(this, getString(R.string.pause_ing), new String[]{getString(R
                        .string.conti),
                        getString(R.string.contin)},
                new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPauseMenuItem.setIcon(R.mipmap.pause);
                        reStartCountTime();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPauseMenuItem.setIcon(R.mipmap.pause);
                        reStartCountTime();
                    }
                }, false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCountTime();
    }

    @Override
    public void onBackPressed() {
        inquiry();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reStartCountTime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCountTime();
    }
}
