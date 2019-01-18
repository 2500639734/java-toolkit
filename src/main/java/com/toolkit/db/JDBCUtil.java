package com.toolkit.db;

import com.toolkit.array.ArrayUtil;
import com.toolkit.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenke on 2019/1/15.
 */
@Slf4j
public final class JDBCUtil {

    private JDBCUtil(){}

    /**
     * 打开数据库连接
     * @param driver
     * @param url
     * @param username
     * @param password
     * @return
     */
    public static Connection openConnection(String driver, String url, String username, String password) {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            log.info("打开数据库连接,conn = [{}]", connection);
        } catch (ClassNotFoundException e) {
            log.error("加载类驱动失败,Exception = [{}]", e);
        } catch (SQLException e) {
            log.error("获取连接失败,Exception = [{}]", e);
        }
        return connection;
    }

    /**
     * 打开数据库连接
     * @param dbConfig
     * @return
     */
    public static Connection openConnection(DbConfig dbConfig) {
        return openConnection(dbConfig.getDriver(), dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
    }

    /**
     * 关闭数据库连接
     * @param connection
     */
    public static void colseConnection(Connection connection) {
        closeConnection(connection);
    }

    /**
     * 关闭数据库连接
     * @param connection
     * @param preparedStatement
     */
    public static void colseConnection(Connection connection, PreparedStatement preparedStatement) {
        closePreparedStatement(preparedStatement);
        closeConnection(connection);
    }

    /**
     * 关闭数据库连接
     * @param connection
     * @param preparedStatement
     * @param resultSet
     */
    public static void colseConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        closeResultSet(resultSet);
        closePreparedStatement(preparedStatement);
        closeConnection(connection);
    }

    /**
     * 关闭数据库连接
     * @param connection
     */
    public static void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
                log.info("关闭数据库连接,conn = [{}]", connection);
            } catch (SQLException e) {
                log.error("关闭数据库连接失败,Exception = [{}]", e);
            }
        }
    }

    /**
     * 关闭preparedStatement
     * @param preparedStatement
     */
    public static void closePreparedStatement(PreparedStatement preparedStatement) {
        if(preparedStatement != null) {
            try {
                preparedStatement.close();
                log.info("关闭数据库sql执行对象,preparedStatement = [{}]", preparedStatement);
            } catch (SQLException e) {
                log.error("关闭数据库sql执行对象失败,Exception = [{}]", e);
            }
        }
    }

    /**
     * 关闭resultSet
     * @param resultSet
     */
    public static void closeResultSet(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
                log.info("关闭数据库查询结果集对象,resultSet = [{}]", resultSet);
            } catch (SQLException e) {
                log.error("关闭数据库查询结果集对象失败,Exception = [{}]", e);
            }
        }
    }

    /**
     * 执行sql
     * @param dbConfig
     * @param clazz
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> selectSql(DbConfig dbConfig, Class<T> clazz, String sql, Object... params){
        if(dbConfig == null){
            log.error("执行sql失败,数据库配置为空,dbConfig = [{}]", dbConfig);
            return null;
        }
        Connection connection = openConnection(dbConfig);
        if(connection == null){
            log.error("执行sql失败,连接为空,connection = [{}]", connection);
            return null;
        }
        return selectSql(connection, clazz, sql, params);
    }

    /**
     * 执行sql
     * @param connection
     * @param clazz
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> selectSql(Connection connection, Class<T> clazz, String sql, Object... params){
        if(connection == null){
            log.error("执行sql失败,连接为空,connection = [{}]", connection);
            return null;
        }

        if(SqlUtil.isNotSelectSql(sql)){
            log.error("执行sql失败,不是查询sql,sql = [{}]", sql);
            return null;
        }

        if(clazz == null){
            log.error("执行sql失败,实体类为空,clazz = [{}]", clazz);
            return null;
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if(preparedStatement == null){
                log.error("执行sql失败,preparedStatement为空,preparedStatement = [{}]", preparedStatement);
                return null;
            }

            if(ArrayUtil.isNotEmpty(params)){
                SqlUtil.settingSqlParams(preparedStatement, params);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet == null){
                log.error("执行sql失败,resultSet为空,resultSet = [{}]", resultSet);
                return null;
            }

            List<T> resultList = new ArrayList<>();

            while (resultSet.next()){
                T obj = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields){
                    String fieldName = field.getName();
                    String columnName = StringUtil.lineToHump(fieldName);
                    Object columnValue = resultSet.getObject(columnName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, clazz);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.setAccessible(true);
                    method.invoke(obj, columnValue);
                }
                resultList.add(obj);
            }

            return resultList;
        } catch (SQLException e) {
            log.error("执行sql失败,Exception = [{}]", e);
        } catch (IntrospectionException e) {
            log.error("执行sql失败,Exception = [{}]", e);
        } catch (IllegalAccessException e) {
            log.error("执行sql失败,Exception = [{}]", e);
        } catch (InstantiationException e) {
            log.error("执行sql失败,Exception = [{}]", e);
        } catch (InvocationTargetException e) {
            log.error("执行sql失败,Exception = [{}]", e);
        }
        return null;
    }

}
