package com.toolkit.db;

import com.toolkit.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by shenke on 2019/1/18.
 */
@Slf4j
public class SqlUtil {

    /**
     * select查询语句起始
     */
    private static final String SELECT_SQL_VALID = "select";

    private SqlUtil(){

    }

    /**
     * 是否是查询语句
     * @param sql
     * @return
     */
    public static boolean isSelectSql(String sql){
        return StringUtil.isEmpty(sql) ? false : sql.trim().toLowerCase().startsWith(SELECT_SQL_VALID);
    }

    /**
     * 是否不是查询语句
     * @param sql
     * @return
     */
    public static boolean isNotSelectSql(String sql){
        return !isSelectSql(sql);
    }

    /**
     * 是否包含占位符
     * @param sql
     * @return
     */
    public static boolean isContainsPlaceholder(String sql){
        return StringUtil.isEmpty(sql) ? false : sql.trim().contains("?");
    }

    /**
     * 是否不包含占位符
     * @param sql
     * @return
     */
    public static boolean isNotContainsPlaceholder(String sql){
        return !isContainsPlaceholder(sql);
    }

    /**
     * 设置sql参数,失败返回原sql
     * 直接将参数转换替代占位符
     * @param sql
     * @param params
     * @return
     */
    public static String settingSqlParams(String sql, Object... params){
        if(isNotSelectSql(sql)){
            log.error("设置sql参数失败,不是sql语句. sql = [{}]", sql);
            return sql;
        }

        if(isNotContainsPlaceholder(sql)){
            log.error("设置sql参数失败,没有占位符设置参数. sql = [{}]", sql);
            return sql;
        }

        String[] charArr = sql.trim().split("\\?");

        if(charArr.length != params.length){
            log.error("设置sql参数失败,sql语句和参数不匹配. sql = [{}], params = [{}]", sql, Arrays.toString(params));
            return sql;
        }

        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0, len = charArr.length; i < len; i ++){
            String character = charArr[i];
            stringBuffer.append(character).append(params[i]);
        }
        return stringBuffer.toString();
    }

    /**
     * 设置sql语句,失败打印异常
     * 通过preparedStatement形参设置
     * @param preparedStatement
     * @param params
     */
    public static void settingSqlParams(PreparedStatement preparedStatement, Object... params){
        try {
            for(int i = 0, len = params.length; i < len; i ++){
                preparedStatement.setObject(i + 1, params[i]);
            }
        } catch (SQLException e) {
            log.error("设置sql参数失败,params = [{}], Exception = [{}]", params, e);
        }

    }

}
