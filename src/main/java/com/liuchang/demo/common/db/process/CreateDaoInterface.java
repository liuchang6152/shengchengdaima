package com.liuchang.demo.common.db.process;

import com.liuchang.demo.common.db.StringChangeUtil;
import com.liuchang.demo.common.db.TypeChangeUtil;
import com.liuchang.demo.common.db.processIn.CreateInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 11:20 2020/9/27
 * @ Description：
 * @ Modified By：
 */
@Component
public class CreateDaoInterface implements CreateInterface {
    //表名
    @Value("${table.name}")
    private String tableNames;
    //包名
    @Value("${daoInterface.package.name}")
    private String packageName;
    //导入包名的字符串
    @Value("${daoInterface.import.string}")
    private String importName;
    //类名后缀
    @Value("${daoInterface.suffix.string}")
    private String classSuffix;
    //类注解字符串
    @Value("${daoInterface.class.annotate.string}")
    private String classAnnotate;

    @Value("${pojo.package.name}")
    private String poPackageName;



    @Autowired
    private CreatePo createPo;

    public CreateDaoInterface() {
    }

    private String CreateToDaoInterface() {
        String result="";
        result=getPackageStr() + "\n"
                + getImportStr() + "\n"
                + getClassheadStr(tableNames) + "\n";

        result=result + ("}");
        return result;
    }

    @Override
    public String CreateContent() {
        return CreateToDaoInterface();
    }

    //拿自定义包名
    public String getPackageStr() {
        return "package "+this.packageName+";";
    }

    //拿import信息
    public String getImportStr() {
        //CreatePo createPo = new CreatePo();
        return this.importName+"\n"+"import "+poPackageName+"."+ createPo.getClassName(tableNames)+";";
    }

    //拿类的头信息
    public String getClassheadStr(String tables) {
        return classAnnotate + "\r\n" + "public interface " + StringChangeUtil.classMysqlToJava(tables) + classSuffix +
                " extends JpaRepository<"+createPo.getClassName(tableNames)+ ", Long> "+
                "{";

    }

    public String getClassName(String tables) {
        return StringChangeUtil.classMysqlToJava(tables) + classSuffix;
    }

    @Override
    public String getFields() {
        return "";
    }

    @Override
    public String getMethods() {
        return "";
    }


}
