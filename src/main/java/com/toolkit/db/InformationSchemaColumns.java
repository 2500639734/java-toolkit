package com.toolkit.db;

import lombok.Data;

/**
 * 数据库字段信息
 * select * from information_schema.columns where table_name='t_user' and table_schema='iot'
 * Created by shenke on 2019/1/18.
 */
@Data
public class InformationSchemaColumns {

    /**
     * 表限定符? 永远是def
     */
    private String tableCataLog;

    /**
     * 所属库
     */
    private String tableSchema;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 字段编号,从1开始
     */
    private Integer ordinalPosition;

    /**
     * 字段默认值
     */
    private Object columnDefault;

    /**
     * 字段是否可以为null,YES可以,NO不可以
     */
    private String isNullable;

    /**
     * 数据类型,如varchar，float，int
     */
    private String dataType;

    /**
     * 字段最大字符数,如varchar(32)那么就是32,适用于字符串类型
     */
    private Integer characterMaximumLength;

    /**
     * 字段最大字节数,适用于二进制类型
     */
    private Integer characterOctetLength;

    /**
     * 字段数据精度,适用于整型和浮点型
     */
    private Integer numericPrecision;

    /**
     * 字段小数位数,适用于浮点型
     */
    private Integer numericScale;

    /**
     * datetime类型和SQL-92interval类型数据库的子类型代码,datetime为0,其它为null
     */
    private String dateTimePrecision;

    /**
     * 字段字符集名称,如utf8
     */
    private String charchterSetName;

    /**
     * 字段排序规则,如utf8_general_ci不区分大小写,utf8_general_cs区分大小写
     */
    private String collationName;

    /**
     * 字段类型,如varchar(20)
     */
    private String columnType;

    /**
     * 索引类型
     */
    private String columnKey;

    /**
     * 其他信息,比如主键的auto_increment
     */
    private String extRa;

    /**
     * 权限,多个权限用逗号隔开，比如 select,insert,update,references
     */
    private String privileges;

    /**
     * 字段注释
     */
    private String columnComment;

    /**
     * 组合字段的公式
     */
    private String generationExpression;


}
