package com.xiajue.count.count.constant;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.xiajue.count.count.utils.SPUtils;

/**
 * Created by xiaJue on 2017/12/26.
 */

public class ConfigHelper {
    private static final String KEY_TYPE = "TYPE";
    private static final String KEY_MODEL = "MODEL";
    private static final String KEY_NUMBER_FROM = "COUNT_NUMBER_FROM";
    private static final String KEY_NUMBER_TO = "COUNT_NUMBER_TO";
    private static final String KEY_NUMBER2_FROM = "COUNT_NUMBER2_FROM";
    private static final String KEY_NUMBER2_TO = "COUNT_NUMBER2_TO";
    private static final String KEY_COUNT_NUMBER = "COUNT_NUMBER";
    private static final String KEY_UN_COUNT_TIME_MAX = "UN_COUNT_TIME_MAX";
    private static final String KEY_BLXS = "BLXS";
    private static final int INIT_FROM = 0;//默认的范围起始数字
    private static final int INIT_TO = 100;
    private static final int INIT_COUNT_NUMBER = 20;//默认的题目数量
    private static final int INIT_UN_COUNT_TIME = 120;//默认的倒计时秒数
    public static final int INIT_COUNT_DIVISION_SAVE_DECIMAL = 1;//除法保留小数位
    public static final int ABOUT_TITLE_MAX_LENGTH = 10;
    public static final int NUMBER_MAX_INT = 10000;//选择范围不能大于或等于次数

    /**
     * 读取配置
     */
    public static Config getConfig(Context context) {
        int type = (int) SPUtils.get(context, KEY_TYPE, 1);
        int model = (int) SPUtils.get(context, KEY_MODEL, 1);
        int[] numbers = new int[2];
        numbers[0] = (int) SPUtils.get(context, KEY_NUMBER_FROM, INIT_FROM);
        numbers[1] = (int) SPUtils.get(context, KEY_NUMBER_TO, INIT_TO);
        int[] numbers2 = new int[2];
        numbers2[0] = (int) SPUtils.get(context, KEY_NUMBER2_FROM, INIT_FROM);
        numbers2[1] = (int) SPUtils.get(context, KEY_NUMBER2_TO, INIT_TO);
        int countNumber = (int) SPUtils.get(context, KEY_COUNT_NUMBER, INIT_COUNT_NUMBER);
        int unCountTimeMax = (int) SPUtils.get(context, KEY_UN_COUNT_TIME_MAX, INIT_UN_COUNT_TIME);
        int saveDecimal = (int) SPUtils.get(context, KEY_BLXS, INIT_COUNT_DIVISION_SAVE_DECIMAL);
        return new Config(type, model, numbers, numbers2, countNumber, unCountTimeMax, saveDecimal);
    }

    /**
     * 保存配置
     */
    public static void saveConfig(Context context, Config config) {
        SPUtils.put(context, KEY_TYPE, config.type);
        SPUtils.put(context, KEY_MODEL, config.model);
        SPUtils.put(context, KEY_NUMBER_FROM, config.numbers[0]);
        SPUtils.put(context, KEY_NUMBER_TO, config.numbers[1]);
        SPUtils.put(context, KEY_NUMBER2_FROM, config.numbers2[0]);
        SPUtils.put(context, KEY_NUMBER2_TO, config.numbers2[1]);
        SPUtils.put(context, KEY_COUNT_NUMBER, config.countNumber);
        SPUtils.put(context, KEY_UN_COUNT_TIME_MAX, config.unCountTimeMax);
        SPUtils.put(context, KEY_BLXS, config.saveDecimal);
    }

    public static void setFractionTextColor(TextView tv, int fraction) {
        if (fraction < 60) {
            tv.setTextColor(Color.RED);
        } else if (fraction < 90) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
    }

    public static class Config {
        public int type;
        public int model;
        public int[] numbers;
        public int[] numbers2;
        public int countNumber;
        public int unCountTimeMax;
        public int saveDecimal;

        public Config() {
        }

        public Config(int type, int model, int[] numbers, int[] numbers2, int countNumber, int
                unCountTimeMax,
                      int saveDecimal) {
            this.type = type;
            this.model = model;
            this.numbers = numbers;
            this.numbers2 = numbers2;
            this.countNumber = countNumber;
            this.unCountTimeMax = unCountTimeMax;
            this.saveDecimal = saveDecimal;
        }
    }
}
