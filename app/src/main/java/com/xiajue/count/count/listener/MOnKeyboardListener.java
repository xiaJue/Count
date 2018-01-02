package com.xiajue.count.count.listener;

import android.view.View;
import android.widget.EditText;

import com.xiajue.count.count.utils.L;
import com.xiajue.count.count.view.NumberKeyboardView;

/**
 * Created by xiaJue on 2017/12/22.
 */

public class MOnKeyboardListener implements NumberKeyboardView.IOnKeyboardListener {
    private EditText mEditText;

    public MOnKeyboardListener(EditText editText) {
        mEditText = editText;
    }

    @Override
    public void onInsertKeyEvent(String text) {
        L.e(text);
        if (text.equals(".")) {
            text = ".";
        }
        mEditText.setText(mEditText.getText().toString() + text);
        mEditText.setSelection(mEditText.getText().toString().length());
    }

    @Override
    public void onDeleteKeyEvent() {
        String text = mEditText.getText().toString();
        if (text.isEmpty()) return;
        mEditText.setText(text.substring(0, text.length() - 1));
        mEditText.setSelection(mEditText.getText().toString().length());
    }

    @Override
    public void onHideKeyEvent(View view) {
        view.setVisibility(View.GONE);
    }
    @Override
    public void onShowKeyViewFinish() {
    }
}
