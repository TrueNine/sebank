package DataBase.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC连接工具
 * 负责提供连接,关闭资源等功能
 * 从配置文件读取内容
 * <p>配置文件位于: DataBase\Connection\databaseConfig.properties</p>
 *
 * @author hp
 * @version 1.0
 */
public class JDBCUtils {
    /**
     * 用于获取数据库连接
     *
     * @return 数据库连接
     */
    @SuppressWarnings("all")
    public static Connection getConnection() {
        // 读取配置文件
        Properties p = new Properties();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Thread.currentThread()
                                .getContextClassLoader()
                                .getResourceAsStream("DataBase/Connection/databaseConfig.properties")))
        ) {
            p.load(br);
        } catch (IOException e) {
            System.out.println("加载配置文件失败");
        }

        Connection conn = null;
        try {
            // 加载 驱动
            Class<Driver> driverClass = (Class<Driver>) Class.forName(p.getProperty("DriverClass"));
            // 注册驱动
            DriverManager.registerDriver(driverClass.newInstance());
            // 通过工具类获取连接
            conn = DriverManager.getConnection(
                    p.getProperty("url"),
                    p.getProperty("user"),
                    p.getProperty("password")
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    /**
     * 用于关闭数据库
     * 没有需要关闭的直接传入 null 即可
     *
     * @param conn 连接
     * @param s    sql
     * @param r    结果集
     * @return 所有连接是否关闭成功, 可忽略
     */
    public static boolean closeSource(Connection conn, Statement s, ResultSet r) {
        boolean result = true;
        if (null != r) {
            try {
                r.close();
            } catch (SQLException throwables) {
                result = false;
            }
        }
        if (null != s) {
            try {
                s.close();
            } catch (SQLException throwables) {
                result = false;
            }
        }
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                result = false;
            }
        }
        return result;
    }
}