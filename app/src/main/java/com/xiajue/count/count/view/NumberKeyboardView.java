package com.xiajue.count.count.view;

/**
 * Created by Moing_Admin on 2017/12/21.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.View;

import com.xiajue.count.count.R;

import java.util.List;

/**
 * 输入数字密码的键盘布局控件。
 */
public class NumberKeyboardView extends KeyboardView implements
        android.inputmethodservice.KeyboardView.OnKeyboardActionListener {

    // 用于区分左下角空白的按键
    private static final int KEYCODE_CLOSE = -10;

    private int mKeyBackgroundColor;
    private Rect mDeleteDrawRect;
    private Drawable mDeleteDrawable;
    private Rect mHideDrawRect;
    private Drawable mHideDrawable;

    private IOnKeyboardListener mOnKeyboardListener;

    public NumberKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs,
                      int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.NumberKeyboardView, defStyleAttr, 0);
        mDeleteDrawable = a.getDrawable(
                R.styleable.NumberKeyboardView_pkvDeleteDrawable);
        mHideDrawable = a.getDrawable(
                R.styleable.NumberKeyboardView_pkvHideDrawable);
        mKeyBackgroundColor = a.getColor(
                R.styleable.NumberKeyboardView_pkvDeleteBackgroundColor,
                Color.TRANSPARENT);
        a.recycle();

        // 设置软键盘按键的布局
        Keyboard keyboard = new Keyboard(context,
                R.xml.keyboard_number_password);
        setKeyboard(keyboard);

        setEnabled(true);
        setPreviewEnabled(false);
        setOnKeyboardActionListener(this);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 遍历所有的按键
        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {
            // 如果是左下角空白的按键，重画按键的背景
            if (key.codes[0] == KEYCODE_CLOSE) {
                if (isShowDecimalPoint) {
                    key.label = ".";
                }
                if (isShowHideEvent) {
                    drawKeyBackground(key, canvas, mKeyBackgroundColor);
                    drawDeleteButton(mHideDrawable, mHideDrawRect, key, canvas);
                }
            }
            // 如果是右下角的删除按键，重画背景，并且绘制删除的图标
            else if (key.codes[0] == Keyboard.KEYCODE_DELETE) {
                drawKeyBackground(key, canvas, mKeyBackgroundColor);
                drawDeleteButton(mDeleteDrawable, mDeleteDrawRect, key, canvas);
            }
        }
        mOnKeyboardListener.onShowKeyViewFinish();
    }

    // 绘制按键的背景
    private void drawKeyBackground(Keyboard.Key key, Canvas canvas,
                                   int color) {
        ColorDrawable drawable = new ColorDrawable(color);
        drawable.setBounds(key.x, key.y,
                key.x + key.width, key.y + key.height);
        drawable.draw(canvas);
    }

    // 绘制按键图标
    private void drawDeleteButton(Drawable mDrawable, Rect mDrawableRect, Keyboard.Key key,
                                  Canvas canvas) {
        if (mDrawable == null)
            return;

        // 计算删除图标绘制的坐标
        if (mDrawableRect == null || mDrawableRect.isEmpty()) {
            int intrinsicWidth = mDrawable.getIntrinsicWidth();
            int intrinsicHeight = mDrawable.getIntrinsicHeight();
            int drawWidth = intrinsicWidth;
            int drawHeight = intrinsicHeight;

            // 限制图标的大小，防止图标超出按键
            if (drawWidth > key.width) {
                drawWidth = key.width;
                drawHeight = drawWidth * intrinsicHeight / intrinsicWidth;
            }
            if (drawHeight > key.height) {
                drawHeight = key.height;
                drawWidth = drawHeight * intrinsicWidth / intrinsicHeight;
            }

            // 获取删除图标绘制的坐标
            int left = key.x + (key.width - drawWidth) / 2;
            int top = key.y + (key.height - drawHeight) / 2;
            mDrawableRect = new Rect(left, top,
                    left + drawWidth, top + drawHeight);
        }

        // 绘制删除的图标
        if (mDrawableRect != null && !mDrawableRect.isEmpty()) {
            mDrawable.setBounds(mDrawableRect.left,
                    mDrawableRect.top, mDrawableRect.right,
                    mDrawableRect.bottom);
            mDrawable.draw(canvas);
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        // 处理按键的点击事件
        // 点击删除按键
        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            if (mOnKeyboardListener != null) {
                mOnKeyboardListener.onDeleteKeyEvent();
            }
        }
        // 点击了非左下角按键的其他按键
        else if (primaryCode == KEYCODE_CLOSE) {
            if (isShowDecimalPoint) {
                mOnKeyboardListener.onInsertKeyEvent(".");
            }
            if (isShowHideEvent) {
                mOnKeyboardListener.onHideKeyEvent(this);
            }
        } else {
            if (mOnKeyboardListener != null) {
                mOnKeyboardListener.onInsertKeyEvent(
                        Character.toString((char) primaryCode));
            }
        }
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    private boolean isShowHideEvent, isShowDecimalPoint;

    /**
     * 是否显示小数点
     */
    public void setShowDecimalPoint(boolean showDecimalPoint) {
        isShowDecimalPoint = showDecimalPoint;
        invalidate();
    }

    /**
     * 是否显示隐藏键盘按键
     */
    public void setShowHideEvent(boolean listenerHideEvent) {
        isShowHideEvent = listenerHideEvent;
        invalidate();
    }

    /**
     * 设置键盘的监听事件。
     *
     * @param listener 监听事件
     */
    public void setIOnKeyboardListener(IOnKeyboardListener listener) {
        this.mOnKeyboardListener = listener;
    }

    public interface IOnKeyboardListener {

        void onInsertKeyEvent(String text);

        void onDeleteKeyEvent();

        void onHideKeyEvent(View keyView);

        void onShowKeyViewFinish();
    }
}