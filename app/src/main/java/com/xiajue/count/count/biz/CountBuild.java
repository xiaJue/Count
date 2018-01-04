package com.xiajue.count.count.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.xiajue.count.count.constant.TypeModel.COUNT_ADDITION;
import static com.xiajue.count.count.constant.TypeModel.COUNT_DIVISION;
import static com.xiajue.count.count.constant.TypeModel.COUNT_MULTIPLICATION;
import static com.xiajue.count.count.constant.TypeModel.COUNT_SUBTRACTION;

/**
 * Created by xiaJue on 2017/12/22.
 */

public class CountBuild {
    private int[] mNumbers, mNumbers2;
    private int mType, mModel, mCountNumber, mSaveDecimal;

    public CountBuild initialize(int[] numbers, int[] numbers2, int type, int model, int
            countNumber, int blxs) {
        mNumbers = numbers;
        mNumbers2 = numbers2;
        mType = type;
        mModel = model;
        mCountNumber = countNumber;
        this.mSaveDecimal = blxs;
        mRandom = new Random();
        return this;
    }

    public CountHandler build() {
        CountHandler handler = new CountHandler();
        handler.setSaveDecimal(mSaveDecimal);
        List<CountHandler.Count> counts = new ArrayList<>();
        for (int i = 0; i < mCountNumber; i++) {
            CountHandler.Count count = new CountHandler.Count();
            count.setType(getRandomType());
            int[] random = getRandom();
            count.setArgs1(random[0]);
            count.setArgs2(random[1]);
            count.countAnswer();
            counts.add(count);
        }
        handler.setCounts(counts);
        return handler;
    }

    private int[] getRandom() {
        int args1 = randomInt(mNumbers[1], mNumbers[0]);
        int args2 = randomInt(mNumbers2[1], mNumbers2[0]);
        //在此处理产生的随机数~如果需要的话
        return new int[]{args1, args2};
    }

    private int getRandomType() {
        int type = COUNT_ADDITION;
        if (mType == 1) {
            type = randomInt(1, 0) == 1 ? COUNT_ADDITION : COUNT_SUBTRACTION;
        }
        if (mType == 2) {
            type = randomInt(1, 0) == 1 ? COUNT_MULTIPLICATION : COUNT_DIVISION;
        }
        if (mType == 3) {
            type = randomInt(1, 0) == 1 ? randomInt(1, 0) == 1 ? COUNT_ADDITION :
                    COUNT_SUBTRACTION : randomInt(1, 0) == 1 ? COUNT_MULTIPLICATION :
                    COUNT_DIVISION;
        }
        return type;
    }

    private Random mRandom;

    public int randomInt(int max, int min) {
        return mRandom.nextInt(max - min + 1) + min;
    }
}
