package com.sleepy.box.util;

import java.util.Random;

/**
 * Created by gehou on 2017/9/18.
 */

public class SleepyUtil {

    public static String generatePassWord(boolean isContainLetter, boolean isContainNum, boolean isContainMark, int bitNum) {
        String pw = "";
        int type = (isContainLetter ? 4 : 0) + (isContainNum ? 2 : 0) + (isContainMark ? 1 : 0);
        char c = ' ';
        for (int i = 0; i < bitNum; i++) {
            switch (type) {
                case 1:
                    switch (generateRandomNum(0, 4)) {
                        case 0:
                            c = (char) generateRandomNum(33, 48);
                            break;
                        case 1:
                            c = (char) generateRandomNum(58, 65);
                            break;
                        case 2:
                            c = (char) generateRandomNum(91, 97);
                            break;
                        case 3:
                            c = (char) generateRandomNum(123, 127);
                            break;
                    }
                    break;
                case 2:
                    c = (char) generateRandomNum(48, 58);
                    break;
                case 3:
                    switch (generateRandomNum(0, 5)) {
                        case 0:
                            c = (char) generateRandomNum(33, 48);
                            break;
                        case 1:
                            c = (char) generateRandomNum(58, 65);
                            break;
                        case 2:
                            c = (char) generateRandomNum(91, 97);
                            break;
                        case 3:
                            c = (char) generateRandomNum(123, 127);
                            break;
                        case 4:
                            c = (char) generateRandomNum(48, 58);
                            break;
                    }
                    break;
                case 4:
                    switch (generateRandomNum(0, 2)) {
                        case 0:
                            c = (char) generateRandomNum(65, 91);
                            break;
                        case 1:
                            c = (char) generateRandomNum(97, 123);
                            break;
                    }
                    break;
                case 5:
                    switch (generateRandomNum(0, 6)) {
                        case 0:
                            c = (char) generateRandomNum(65, 91);
                            break;
                        case 1:
                            c = (char) generateRandomNum(97, 123);
                            break;
                        case 2:
                            c = (char) generateRandomNum(33, 48);
                            break;
                        case 3:
                            c = (char) generateRandomNum(58, 65);
                            break;
                        case 4:
                            c = (char) generateRandomNum(91, 97);
                            break;
                        case 5:
                            c = (char) generateRandomNum(123, 127);
                            break;
                    }
                    break;
                case 6:
                    switch (generateRandomNum(0, 3)) {
                        case 0:
                            c = (char) generateRandomNum(65, 91);
                            break;
                        case 1:
                            c = (char) generateRandomNum(97, 123);
                            break;
                        case 2:
                            c = (char) generateRandomNum(48, 58);
                            break;
                    }
                    break;
                case 7:
                    c = (char) generateRandomNum(33, 127);
                    break;

            }
            pw += c;
        }
        return pw;
    }

    public static int generateRandomNum(int start, int end) {
        //范围是[start,end)
        int result = 0;
        Random random = new Random();
        result = random.nextInt(end - start) + start;
        return result;
    }

    public static EncryptUtil encryptUtil= new EncryptUtil("6rtu456dfh4xfbd8ujrtu4eya8ry1f354j8ygi4uokdhjm4nxc","utf-8");

}
