package com.toolkit.id;

import java.util.UUID;

/**
 * Created by shenke on 2019/1/3.
 */
public class IdUtil {

    private IdUtil(){

    }

    public static String uuid(){
        return UUID.randomUUID().toString();
    }

}
