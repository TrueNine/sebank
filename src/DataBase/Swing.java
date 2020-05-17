package DataBase;

import DataBase.Connection.JDBCUtils;
import DataBase.Connection.Str;
import DataBase.DAO.interfaces.BankDAOInterface;
import DataBase.ORM.AccountORM;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * 接口的实现类,用于和视图层接入3
 *
 * @author hp
 * @version 1.0
 */
public class Swing implements UserInterface {

    /**
     * 用于和数据库连接的连接
     */
    private Connection conn;
    /**
     * 用于和数据库连接的DAO
     */
    private BankDAOInterface bdi;

    public Swing() {
        this.conn = JDBCUtils.getConnection();
        this.bdi = new DataBaseLogic();
    }

    /**
     * 用于登录界面返回消息
     *
     * @param account  用户
     * @param password 密码
     * @return 对应结果
     */
    @Override
    public String login(String account, String password) {
        // id 不是数值
        if (!isDigit(account))
            return Str.error(Str.LOGIN, Str.LOGIN_ID);
        // id,密码 超出长度
        if (!isLength(6, account, password))
            return Str.error(Str.LOGIN, Str.ID_LENGTH);
        // 用户不存在
        AccountORM id = bdi.getAccountORMById(conn, Integer.parseInt(account));
        if (null == id)
            return Str.error(Str.LOGIN, Str.NO_ACCOUNT_LOGIN);
        // 密码是否正确
        if (!password.equals(id.getPassword()))
            return Str.error(Str.LOGIN, Str.LOGIN_PASSWORD_ERROR);
        else return Str.ok(Str.LOGIN);
    }

    /**
     * 用于注册界面返回消息
     * 名称不能超过 6 个字符
     * 密码 不能超过 6 个字符
     *
     * @param id       用户账号
     * @param name     用户名称
     * @param password 用户密码
     * @return 对应结果
     */
    @Override
    public String registered(String id, String name, String password) {
        // id 和 name 长度不符合规则
        if (!isLength(6, id, name))
            return Str.error(Str.REGISTERED, Str.ID_LENGTH);
        // id 不是数值数值
        if (!isDigit(id))
            return Str.error(Str.REGISTERED, Str.LOGIN_ID);
        // 用户已经存在
        AccountORM a = bdi.getAccountORMById(conn, Integer.parseInt(id));
        if (null != a)
            return Str.error(Str.REGISTERED, Str.REGISTERED_ID_EXISTS);

        // 进行注册
        return bdi.registeredAccount(conn, new AccountORM(
                Integer.parseInt(id),
                name,
                password,
                0
        ));
    }

    /**
     * 通过 id 进行存款
     *
     * @param id    账户
     * @param money 金额
     * @return 对应结果
     */
    @Override
    public String addMoney(String id, String money) {
        // id 不合法
        if (!isDigit(id))
            return Str.error(Str.ADD, Str.ID_LENGTH);
        // 金额不合法
        if (!isDigit(money))
            return Str.error(Str.ADD, Str.NO_NUMBER);
        else return bdi.addMoney(conn,
                new AccountORM(
                        Integer.parseInt(id)
                ),
                Integer.parseInt(money));
    }

    /**
     * 根据 id 进行取款
     *
     * @param id    账户
     * @param money 金额
     * @return 对应结果
     */
    @Override
    public String removeMoney(String id, String money) {
        // id 不合法
        if (!isDigit(id))
            return Str.error(Str.REMOVE, Str.LOGIN_ID);
            // 金额不合法
        else if (!isDigit(money))
            return Str.error(Str.REMOVE, Str.NO_NUMBER);
            // id超过长度限制
        else if (!isLength(6, id))
            return Str.error(Str.REMOVE, Str.ID_LENGTH);
        // 用户不存在,金额不足
        AccountORM a = bdi.getAccountORMById(conn, Integer.parseInt(id));
        if (null == a)
            return Str.error(Str.REMOVE, Str.NO_ACCOUNT_LOGIN);
        else if (!(a.getRemainderMoney() > Integer.parseInt(money)))
            return Str.error(Str.REMOVE, Str.NO_MONEY);
        else return bdi.removeMoneyById(conn,
                    new AccountORM(
                            Integer.parseInt(id)
                    ), Integer.parseInt(money));
    }

    /**
     * 根据 双方 id 进行转账
     *
     * @param id       id
     * @param targetId 对方id
     * @param money    金额
     * @return 对应结果
     */
    @Override
    public String transferTo(String id, String targetId, String money) {
        if (id.equals(targetId)) {
            return Str.error(Str.TRANSFER, Str.NO_HTIS_TRANSFER);
        }
        if (isDigit(id) && isDigit(targetId) && isDigit(money)) {
            try {
                // 查询是否同时存在两个目标
                if (accountExists(parseInt(id)) && accountExists(parseInt(targetId))) {
                    // 金额是否足以支付
                    AccountORM a = bdi.getAccountORMById(conn, Integer.parseInt(id));
                    if (a.getRemainderMoney() > Integer.parseInt(money)) {
                        // 进行转账
                        return bdi.transferById(conn,
                                a,
                                new AccountORM(
                                        Integer.parseInt(id)
                                ), Integer.parseInt(money));
                    } else {
                        return Str.error(Str.TRANSFER, Str.NO_MONEY);
                    }
                } else {
                    return Str.error(Str.TRANSFER, Str.NO_ACCOUNT_LOGIN);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return Str.error(Str.TRANSFER, Str.NO_CONNECTION);
            }
        } else {
            return Str.error(Str.TRANSFER, Str.LOGIN_ID);
        }
    }

    /**
     * 返回对应 id 的所有记录
     *
     * @param id 账户
     * @return 对应结果
     */
    @Override
    public String[] recordingQuery(String id) {
        // id 非法
        if (!isDigit(id))
            return new String[]{Str.error(Str.YUER, Str.NO_ACCOUNT_LOGIN)};
        else
            return bdi.getRecordingById(conn, new AccountORM(Integer.parseInt(id)));
    }

    /**
     * 根据 id 返回对应的 余额
     *
     * @param id 账户
     * @return 对应结果
     */
    @Override
    public String remainderMoney(String id) {
        // id 非法
        if (!isDigit(id))
            return Str.error(Str.YUER, Str.NO_ACCOUNT_LOGIN);
        else
            return bdi.getYuer(conn, new AccountORM(Integer.parseInt(id)));
    }

    /**
     * 用于修改密码
     * 密码长度不得超过 6个字符
     *
     * @param id          账户
     * @param oldPassword 现在的密码
     * @param newPassword 新的密码
     * @return 对应结果
     */
    @Override
    public String rePassword(String id, String oldPassword, String newPassword) {
        // 密码长度不正确
        if (!isLength(6, oldPassword, newPassword))
            return Str.error(Str.REPASSWORD, Str.ID_LENGTH);
        // id 非法
        if (!isDigit(id))
            return Str.error(Str.REPASSWORD, Str.LOGIN_ID);
        // 账户不存在,新旧密码一致,旧密码不正确
        AccountORM a = bdi.getAccountORMById(conn, Integer.parseInt(id));
        if (null == a)
            return Str.error(Str.REPASSWORD, Str.NO_ACCOUNT_LOGIN);
        else if (!(oldPassword.equals(a.getPassword())))
            return Str.error(Str.REPASSWORD, Str.NO_PASSWORD);
        else if ((newPassword.equals(a.getPassword())))
            return Str.error(Str.REPASSWORD, Str.NO_PASSWORD_EQUALS);
        else
            return bdi.rePasswordById(conn, a, newPassword);
    }

    /**
     * 判断字符串是否为数值,整数
     *
     * @param str 字符串
     * @return 对应结果
     */
    @Override
    public boolean isDigit(String str) {
        char[] cs = str.toCharArray();
        for (char c : cs) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 用于解析字符串为整数
     *
     * @param str 字符串
     * @return 整数
     * @throws ParseException 可能解析不了的数字
     */
    @Override
    public int parseInt(String str) throws ParseException {
        if (!isDigit(str)) {
            throw new ParseException("解析不出", -1);
        }
        return Integer.parseInt(str);
    }

    /**
     * 判断长度是否超过了指定长度
     * 判断所有的字符串长度
     *
     * @param length 字符串不能超过的长度
     * @param str    字符串
     * @return 对应结果
     */
    @Override
    public boolean isLength(int length, String... str) {
        boolean is = true;
        for (String s : str) {
            if (s.length() > length) {
                is = false;
                break;
            }
        }
        return is;
    }

    /**
     * 用于关闭数据库连接
     */
    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 用于登录,注册,查询数据库,查看此 id 是否存在
     * 可能会消耗资源,但出于安全考虑,还是消耗资源的好
     *
     * @param id 用户 账号
     * @return 是否存在
     */
    private boolean accountExists(int id) {
        AccountORM a = bdi.getAccountORMById(conn, id);
        // 如果没有查询到此用户,则直接返回
        return null != a;
    }
}