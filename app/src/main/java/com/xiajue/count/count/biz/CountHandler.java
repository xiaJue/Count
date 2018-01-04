package com.xiajue.count.count.biz;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;

import static com.xiajue.count.count.constant.TypeModel.COUNT_ADDITION;
import static com.xiajue.count.count.constant.TypeModel.COUNT_DIVISION;
import static com.xiajue.count.count.constant.TypeModel.COUNT_MULTIPLICATION;
import static com.xiajue.count.count.constant.TypeModel.COUNT_SUBTRACTION;
import static com.xiajue.count.count.constant.TypeModel.getTypeKey;

/**
 * Created by xiaJue on 2017/12/22.
 * 管理算数类Count
 */

public class CountHandler implements Serializable {
    private List<Count> mCounts;
    private int mIndex = 0;
    public int mType, mModel, mCountNumber;
    public int[] mNumbers;
    public long mUseTime;//用户答题使用的时间
    public long mMaxTime;//倒计时时间

    public CountHandler() {
    }

    public CountHandler(int type, int model, int countNumber, int[] numbers, long useTime, long
            maxTime) {
        mType = type;
        mModel = model;
        mCountNumber = countNumber;
        mNumbers = numbers;
        mUseTime = useTime;
        mMaxTime = maxTime;
    }

    public void setSaveDecimal(int blxs) {
        Count.saveDecimal = blxs;
    }

    public void setCounts(List<Count> counts) {
        mCounts = counts;
    }

    public List<Count> getCounts() {
        return mCounts;
    }

    public Count getSubject(int index) {
        return mCounts.get(index);
    }

    public Count nextCount() {
        return mCounts.get(mIndex++);
    }

    private Count backSubject() {
        return mCounts.get(--mIndex);
    }

    public int getNowIndex() {
        return mIndex;
    }

    public int getCorrectCount() {
        int count = 0;
        for (Count c : mCounts) {
            if (c.isCorrect())
                count++;
        }
        return count;
    }

    public static class Count implements Serializable {
        private String subject = "";
        private int args1, args2;
        private int type;
        private float answer;
        private float userAnswer = -1;//用户回答的
        private static int saveDecimal;//保留小数数量

        public float getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(float userAnswer) {
            this.userAnswer = userAnswer;
        }

        public String getSubject() {
            if (subject.isEmpty()) {
                subject = args1 + getTypeKey(type) + args2 + " = ";
            }
            return subject;
        }

        public float getAnswer() {
            return answer;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getArgs1() {
            return args1;
        }

        public int getArgs2() {
            return args2;
        }

        public void setArgs1(int args1) {
            this.args1 = args1;
        }

        public void setArgs2(int args2) {
            this.args2 = args2;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isCorrect() {
            return answer == userAnswer;
        }

        public void countAnswer() {
            switch (type) {
                case COUNT_ADDITION:
                    answer = args1 + args2;
                    break;
                case COUNT_SUBTRACTION:
                    //减法不希望出现负数
                    if (args2 > args1) {
                        int temp;
                        temp = args1;
                        args1 = args2;
                        args2 = temp;
                    }
                    answer = args1 - args2;
                    break;
                case COUNT_MULTIPLICATION:
                    answer = args1 * args2;
                    break;
                case COUNT_DIVISION:
                    //0不能被除
                    if (args2 == 0) {
                        int temp;
                        temp = args1;
                        args1 = args2;
                        args2 = temp;
                    }
                    answer = ((float) args1 / (float) args2);
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(saveDecimal);
                    answer = Float.valueOf(nf.format(answer));//四舍五入结果保留两位小数
                    break;
            }
        }

        @Override
        public boolean equals(Object obj) {
            return answer == ((Count) obj).answer;
        }
    }
}
