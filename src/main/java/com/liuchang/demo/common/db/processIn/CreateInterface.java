package com.liuchang.demo.common.db.processIn;

import java.util.HashMap;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 12:44 2020/9/27
 * @ Description：
 * @ Modified By：
 */
public interface CreateInterface {
    public String CreateContent();
    //拿自定义包名
    public String getPackageStr();

    //拿import信息
    public String getImportStr();

    //拿类的头信息
    public String getClassheadStr(String tables);
    //拿类名
    public String getClassName(String tables);
    //拿属性信息
    public String getFields();
    //拿方法信息
    public String getMethods();

}
