package com.toolkit.array;

/**
 * Created by shenke on 2019/1/18.
 */
public class ArrayUtil {

    private ArrayUtil(){

    }

    /**
     * 数组是否为空
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T... t){
        return t == null || t.length == 0;
    }

    /**
     * 数组是否不为空
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isNotEmpty(T... t){
        return !isEmpty(t);
    }

}
