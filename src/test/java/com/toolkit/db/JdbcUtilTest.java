package com.toolkit.db;

import org.junit.*;

import java.sql.Connection;
import java.util.List;

/**
 * Created by shenke on 2019/1/18.
 */
public class JdbcUtilTest {

    private DbConfig dbConfig = null;

    private Connection connection = null;

    @Before
    public void openConnectionTest(){
        dbConfig = DbConfig.settingDbConfig("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/iot?characterEncoding=utf-8&allowMultiQueries=true", "root", "root");
    }

    @Test
    public void selectTest(){
        String sql = "select * from t_user where id = ? and name = ? ";
        List<TUser> tUserList = JDBCUtil.selectSql(dbConfig, TUser.class, sql, new Object[]{1, "张三"});
        System.out.println(tUserList);
    }

    @After
    public void closeConnectionTest(){
        JDBCUtil.closeConnection(connection);
    }


}
