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

    public static void main(String[] args) {
        for(int i = 1; i <= 10; i ++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String id = IdUtil.uuid();
                    Long timeout = 1L;
                    try {
                        boolean isLock = JedisUtil.lock(id, timeout);
                        if(isLock){
                            System.out.printf("%s get lock success%n", Thread.currentThread().getName());
                            Thread.sleep(1000);
                        } else {
                            System.out.printf("%s get lock failure%n", Thread.currentThread().getName());
                        }
                    } catch (InterruptedException e) {
                        JedisUtil.unlock(id);
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
