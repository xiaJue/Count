package com.xiajue.count.count.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xiajue.count.count.R;
import com.xiajue.count.count.constant.ConfigHelper;
import com.xiajue.count.count.listener.MOnCheckChangeListener;
import com.xiajue.count.count.listener.MOnKeyboardListener;
import com.xiajue.count.count.listener.MSeekBarChangeListener;
import com.xiajue.count.count.view.NoKeyboardEditText;
import com.xiajue.count.count.view.NumberKeyboardView;

import static com.xiajue.count.count.constant.TypeModel.COUNT_ADDITION;
import static com.xiajue.count.count.constant.TypeModel.COUNT_DIVISION;
import static com.xiajue.count.count.constant.TypeModel.getTypeKey;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private NoKeyboardEditText mNumberFromEditText;
    private NoKeyboardEditText mNumberToEditText;
    private NoKeyboardEditText mNumber2FromEditText;
    private NoKeyboardEditText mNumber2ToEditText;
    private Button mButton;
    private RadioGroup mTypeGroup;
    private RadioGroup mModelGroup;
    private SeekBar mCountNumberSeeBar;
    private NoKeyboardEditText mSeeBarEditText;
    private NumberKeyboardView mKeyboardView;
    private ScrollView mScrollView;
    private View mDjsEditRoot;
    private EditText mDjsEdit;
    private View mCCEditRoot;
    private EditText mCCEdit;
    private TextView mTypeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        initialData();
        setEvents();
    }

    private void bindView() {
        mNumberFromEditText = (NoKeyboardEditText) findViewById(R.id.main_number_edit_from);
        mNumberToEditText = (NoKeyboardEditText) findViewById(R.id.main_number_edit_to);
        mNumber2FromEditText = (NoKeyboardEditText) findViewById(R.id.main_number2_edit_from);
        mNumber2ToEditText = (NoKeyboardEditText) findViewById(R.id.main_number2_edit_to);
        mButton = (Button) findViewById(R.id.main_start_button);
        //....
        mTypeGroup = (RadioGroup) findViewById(R.id.main_type_radioGroup);
        mModelGroup = (RadioGroup) findViewById(R.id.main_model_radioGroup);
        mCountNumberSeeBar = (SeekBar) findViewById(R.id.main_count_number_seekBar);
        mSeeBarEditText = (NoKeyboardEditText) findViewById(R.id.main_count_number_edit);
        mKeyboardView = (NumberKeyboardView) findViewById(R.id.main_key_view);
        mScrollView = (ScrollView) findViewById(R.id.main_scrollView);
        mDjsEdit = (EditText) findViewById(R.id.main_djs_edit);
        mDjsEditRoot = findViewById(R.id.main_djs_edit_root);
        mCCEdit = (EditText) findViewById(R.id.main_cc_edit);
        mCCEditRoot = findViewById(R.id.main_cc_edit_root);
        mTypeText = (TextView) findViewById(R.id.main_type_text);
    }

    /**
     * 从用户的设置数据中初始化UI
     */
    private void initialData() {
        //初始化UI数据
        getSupportActionBar().setTitle(R.string.settings_tm);
        ConfigHelper.Config config = ConfigHelper.getConfig(this);
        setCheck(config.type, mTypeGroup, new int[]{R.id.main_radio_jj, R.id.main_radio_cc, R.id
                .main_radio_all});
        mNumberFromEditText.setText(String.valueOf(config.numbers[0]));
        mNumberToEditText.setText(String.valueOf(config.numbers[1]));
        mModelGroup.check(R.id.main_radio_js);//为了触发changeListener
        setCheck(config.model, mModelGroup, new int[]{R.id.main_radio_js, R.id.main_radio_djs, R.id
                .main_radio_bjs});
        mCountNumberSeeBar.setProgress(config.countNumber);
        mSeeBarEditText.setText(String.valueOf(config.countNumber));
        mDjsEdit.setText(String.valueOf(config.unCountTimeMax));
        mDjsEditRoot.setVisibility(config.model == 2 ? View.VISIBLE : View.GONE);
        mTypeText.setText(getTypeKey(config.type == 1 ? COUNT_ADDITION : (config.type == 2 ?
                COUNT_DIVISION : COUNT_ADDITION)));

        mCCEdit.setText(String.valueOf(config.saveDecimal));
        mCCEditRoot.setVisibility(config.type == 2 ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置选中radioButton
     */
    private void setCheck(int position, RadioGroup group, int[] checkIds) {
        group.check(checkIds[position - 1]);
    }

    /**
     * 设置一些响应事件
     */
    private void setEvents() {
        mButton.setOnClickListener(this);
        mSeeBarEditText.setText(String.valueOf(mCountNumberSeeBar.getProgress()));
        //将数值更改同步到seeBar
        mSeeBarEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    s = "0";
                }
                mCountNumberSeeBar.setProgress(Integer.valueOf(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mCountNumberSeeBar.setOnSeekBarChangeListener(new MSeekBarChangeListener(mSeeBarEditText));
        mKeyboardView.setShowHideEvent(true);//键盘显示隐藏键盘的图标
        //设置所有editText的点击事件
        mNumberFromEditText.setOnClickListener(this);
        mNumberToEditText.setOnClickListener(this);
        mNumber2FromEditText.setOnClickListener(this);
        mNumber2ToEditText.setOnClickListener(this);
        mSeeBarEditText.setOnClickListener(this);
        mDjsEdit.setOnClickListener(this);
        mCCEdit.setOnClickListener(this);
        //选择倒计时时显示额外的控件
        mModelGroup.setOnCheckedChangeListener(new MOnCheckChangeListener(mDjsEditRoot, new
                int[]{R.id.main_radio_djs}));
        //选择乘除时显示额外的控件
        mTypeGroup.setOnCheckedChangeListener(new MOnCheckChangeListener(mCCEditRoot, new int[]{R
                .id.main_radio_cc, R.id.main_radio_all}, mTypeText));
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.main_start_button:
                if (!lostSome()) {
                    return;
                }
                mButton.setEnabled(false);//防止多次点击
                Intent intent = getCountIntent();
                startActivity(intent);
                break;
            case R.id.main_number_edit_from:
            case R.id.main_number_edit_to:
            case R.id.main_number2_edit_from:
            case R.id.main_number2_edit_to:
            case R.id.main_count_number_edit:
            case R.id.main_djs_edit:
            case R.id.main_cc_edit:
                editKeyboard(v);
                break;
            default:
                break;
        }
    }

    private void editKeyboard(final View v) {

        //设置数字键盘到editText
        mKeyboardView.setIOnKeyboardListener(new MOnKeyboardListener((EditText) v) {
            @Override
            public void onShowKeyViewFinish() {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                v.setFocusable(true);
                v.requestFocus();
                //屏蔽掉scrollView的滑动功能
                mScrollView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
            @Override
            public void onHideKeyEvent(View view) {
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
                super.onHideKeyEvent(view);
                //取消屏蔽scrollView的滑动功能
                mScrollView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
            }
        });
        mKeyboardView.setVisibility(View.VISIBLE);
    }

    /**
     * 将用户设置的数据封装到intent中
     * @return
     */
    @NonNull
    private Intent getCountIntent() {
        Intent intent = new Intent(this, CountActivity.class);
        //put data..
        int type = getIndexFromRadioButton(mTypeGroup);
        intent.putExtra("type", type);
        //...
        int[] numbers = getNumber(1);
        int[] numbers2 = getNumber(2);
        intent.putExtra("from", numbers[0]);
        intent.putExtra("to", numbers[1]);
        intent.putExtra("from2", numbers2[0]);
        intent.putExtra("to2", numbers2[1]);
        //....
        int model = getIndexFromRadioButton(mModelGroup);
        intent.putExtra("model", model);
        String time = mDjsEdit.getText().toString();
        int djsTime = Integer.valueOf(time);
        intent.putExtra("djs_time", djsTime);
        //.....
        int countNumber = mCountNumberSeeBar.getProgress();
        intent.putExtra("count_number", countNumber);
        String saveDecimalText = mCCEdit.getText().toString().trim();
        int saveDecimal = Integer.valueOf(saveDecimalText);
        intent.putExtra("saveDecimal", saveDecimal);
        saveToConfig(type, model, numbers, countNumber, djsTime, saveDecimal);
        return intent;
    }

    /**
     * 将用户的设置保存起来
     */
    private void saveToConfig(int type, int model, int[] numbers, int countNumber, int djsTime,
                              int saveDecimal) {
        ConfigHelper.Config config = new ConfigHelper.Config();
        config.type = type;
        config.model = model;
        config.numbers = numbers;
        config.countNumber = countNumber;
        config.unCountTimeMax = djsTime;
        config.saveDecimal = saveDecimal;
        ConfigHelper.saveConfig(this, config);
    }

    /**
     * 过滤掉一些不能出现的情况.如editText为空
     */
    private boolean lostSome() {
        int size = mCountNumberSeeBar.getProgress();
        if (size <= 1) {
            Toast.makeText(this, R.string.ts_min, Toast.LENGTH_SHORT).show();
            return false;
        }
        int[] number = getNumber(1);
        int[] number2 = getNumber(2);
        if (number == null || number2 == null) {
            Toast.makeText(this, R.string.veal_null, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (number[0] >= number[1] || number2[0] >= number2[1]) {
            Toast.makeText(this, R.string.select_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mDjsEdit.isShown()) {
            String time = mDjsEdit.getText().toString();
            if (time.isEmpty()) {
                Toast.makeText(this, R.string.select_un_time, Toast.LENGTH_SHORT).show();
                return false;
            }
            int djsTime = Integer.valueOf(time);
            if (djsTime < 5) {
                Toast.makeText(this, R.string.time_d, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (mCCEditRoot.isShown()) {
            String saveDecimalText = mCCEdit.getText().toString();
            if (saveDecimalText.isEmpty()) {
                Toast.makeText(this, R.string.settings_save_decimal, Toast.LENGTH_SHORT).show();
                return false;
            }
            int saveDecimal = Integer.valueOf(saveDecimalText);
            if (saveDecimal >= 5 || saveDecimal < 0) {
                Toast.makeText(this, R.string.save_decimal_max, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private int getIndexFromRadioButton(RadioGroup modelGroup) {
        int checkId = modelGroup.getCheckedRadioButtonId();
        //return TypeModel.class veal
        if (checkId == R.id.main_radio_jj || checkId == R.id.main_radio_js)
            return 1;
        if (checkId == R.id.main_radio_cc || checkId == R.id.main_radio_djs)
            return 2;
        if (checkId == R.id.main_radio_all || checkId == R.id.main_radio_bjs)
            return 3;
        return 1;
    }

    public int[] getNumber(int i) {
        String text1 = null;
        String text2 = null;
        if (i == 1) {
            text1 = mNumberFromEditText.getText().toString().trim();
            text2 = mNumberToEditText.getText().toString().trim();
        } else if (i == 2) {
            text1 = mNumber2FromEditText.getText().toString().trim();
            text2 = mNumber2ToEditText.getText().toString().trim();
        }
        if (text1.isEmpty() || text2.isEmpty()) {
            return null;
        }
        return new int[]{Integer.valueOf(text1), Integer.valueOf(text2)};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_phb:
                //打开排行榜界面
                Intent intent = new Intent(this, PHActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_main_about:
                //打开关于界面
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
