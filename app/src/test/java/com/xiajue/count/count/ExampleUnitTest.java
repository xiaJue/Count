package com.xiajue.count.count;

import org.junit.Test;

import java.text.NumberFormat;
import java.util.Random;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final int COUNT_ADDITION = 210;
    private static final int COUNT_SUBTRACTION = 211;
    private static final int COUNT_MULTIPLICATION = 212;
    private static final int COUNT_DIVISION = 213;
    private int mType;

    @Test
    public void testRandom() throws Exception {
//        mType = 3;
//        for (int i = 0; i < 10; i++) {
//            System.out.println(getType() + "---");
//        }

        int args1 = 10;
        int args2 = 7;
        float answer  = ((float)args1 / (float)args2);
        System.out.println(args1 + "----" + args2 + "--==" + answer);
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        answer = Float.valueOf(nf.format(answer));
        System.out.println(args1 + "----" + args2 + "--==" + answer);
    }

    private int getType() {
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

    public int randomInt(int max, int min) {
        return new Random().nextInt(max - min + 1) + min;
    }
}