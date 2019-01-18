package com.toolkit.db;

import com.toolkit.string.StringUtil;
import lombok.Data;

/**
 * Created by shenke on 2019/1/18.
 */
@Data
public class TableConfig {

    private TableConfig(String tableName){
        this.tableName = tableName;
    }

    private TableConfig(String tableName, TableConfig[] relevanceTableConfigs){
        this.tableName = tableName;
        this.relevanceTableConfigs = relevanceTableConfigs;
    }

    private String tableName;

    private TableConfig[] relevanceTableConfigs;

    public static TableConfig settingTableConfig(String tableName){
        return StringUtil.isEmpty(tableName) ? null : new TableConfig(tableName);
    }

    public static TableConfig settingTableConfig(String tableName, TableConfig[] relevanceTableConfigs){
        return StringUtil.isEmpty(tableName) ? null : new TableConfig(tableName, relevanceTableConfigs);
    }

}
