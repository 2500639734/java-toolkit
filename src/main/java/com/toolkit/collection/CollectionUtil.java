package com.toolkit.collection;

import java.util.Collection;

/**
 * Created by shenke on 2019/1/18.
 * 集合工具类
 */
public class CollectionUtil {

    private CollectionUtil(){

    }

    public static <E> boolean isEmpty(Collection<E> collection){
        return collection == null || collection.size() == 0;
    }

    public static <E> boolean isNotEmpty(Collection<E> collection){
        return !isEmpty(collection);
    }

}
