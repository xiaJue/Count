package com.xiajue.count.count.constant;

/**
 * Created by xiaJue on 2017/12/25.
 */

public class TypeModel {
    public static final int COUNT_ADDITION = 210;//加法
    public static final int COUNT_SUBTRACTION = 211;//减法
    public static final int COUNT_MULTIPLICATION = 212;//乘法
    public static final int COUNT_DIVISION = 213;//除法

    public static final int MODEL_COUNT_TIME = 1;//计时模式
    public static final int MODEL_UN_COUNT_TIME = 2;//倒计时模式
    public static final int MODEL_NO_COUNT_TIME = 3;//不计时

    public static final int GET_MODEL = 341;
    public static final int GET_TYPE = 342;

    public static String getStringFromInt(int integer, int model) {
        if (model == GET_MODEL) {
            switch (integer) {
                case 1:
                    return "计时";
                case 2:
                    return "不计时";
                case 3:
                    return "倒计时";
            }
        } else if (model == GET_TYPE) {
            switch (integer) {
                case 1:
                    return "加减";
                case 2:
                    return "乘除";
                case 3:
                    return "综合";
            }
        }
        return null;
    }

    public static String getTypeKey(int type) {
        switch (type) {
            case COUNT_ADDITION:
                return "+";
            case COUNT_SUBTRACTION:
                return "-";
            case COUNT_MULTIPLICATION:
                return "×";
            case COUNT_DIVISION:
                return "÷";
        }
        return "ooxx";
    }

}
