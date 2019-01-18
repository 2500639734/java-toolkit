package com.toolkit.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shenke on 2019/1/18.
 */
public class StringUtil {

    private StringUtil(){

    }

    public static boolean isEmpty(String str){
        return str.trim() == null || str.trim().equals("");
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 下划线转驼峰
     * @param str
     * @return
     */
    public static String lineToHump(String str){
        if(isEmpty(str)){
            return str;
        }
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str.toLowerCase());
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    public static String humpToLine(String str){
        if(isEmpty(str)){
            return str;
        }
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        String hump = sb.toString();
        return hump.substring(1, sb.toString().length());
    }

}
