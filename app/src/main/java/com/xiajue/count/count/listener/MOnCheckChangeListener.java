package com.xiajue.count.count.listener;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xiajue.count.count.R;
import com.xiajue.count.count.utils.L;

import static com.xiajue.count.count.constant.TypeModel.COUNT_ADDITION;
import static com.xiajue.count.count.constant.TypeModel.COUNT_DIVISION;
import static com.xiajue.count.count.constant.TypeModel.COUNT_MULTIPLICATION;
import static com.xiajue.count.count.constant.TypeModel.COUNT_SUBTRACTION;
import static com.xiajue.count.count.constant.TypeModel.getTypeKey;

/**
 * Created by Moing_Admin on 2018/1/4.
 */

public class MOnCheckChangeListener implements RadioGroup.OnCheckedChangeListener {
    private View mView;
    private int[] ids;
    private TextView mTextView;

    public MOnCheckChangeListener(View view, int[] ids) {
        mView = view;
        this.ids = ids;
    }

    public MOnCheckChangeListener(View view, int[] ids, TextView textView) {
        mView = view;
        this.ids = ids;
        mTextView = textView;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        L.e("change " + checkedId);
        boolean isV = false;
        for (int i = 0; i < ids.length; i++) {
            if (checkedId == ids[i]) {
                mView.setVisibility(View.VISIBLE);
                isV = true;
            }
        }
        if (!isV) {
            mView.setVisibility(View.GONE);
        }
        //....
        if (checkedId == R.id.main_radio_jj) {
            mTextView.setText(getTypeKey(COUNT_ADDITION) + getTypeKey(COUNT_SUBTRACTION));
        } else if (checkedId == R.id.main_radio_cc) {
            mTextView.setText(getTypeKey(COUNT_MULTIPLICATION) + getTypeKey(COUNT_DIVISION));
        } else if (checkedId == R.id.main_radio_all) {
            mTextView.setText(getTypeKey(COUNT_ADDITION) + getTypeKey(COUNT_SUBTRACTION) +
                    getTypeKey(COUNT_MULTIPLICATION) + getTypeKey(COUNT_DIVISION));
        }
    }
}
