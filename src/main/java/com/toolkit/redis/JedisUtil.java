package com.toolkit.redis;

import com.toolkit.id.IdUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * Created by shenke on 2019/1/3.
 */
public class JedisUtil {

    public static final String LOCK_KEY = "lock";

    private static final String NX = "NX"; // key不存在时set
    private static final String XX = "XX"; // key已存在时set

    private static final String EX = "EX"; // 秒
    private static final String PX = "PX"; // 毫秒

    public static boolean exist(String key){
        if(StringUtils.isEmpty(key)){
            return false;
        }
        Jedis jedis = JedisPoolUtil.getJedis();
        boolean isExists = jedis.exists(key);
        JedisPoolUtil.closeJedis(jedis);
        return isExists;
    }

    public static String get(String key){
        if(StringUtils.isEmpty(key)){
            return null;
        }
        Jedis jedis = JedisPoolUtil.getJedis();
        String value = jedis.get(key);
        JedisPoolUtil.closeJedis(jedis);
        return value;
    }

    public static String setNx(String key, String value, Long timeout){
        if(StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value) && timeout != null){
            Jedis jedis = JedisPoolUtil.getJedis();
            String response = jedis.set(key, value, NX, EX, timeout);
            JedisPoolUtil.closeJedis(jedis);
            return response;
        }
        return null;
    }

    public static void del(String key){
        if(StringUtils.isNotEmpty(key)){
            Jedis jedis = JedisPoolUtil.getJedis();
            jedis.del(key);
            JedisPoolUtil.closeJedis(jedis);
        }
    }

    /**
     * redis 分布式锁获取
     * @param value
     * @param timeout
     * @return
     */
    public static boolean lock(String value, Long timeout){
        if(StringUtils.isNotEmpty(value) && timeout != null){
            return "OK".equals(setNx(LOCK_KEY, value, timeout));
        }
        return false;
    }

    /**
     * redis 分布式锁解锁
     * @param value
     */
    public static void unlock(String value){
        if(StringUtils.isNotEmpty(value)){
            String val = get(LOCK_KEY);
            if(value.equals(val)){
                del(LOCK_KEY);
            }
        }
    }

}
