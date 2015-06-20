package com.awant.lion.tools;

/**
 * Created by dexter on 2015/6/17.
 */
public class Tools {

    public static String strLen(String str, int len) {
        while (str.length() < len) {
            str = "0" + str;
        }
        return str;
    }

    public static String strLen(int n, int len) {
        return strLen(String.valueOf(n), len);
    }

    public static String floatToInt(String strFloat) {
        float tempFloat = Float.valueOf(strFloat);
        int tempInt = (int) tempFloat;
        return String.valueOf(tempInt);
    }
}
