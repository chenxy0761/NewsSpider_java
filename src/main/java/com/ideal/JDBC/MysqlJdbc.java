package com.ideal.JDBC;

import java.sql.*;

public class MysqlJdbc {
    private static StringBuilder urls = new StringBuilder("");
    public static StringBuilder getURL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");     //加载MYSQL JDBC驱动程序
            //Class.forName("org.gjt.mm.mysql.Driver");
            System.out.println("Success loading Mysql Driver!");
        } catch (Exception e) {
            System.out.print("Error loading Mysql Driver!");
            e.printStackTrace();
        }
        try {
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test", "root", "root");
            //连接URL为   jdbc:mysql//服务器地址/数据库名  ，后面的2个参数分别是登陆用户名和密码

            System.out.println("Success connect Mysql server!");
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("select `URL` from baojing;");
            //user 为你表的名称
            while (rs.next()) {
                urls.append(rs.getString("URL")+"\n");
            }
            return urls;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
