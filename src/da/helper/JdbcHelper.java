/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class JdbcHelper {

//    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String dburl = "jdbc:mysql://localhost:3306/duanfinalfpl2";
    private static String username = "root";
    private static String password = "kien5620";

//    static {
//        try {
//            Class.forName(driver);
//        } catch (ClassNotFoundException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

//     test ket noi
    public static void main(String[] args) throws SQLException {

        try {
            Connection connection = DriverManager.getConnection(dburl, username, password);
            System.out.println("ok");

        } catch (Exception e) {
            System.out.println("thatbai");
            e.printStackTrace();
        }

    }

    public static PreparedStatement prepareStatement(String sql, Object... args) throws SQLException {
        Connection connection = DriverManager.getConnection(dburl, username, password);
        PreparedStatement pstmt = null;
        if (sql.trim().startsWith("{")) {
            pstmt = connection.prepareCall(sql);
        } else {
            pstmt = connection.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    public static void executeUpdate(String sql, Object... args) {
        try {

            PreparedStatement stmt = prepareStatement(sql, args);
            try {
                stmt.executeUpdate();
            } finally {
                stmt.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement stmt = prepareStatement(sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
