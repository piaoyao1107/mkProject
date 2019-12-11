package com.course.httpclient.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 连接数据库类
 */


public class DBHelper {


    public static void conn() {

        String URL = "jdbc:mysql://127.0.0.1:3306/school?characterEncoding=utf-8";
        String USER = "root";
        String PASSWORD = "Passw0rd";
        String JDBCDriverClassName="com.mysql.cj.jdbc.Driver";

        try {
            // 1.加载驱动程序
            Class.forName(JDBCDriverClassName);
            // 2.获得数据库链接
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            String user_name="Tom";
            //预编译
            String sql="select * from student where user_name=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user_name);
            ResultSet rs = statement.executeQuery();
            // 4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                System.out.println(rs.getString("user_name") + " " + rs.getString("status"));
            }

            // 关闭资源
            conn.close();
            rs.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        conn();
    }

}
