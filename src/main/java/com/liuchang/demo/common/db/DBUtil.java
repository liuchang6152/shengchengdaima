package com.liuchang.demo.common.db;

import com.liuchang.demo.common.db.process.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 17:06 2020/9/16
 * @ Description：
 * @ Modified By：
 */
@Component
public class DBUtil implements ApplicationRunner {
    private static final String URL="jdbc:mysql://101.200.210.59:3306/ecs_uat?"
            + "user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";
    private static final String DRIVER="com.mysql.cj.jdbc.Driver";
    private final static Logger LOGGER=LoggerFactory.getLogger(DBUtil.class);

    private static final String USERNAME="root";
    private static final String PASSWORD="123456";

    private static final String SQL="SELECT * FROM ";// 数据库操作
    @Value("${table.name}")
    private String tableNames;
    @Value("${pojo.package.name}")
    private String packageName;
    @Value("${pojo.import.string}")
    private String importName;
    @Value("${pojo.suffix.string}")
    private String classSuffix;
    @Value("${pojo.class.annotate.string}")
    private String classAnnotate;
    @Autowired
    private CreateVo createVo;

    @Autowired
    private CreateDaoInterface createDaoInterface;
    @Autowired
    private CreatePo createPo;
    @Autowired
    private CreateServiceInterface createServiceInterface;

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn=null;
        try {
            conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get connection failure", e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }

    /**
     * 获取数据库下的所有表名
     */
    public static List<String> getTableNames() {
        List<String> tableNames=new ArrayList<>();
        Connection conn=getConnection();
        ResultSet rs=null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db=conn.getMetaData();
            //从元数据中获取到所有的表名
            rs=db.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            LOGGER.error("getTableNames failure", e);
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                LOGGER.error("close ResultSet failure", e);
            }
        }
        return tableNames;
    }

    /**
     * 获取表中所有字段名称
     *
     * @param tableName 表名
     * @return
     */
    public static List<String> getColumnNames(String tableName) {
        List<String> columnNames=new ArrayList<>();
        //与数据库的连接
        Connection conn=getConnection();
        PreparedStatement pStemt=null;
        String tableSql=SQL + tableName;
        try {
            pStemt=conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd=pStemt.getMetaData();
            //表列数
            int size=rsmd.getColumnCount();
            for (int i=0; i < size; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.error("getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取表中所有字段类型
     *
     * @param tableName
     * @return
     */
    public static List<String> getColumnTypes(String tableName) {
        List<String> columnTypes=new ArrayList<>();
        //与数据库的连接
        Connection conn=getConnection();
        PreparedStatement pStemt=null;
        String tableSql=SQL + tableName;
        try {
            pStemt=conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd=pStemt.getMetaData();
            //表列数
            int size=rsmd.getColumnCount();
            for (int i=0; i < size; i++) {
                columnTypes.add(rsmd.getColumnTypeName(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.error("getColumnTypes failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnTypes close pstem and connection failure", e);
                }
            }
        }
        return columnTypes;
    }

    /**
     * 获取表中字段的所有注释
     *
     * @param tableName
     * @return
     */
    public static List<String> getColumnComments(String tableName) {
        List<String> columnTypes=new ArrayList<>();
        //与数据库的连接
        Connection conn=getConnection();
        PreparedStatement pStemt=null;
        String tableSql=SQL + tableName;
        List<String> columnComments=new ArrayList<>();//列名注释集合
        ResultSet rs=null;
        try {
            pStemt=conn.prepareStatement(tableSql);
            rs=pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }




    //拿自定义包名
    private String getPackageStr() {
        return "package "+this.packageName+";";
    }

    //拿import信息
    private String getImportStr() {
        return this.importName;
    }

    //拿类的头信息
    private String getClassheadStr(String tables) {
        return classAnnotate + "\r\n" + "public class " + StringChangeUtil.classMysqlToJava(tables) + classSuffix + "{";
    }

    private String getClassName(String tables) {
        return StringChangeUtil.classMysqlToJava(tables) + classSuffix;
    }

    private String getFields(HashMap<String, String> map) {
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
    private String getMethods(HashMap<String, String> map) {
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String result = createVo.CreateContent();
        ExportFile.ExportToFile(createVo.getClassName(tableNames)+".java",result);
        result = createDaoInterface.CreateContent();
        ExportFile.ExportToFile(createDaoInterface.getClassName(tableNames)+".java",result);
        result = createPo.CreateContent();
        ExportFile.ExportToFile(createPo.getClassName(tableNames)+".java",result);
        result = createServiceInterface.CreateContent();
        ExportFile.ExportToFile(createServiceInterface.getClassName(tableNames)+".java",result);
}

}
