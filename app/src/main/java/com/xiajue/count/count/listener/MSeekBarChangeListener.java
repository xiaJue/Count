package com.xiajue.count.count.listener;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by xiaJue on 2017/12/22.
 */

public class MSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
    private TextView mSeeBarTextView;

    public MSeekBarChangeListener(TextView seeBarTextView) {
        mSeeBarTextView = seeBarTextView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mSeeBarTextView.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
