package com.toolkit.redis;

import com.toolkit.id.IdUtil;
import org.junit.Test;

/**
 * Created by shenke on 2019/1/3.
 */
public class JedisUtilTest {

    @Test
    public void redisLockTest(){
        for(int i = 1; i <= 10; i ++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String id = IdUtil.uuid();
                    Long timeout = 3L;
                    try {
                        boolean isLock = JedisUtil.lock(id, timeout);
                        if(isLock){
                            System.out.printf("thread-%d get lock success");
                        } else {
                            System.out.printf("thread-%d get lock failure");
                        }
                    } finally {
                        JedisUtil.unlock(id);
                    }
                }
            });
            thread.setName(String.format("thread-%d", i));
            thread.start();
        }
    }

}
