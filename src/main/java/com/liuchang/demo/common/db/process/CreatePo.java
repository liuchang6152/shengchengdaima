package com.liuchang.demo.common.db.process;

import com.liuchang.demo.common.db.StringChangeUtil;
import com.liuchang.demo.common.db.TypeChangeUtil;
import com.liuchang.demo.common.db.processIn.CreateInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 9:41 2020/9/27
 * @ Description：创建持久层实体
 * @ Modified By：
 */
@Component
public class CreatePo implements CreateInterface {
    //表名
    @Value("${table.name}")
    private String tableNames;
    //包名
    @Value("${pojo.package.name}")
    private String packageName;
    //导入包名的字符串
    @Value("${pojo.import.string}")
    private String importName;
    //类名后缀
    @Value("${pojo.suffix.string}")
    private String classSuffix;
    //类注解字符串
    @Value("${pojo.class.annotate.string}")
    private String classAnnotate;

    private String CreateToPo() {
        String result="";
        result=getPackageStr() + "\n"
                + getImportStr() + "\n"
                + getClassheadStr(tableNames) + "\n"
                + getFields()+"\n"
                + getMethods()+"\n";
        result=result + ("}");
        return result;
    }

    //拿自定义包名
    public String getPackageStr() {
        return "package "+this.packageName+";";
    }

    //拿import信息
    public String getImportStr() {
        return this.importName;
    }

    //拿类的头信息
    public String getClassheadStr(String tables) {
        return classAnnotate + "\r\n" + "public class " + StringChangeUtil.classMysqlToJava(tables) + classSuffix + "{";
    }

    public String getClassName(String tables) {
        return StringChangeUtil.classMysqlToJava(tables) + classSuffix;
    }

    @Override
    public String CreateContent() {
        return CreateToPo();
    }

    @Override
    public String getFields() {
        String result="";
        List<String> columnNames=TableFetch.getColumnNames(tableNames);
        List<String> columnTypes=new ArrayList<>();

        TableFetch.getColumnTypes(tableNames).stream().forEach(mysqlColum -> {
            columnTypes.add(TypeChangeUtil.map.get(mysqlColum));
        });
        List<String> columnComments=TableFetch.getColumnComments(tableNames);
        for (int i=0; i < columnNames.size(); i++) {
            HashMap<String, String> map=new HashMap<>();
            map.put("name", columnNames.get(i));
            map.put("type", columnTypes.get(i));
            map.put("comment", columnComments.get(i));
            result=result + getPriFields(map) + "\n";
        }
        return result;
    }

    @Override
    public String getMethods() {
        String result="";
        List<String> columnNames=TableFetch.getColumnNames(tableNames);
        List<String> columnTypes=new ArrayList<>();

        TableFetch.getColumnTypes(tableNames).stream().forEach(mysqlColum -> {
            columnTypes.add(TypeChangeUtil.map.get(mysqlColum));
        });
        List<String> columnComments=TableFetch.getColumnComments(tableNames);
        for (int i=0; i < columnNames.size(); i++) {
            HashMap<String, String> map=new HashMap<>();
            map.put("name", columnNames.get(i));
            map.put("type", columnTypes.get(i));
            map.put("comment", columnComments.get(i));
            result=result + (getPriMethods(map)) + "\n";
            ;
        }
        return result;
    }

    private String getPriFields(HashMap<String, String> map) {
        String comment=" /**" + "\r\n" + map.get("comment") + "\r\n" + "*/";
        String annotate=" @Column(name=\" " + map.get("name") + "\")";
        String result=" private ";
        result=result + map.get("type") + " " + StringChangeUtil.fieldMysqlToJava(map.get("name"));
        return comment + "\n" + annotate + "\n" + result+";";
    }

    /**
     * @param map
     * @return
     */
    private String getPriMethods(HashMap<String, String> map) {
        return getGetMethod(map.get("name"), map.get("type")) + "\n" + getSetMethod(map.get("name"), map.get("type"));
    }

    private String getGetMethod(String fieldName, String typeName) {
        fieldName=StringChangeUtil.fieldMysqlToJava(fieldName);
        String result=" public ";
        result=result + typeName + " get" + fieldName.replaceFirst(String.valueOf(fieldName.charAt(0)), String.valueOf(fieldName.charAt(0)).toUpperCase()) +
                "(){\n" + "  return " + fieldName + ";" + "\n" + " }";
        return result;
    }

    private String getSetMethod(String fieldName, String typeName) {
        fieldName=StringChangeUtil.fieldMysqlToJava(fieldName);
        String result=" public void";
        result=result + "  set" + fieldName.replaceFirst(String.valueOf(fieldName.charAt(0)), String.valueOf(fieldName.charAt(0)).toUpperCase()) +
                "(" + typeName + " " + fieldName + "){\n" + "  this." + fieldName + " = " + fieldName + ";" + "\n" + " }";
        return result;
    }

}
