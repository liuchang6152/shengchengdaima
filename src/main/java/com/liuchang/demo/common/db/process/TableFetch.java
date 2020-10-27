package com.liuchang.demo.common.db.process;

import com.liuchang.demo.common.db.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 9:36 2020/9/27
 * @ Description：获取某表的信息
 * @ Modified By：
 */
@Component
public class TableFetch {

    private static final String SQL="SELECT * FROM ";// 数据库操作

    private final static Logger LOGGER=LoggerFactory.getLogger(DBUtil.class);



    /**
     * 获取数据库下的所有表名
     */
    public static List<String> getTableNames() {
        List<String> tableNames=new ArrayList<>();
        Connection conn=DbConnect.getConnection();
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
                DbConnect.closeConnection(conn);
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
        Connection conn=DbConnect.getConnection();
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
                    DbConnect.closeConnection(conn);
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
        Connection conn=DbConnect.getConnection();
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
                    DbConnect.closeConnection(conn);
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
        Connection conn=DbConnect.getConnection();
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
                    DbConnect.closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }
}
