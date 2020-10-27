package com.liuchang.demo.common.db.process;

import com.liuchang.demo.common.db.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 9:34 2020/9/27
 * @ Description：连接数据库
 * @ Modified By：
 */
@Component
public class DbConnect {

    private static final String URL="jdbc:mysql://101.200.210.59:3306/ecs_uat?"
            + "user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";
    private static final String DRIVER="com.mysql.cj.jdbc.Driver";
    private final static Logger LOGGER=LoggerFactory.getLogger(DBUtil.class);

    private static final String USERNAME="root";
    private static final String PASSWORD="123456";

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
}
