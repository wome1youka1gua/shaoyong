package com.example.myapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    static final String CLS = "com.mysql.jdbc.Driver";   //驱动，导进来的jar包生成的
    static final String URL = "jdbc:mysql://192.168.1.11:3306/ShopManager?" +
            "serverTimezone = GMT&characterEncoding=UTF-8"; //数据库地址
    static final String USER = "root"; //账号
    static final String PWD = "1234";  //密码
    static final String tempString = "";

    private static Connection conn;   //数据库连接对象

    private static PreparedStatement preStmt;
    private static ResultSet rs;

    public static Connection GetDBConn(){
        return conn;
    }


    public static void Connect(){
        // 必须新开线程才能连接MySQL数据库
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Class.forName(CLS).newInstance();
                    // 连接数据库
                    conn = (Connection) DriverManager.getConnection(URL, USER, PWD);
                    System.out.println("连接成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("连接失败了捏");
                    System.out.println(e);
                }
            }
        }).start();
    }

    public static void CreateAll(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("正在创建表");
                Statement statement;
                try {
                    statement = DBUtil.conn.createStatement();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("创建失败");
                    return;
                }
                for(String sql_str : SqlStatement.SQL_CREATE_ALL){
                    try{
                        statement.execute(sql_str);
                        System.out.println("成功");
                    }
                    catch (Exception e){
                        System.out.println("失败 " + e);
                    }
                }

            }
        }).start();
    }

    public static List<CommodityListItem> CommodityList() throws SQLException {
        System.out.println("执行：SELECT");
        //rs = preStmt.executeQuery();

        List<CommodityListItem> list = new ArrayList<>();
        preStmt = conn.prepareStatement("SELECT * FROM commodity;");
        System.out.println("执行：SELECT");
        rs = preStmt.executeQuery();
        while(rs.next()){
            CommodityListItem item = new CommodityListItem();
            item.setComKey(rs.getString("comKey"));
            item.setName(rs.getString("name"));
            item.setKind(rs.getString("kind"));
            item.setNum(rs.getString("num"));
            item.setPrice(rs.getString("price"));
            list.add(item);
        }
        //conn.closeConn(conn,preStmt,rs);
        return list;
    }
}
