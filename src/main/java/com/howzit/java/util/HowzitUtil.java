package com.howzit.java.util;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/10/14
 * Time: 10:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class HowzitUtil {

    private static final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public static String getAlphaNumeric(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int ndx = (int) (Math.random() * ALPHA_NUM.length());
            sb.append(ALPHA_NUM.charAt(ndx));
        }
        return sb.toString();
    }

    public static String getTempPassword(){
        return getAlphaNumeric(8);
    }


}
