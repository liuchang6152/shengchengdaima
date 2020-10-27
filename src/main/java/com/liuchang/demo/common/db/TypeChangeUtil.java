package com.liuchang.demo.common.db;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 14:48 2020/9/25
 * @ Description：
 * @ Modified By：
 */
public class TypeChangeUtil {
    public static Map<String,String> map = new HashMap<>();
    static{
        map.put("VARCHAR","java.lang.String");
        map.put("CHAR","java.lang.String");
        map.put("BLOB","java.lang.byte[]");
        map.put("INTEGER UNSIGNED","java.lang.Long");
        map.put("TINYINT UNSIGNED","java.lang.Integer");
        map.put("SMALLINT UNSIGNED","java.lang.Integer");
        map.put("MEDIUMINT UNSIGNED","java.lang.Integer");
        map.put("TINYINT","java.lang.Integer");
        map.put("SMALLINT","java.lang.Integer");
        map.put("MEDIUMINT","java.lang.Integer");
        map.put("BIT","java.lang.Boolean");
        map.put("BIGINT UNSIGNED","java.lang.Long");
        map.put("BIGINT","java.lang.Long");
        map.put("INT","java.lang.Integer");
        map.put("FLOAT","java.lang.String");
        map.put("DOUBLE","java.lang.Double");
        map.put("DECIMAL","java.math.BigDecimal");
        map.put("PK (INTEGER UNSIGNED)","java.lang.Long");
        map.put("DATE","java.sql.Date");
        map.put("TIME","java.sql.Time");
        map.put("DATETIME","java.util.Date");
        map.put("TIMESTAMP","java.sql.Timestamp");
        map.put("YEAR","java.sql.Date");
    }


}
