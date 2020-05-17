package DataBase.DAO.interfaces;

import DataBase.Connection.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 用于规定一些常规操作
 *
 * @author hp
 * @version 1.0
 */
public abstract class BaseDAO {

    public static final String ADD = "存款";
    public static final String REMOVE = "取款";
    public static final String REPASSWORD = "改密";
    public static final String TRANSFER = "转账";
    public static final String TRANSFER_T = "被转";
    public static final String YUER = "查余";
    public static final String REGISTERED = "注册";

    public static final String NO_ACCOUNTID = "账户ID已存在 001";
    public static final String NO_NUMBER = "数值错误 002";
    public static final String NO_MONEY = "余额不足 003";
    public static final String NO_CONNECTION = "数据库宕机 004";
    public static final String NO_PASSWORD = "旧密码不符 005";
    public static final String NO_PASSWORD_EQUALS = "新密码与旧密码一致 007";
    public static final String NO_TRANSFER_ID = "对方账户不存在 008";
    public static final String NO_ACCOUNT = "当前用户非法 009";
    public static final String REGISTERED_ID_EXISTS = "注册的账号已存在 010";

    public static final String OK = " : 操作成功! 006";
    public static final String LOSE = " : 操作失败! 错误原因: ";

    /**
     * 对表进行修改
     * 传入连接器,返回是否修改成功
     *
     * @param conn 连接器
     * @param dml  sql语句
     * @param args 参数列表
     * @return 是否成功
     */
    public boolean update(Connection conn, String dml, Object... args) {
        boolean is = false;
        try {
            // 预编译SQL
            PreparedStatement ps = conn.prepareStatement(dml);
            // 填写参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 执行
            is = ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return is;
    }

    /**
     * 用于查询每一列数据
     *
     * @param clazz 传入类类型
     * @param conn  连接器
     * @param sql   SQL语句
     * @param args  参数列表
     * @param <E>   返回类型
     * @return 列表集合
     */
    public <E> List<E> selectAll(Class<E> clazz, Connection conn, String sql, Object... args) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        ResultSetMetaData metaData;
        try {
            // 预编译SQL
            ps = conn.prepareStatement(sql);
            // 填写参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 获取结果集,放入list
            rs = ps.executeQuery();
            // 获取元数据,用于检测是否有至少一列数据
            metaData = rs.getMetaData();
            if (null != metaData) {
                List<E> resultList = new LinkedList<>();
                while (rs.next()) {
                    // 设置属性并返回
                    E e = clazz.newInstance();
                    for (int i = 0; i < metaData.getColumnCount(); i++) {
                        Object value = rs.getObject(i + 1);
                        Field f = clazz.getDeclaredField(
                                metaData.getColumnLabel(i + 1)
                        );
                        f.setAccessible(true);
                        f.set(e, value);
                    }
                    resultList.add(e);
                }
                return resultList;
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException throwables) {
            throwables.printStackTrace();
        } finally {
            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 单独获取一条数据,获取结果集直接写死
     *
     * @param conn  连接器
     * @param sql   SQL语句
     * @param args  参数列表
     * @param <E>   决定的返回类型
     * @param clazz 需要的返回类型
     * @return 一条对应类型数据
     */
    @SuppressWarnings("all")
    public <E> E getOneLine(Connection conn, Class<E> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;
        try {
            // 预编译SQL
            ps = conn.prepareStatement(sql);
            // 设置参数
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 获得结果集
            rs = ps.executeQuery();
            // 获得元数据
            metaData = rs.getMetaData();
            // 单独只获取一条数据
            if (metaData != null) {
                if (rs.next()) {
                    // 设置到相应的对象
                    E e = clazz.newInstance();
                    for (int i = 0; i < metaData.getColumnCount(); i++) {
                        // 通过反射设置值
                        Object value = rs.getObject(i + 1);
                        Field f = e.getClass().getDeclaredField(
                                metaData.getColumnLabel(i + 1)
                        );
                        f.setAccessible(true);
                        f.set(e, value);
                    }
                    return e;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            if (null != ps) {
                JDBCUtils.closeSource(null, ps, null);
            }
            if (null != rs) {
                JDBCUtils.closeSource(null, null, rs);
            }
        }
        return null;
    }
}