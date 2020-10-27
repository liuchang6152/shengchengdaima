package com.liuchang.demo.common.db.process;

import com.liuchang.demo.common.db.StringChangeUtil;
import com.liuchang.demo.common.db.processIn.CreateInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 13:40 2020/9/27
 * @ Description：生成业务逻辑层接口
 * @ Modified By：
 */
@Component
public class CreateServiceInterface implements CreateInterface {
    //表名
    @Value("${table.name}")
    private String tableNames;
    //包名
    @Value("${serviceInterface.package.name}")
    private String packageName;
    //导入包名的字符串
    @Value("${serviceInterface.import.string}")
    private String importName;
    //类名后缀
    @Value("${serviceInterface.suffix.string}")
    private String classSuffix;
    @Autowired
    private CreatePo createPo;
    @Autowired
    private CreateVo createVo;


    @Override
    public String CreateContent() {
        return CreateToServiceInterface();
    }

    public String CreateToServiceInterface() {
        String result="";
        result=getPackageStr() + "\n"
                + getClassheadStr(tableNames) + "\n"
                + getFields()+"\n"
                + getMethods()+"\n";
        result=result + ("}");
        return result;
    }

    @Override
    public String getPackageStr() {
        return "package "+this.packageName+";";
    }

    @Override
    public String getImportStr() {
        return this.importName+
                "\n"+"import "+createPo.getPackageStr()+"."+ createPo.getClassName(tableNames)+";"+
                "\n"+"import "+createVo.getPackageStr()+"."+ createVo.getClassName(tableNames)+";";

    }

    @Override
    public String getClassheadStr(String tables) {
        return "public interface " + StringChangeUtil.classMysqlToJava(tables) + classSuffix + "{";
    }

    @Override
    public String getClassName(String tables) {
        return StringChangeUtil.classMysqlToJava(tables) + classSuffix;
    }

    @Override
    public String getFields() {
        return "";
    }

    @Override
    public String getMethods() {
        String addNote = "/**\n" +
                " * 新增\n" +
                " *\n" +
                " * @param entity 实体\n" +
                " * @return CommonResult\n" +
                " */";
        String addMethodContent= " CommonResult add"+StringChangeUtil.classMysqlToJava(tableNames)+
                "("+createVo.getClassName(tableNames)+" entity);";

        String deleteNote = "/**\n" +
                " * 删除\n" +
                " *\n" +
                " * @param id 主键\n" +
                " * @return CommonResult\n" +
                " */";
        String deleteMethodContent= " CommonResult delete"+StringChangeUtil.classMysqlToJava(tableNames)+
                "("+createVo.getClassName(tableNames)+" entity);";

        String updateNote = "/**\n" +
                " * 修改\n" +
                " *\n" +
                " * @param"+ createVo.getClassName(tableNames)+"\n" +
                " * @return CommonResult\n" +
                " */";
        String upodteMethodContent= " CommonResult update"+StringChangeUtil.classMysqlToJava(tableNames)+
                "("+createVo.getClassName(tableNames)+" entity);";

        String getNote = "/**\n" +
                " * 修改\n" +
                " *\n" +
                " * @param"+ createVo.getClassName(tableNames)+"\n" +
                " * @return CommonResult\n" +
                " */";
        String getMethodContent= " CommonResult getOne"+StringChangeUtil.classMysqlToJava(tableNames)+
                "("+createVo.getClassName(tableNames)+" entity);";

        return addNote+"\n"+addMethodContent+"\n"+deleteNote+"\n"+deleteMethodContent
                +"\n"+updateNote+"\n"+upodteMethodContent+"\n"+getNote+"\n"+getMethodContent;
    }


//    /**
//     * 新增企业组织机构
//     *
//     * @param entity 实体
//     * @return String
//     * @throws Exception 异常
//     * @author t-wenqi 2018年8月14日
//     */
//    CommonResult addOrg(OrgEntity entity) throws Exception;
//
//    /**
//     * 删除企业组织机构
//     *
//     * @param data 数据
//     * @return String
//     * @throws Exception 异常
//     * @author t-wenqi 2018年8月14日
//     */
//    CommonResult deleteOrg(Long[] data) throws Exception;
//
//    /**
//     * 修改企业组织机构
//     *
//     * @param entity 实体
//     * @return String
//     * @throws Exception 异常
//     * @author t-wenqi 2018年8月14日
//     */
//    CommonResult updateOrg(OrgEntity entity) throws Exception;
//
//    /**
//     * 查询单条企业组织机构
//     *
//     * @param orgID 组织ID
//     * @return ProductEntity 返回风险分析点配置
//     * @throws Exception 抛出异常
//     * @author t-wenqi 2018年8月14日
//     */
//    OrgEntity getSingleOrg(Long orgID) throws Exception;
}
