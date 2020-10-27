package com.liuchang.demo.common.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 10:21 2020/9/17
 * @ Description：
 * @ Modified By：
 */
public class MybatisUil {
    public static void main(String[] args) throws IOException {
        String resource = "org/mybatis/example/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //sqlSession.getMapper();
    }
}
