package com.xiajue.count.count.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.xiajue.count.count.utils.L;

/**
 * Created by Moing_Admin on 2017/12/14.
 */

public class NoKeyboardEditText extends EditText {
    public NoKeyboardEditText(Context context) {
        this(context, null);
    }

    public NoKeyboardEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoKeyboardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    private OnClickListener mOnClickListener;

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    private boolean isDown;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //拦截点击事件
        if (mOnClickListener != null) {
            if (isDown && event.getAction() == MotionEvent.ACTION_UP) {
                mOnClickListener.onClick(this);
                requestFocus();
                L.e("requestFocus id= " + getId());
                setSelection(getText().toString().length());
                return false;
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isDown = true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                isDown = false;
            }
        }
        //不弹出输入框
        return true;
    }
}
