package com.xiajue.count.count.biz;

import android.os.Handler;
import android.widget.TextView;

import com.xiajue.count.count.utils.StringUtils;

/**
 * Created by xiaJue on 2017/12/25.
 */

public class CountTime {
    private static final int COUNT_TIME = 231;
    private static final int UN_COUNT_TIME = 232;
    private long mS = 0;
    private int mUnTime;
    private String mTimeText;
    private TextView mTextView;//time TextView
    private int mWhat;

    private long mMilliseconds = 1000;//更新频率

    private boolean countable;
    private boolean isStopting;

    public CountTime(TextView textView) {
        mTextView = textView;
    }

    public void startCountTime() {
        countable = true;
        mWhat = COUNT_TIME;
        mHandler.postDelayed(mRunnable, mMilliseconds);
    }

    public void startUnCountTime(int maxTime, UnTimeListener listener) {
        mUnTimeListener = listener;
        countable = true;
        mWhat = UN_COUNT_TIME;
        mUnTime = maxTime;
        mHandler.postDelayed(mRunnable, mMilliseconds);
    }

    public void stop() {
        isStopting = true;
        countable = false;
    }

    private Runnable mRunnable;
    {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mS++;
                switch (mWhat) {
                    case COUNT_TIME:
                        mTimeText = StringUtils.formatDate(mS);
                        break;
                    case UN_COUNT_TIME:
                        mUnTime--;
                        mTimeText = StringUtils.formatDate(mUnTime);
                        if (mUnTime == 0) {
                            mUnTimeListener.onUnTimeFinish();
                            countable = false;
                        }
                        break;
                }
                mTextView.setText(mTimeText);
                if (countable) {
                    mHandler.postDelayed(this, mMilliseconds);
                }
            }
        };
    }

    public void reStart() {
        if (isStopting) {
            countable = true;
            mHandler.postDelayed(mRunnable, mMilliseconds);
        }
    }

    private Handler mHandler;

    {
        mHandler = new Handler();
    }

    public long getTime() {
        return mS;
    }

    private UnTimeListener mUnTimeListener;

    public interface UnTimeListener {
        void onUnTimeFinish();
    }
}
