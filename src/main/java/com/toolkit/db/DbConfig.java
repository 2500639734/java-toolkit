package com.toolkit.db;

import com.toolkit.string.StringUtil;
import lombok.Data;

/**
 * Created by shenke on 2019/1/18.
 */
@Data
public class DbConfig {

    private DbConfig(String driver, String url, String username, String password){
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private String driver;

    private String url;

    private String username;

    private String password;

    public static DbConfig settingDbConfig(String driver, String url, String username, String password){
        return StringUtil.isEmpty(driver) || StringUtil.isEmpty(url) || StringUtil.isEmpty(username) || StringUtil.isEmpty(password) ?
                null : new DbConfig(driver, url, username, password);
    }

}
