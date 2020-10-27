package com.liuchang.demo.common.db;

import java.util.Arrays;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 15:48 2020/9/25
 * @ Description：
 * @ Modified By：
 */
public class StringChangeUtil {
    //将字段名转换成java属性名 驼峰方式
    public static String fieldMysqlToJava(String mysqlName){
        String result = "";
        if(mysqlName.contains("_")){
            String[] mysqlNameArray = mysqlName.split("_");
            for (int i=0; i < mysqlNameArray.length; i++) {
                String fieldStr = "";
                 if(i!=0){
                     fieldStr = mysqlNameArray[i].toLowerCase().replace(mysqlNameArray[i].toLowerCase().substring(0,1),mysqlNameArray[i].substring(0,1).toUpperCase());
                 }
                 else{
                     fieldStr=  mysqlNameArray[i].toLowerCase();
                 }
                result = result+fieldStr;
            }
            return result;
        }
        return mysqlName;
    }
    //将表名转换成java类名
    public static String classMysqlToJava(String tebleName){
        String result = "";
        if(tebleName.contains("_")){
            result =Arrays.asList(tebleName.split("_")).get(tebleName.split("_").length-1);
            result = result.toLowerCase().replaceFirst(result.toLowerCase().substring(0,1),result.toLowerCase().substring(0,1).toUpperCase());
        }
        return result;
    }


}
