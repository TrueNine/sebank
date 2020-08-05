package DataBase;

import DataBase.Connection.Str;
import DataBase.DAO.interfaces.BankDAOInterface;
import DataBase.DAO.interfaces.BaseDAO;
import DataBase.ORM.AccountORM;
import DataBase.ORM.AccountRecordingORM;
import DataBase.ORM.AccountRecordingORMSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 实现类,用于实现数据库DAO
 *
 * @author hp
 * @version 1.0
 */
public class DataBaseLogic extends BaseDAO implements BankDAOInterface {

    /* 拼接SQL语句,在拼接的时候,不需要预留空格,已经写好 */

    private static final String SQL_INSERT_ZHANHAO = "insert into zhanhao values(?,?,?,?);";
    private static final String SQL_INSERT_JIAOYI = "insert into jiaoyi values(?,?,?,?,?);";
    private static final String SQL_UPDATE_FIRST_ZHANHAO = "update zhanhao set ";
    public static final String SQL_UPDATE_LAST_ZHANHAO = " where id = ?;";
    public static final String SQL_SELECT_ALL_ZHANHAO = "select `id`,`name` as account,`password`,`yuer` as remainderMoney from zhanhao where id = ?;";
    public static final String SQL_SELECT_LAST_ZHANHAO = " from zhanhao where id = ?;";
    public static final String SQL_SELECT_ALL_JIAOYI = "select `id`,`type`,`money`,`time`,`zhid` as accountId from jiaoyi where zhid = ?;";

    /**
     * 存钱
     * 使用反射设置用户余额
     * 事务推送一条交易记录
     * 返回执行之后对应的字符串
     *
     * @param conn       连接器
     * @param accountORM 用户
     * @param money      金额
     * @return 对应结果
     */
    @Override
    public String addMoney(Connection conn, AccountORM accountORM, int money) {
        String news = "";
        try {
            // 关闭自动提交
            autoCommit(conn, false);
            {
                // 进行存钱
                AccountORM a = getAccountORMById(conn, accountORM.getId());
                // 判断当前用户是否存在
                if (isEmpty(conn, a)) {
                    int yuer = a.getRemainderMoney();
                    String dml = SQL_UPDATE_FIRST_ZHANHAO + "yuer = ?" + SQL_UPDATE_LAST_ZHANHAO;
                    update(conn, dml, money + yuer, accountORM.getId());
                    // 加上交易记录
                    pushRecording(conn, ADD, a, money);
                    conn.commit();
                    news = sendNews(ADD, String.valueOf(money), OK);
                } else {
                    rollbackSQL(conn);
                    news = AccountEmptyNews(ADD + money);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            news = sendNews(ADD, String.valueOf(money), LOSE, NO_CONNECTION);
        } finally {
            autoCommit(conn, true);
        }
        return news;
    }

    /**
     * 取钱
     * 事务查看用户的金额是否足以取出
     * 不足以则回滚,返回对应结果
     * 足以则使用反射设置用户余额
     * 事务推送一条交易记录
     * 返回对应字符串执行结果
     *
     * @param conn    连接器
     * @param account 用户ORM
     * @param money   金额
     * @return 相应结果
     */
    @Override
    public String removeMoneyById(Connection conn, AccountORM account, int money) {
        String news = "";
        try {
            autoCommit(conn, false);
            AccountORM a = getAccountORMById(conn, account.getId());
            if (isEmpty(conn, a)) {
                int yuer = a.getRemainderMoney();
                if (money <= yuer) {
                    // 进行修改
                    String dml = SQL_UPDATE_FIRST_ZHANHAO + "yuer = ?" + SQL_UPDATE_LAST_ZHANHAO;
                    update(conn, dml, yuer - money, a.getId());
                    // 推送交易记录
                    pushRecording(conn, REMOVE, a, money);
                    SQLCommit(conn);
                    news = sendNews(REMOVE, String.valueOf(money), OK);
                } else {
                    rollbackSQL(conn);
                    news = REMOVE + LOSE + NO_MONEY;
                }
            } else {
                rollbackSQL(conn);
                news = sendNews(REPASSWORD, String.valueOf(money), LOSE, NO_ACCOUNT);
            }
        } finally {
            autoCommit(conn, true);
        }
        return news;
    }

    /**
     * 用于修改密码
     * 将用户密码使用反射,设置为新的密码
     *
     * @param conn        连接器
     * @param accountORM  用户
     * @param newPassword 新的密码
     * @return 对应结果
     */
    @Override
    public String rePasswordById(Connection conn, AccountORM accountORM, String newPassword) {
        String news = "";
        try {
            // 关闭自动提交
            autoCommit(conn, false);
            // 旧密码是否符合
            AccountORM a = getAccountORMById(conn, accountORM.getId());
            String password = a.getPassword();
            // 旧密码是否符合
            if (isEmpty(conn, a)) {
                if (password.equals(accountORM.getPassword())) {
                    if (!newPassword.equals(password)) {
                        // 进行更改,推送记录
                        String dml = SQL_UPDATE_FIRST_ZHANHAO + "`password` = ?" + SQL_UPDATE_LAST_ZHANHAO;
                        update(conn, dml, newPassword, a.getId());
                        pushRecording(conn, REPASSWORD + newPassword, a, 0);
                        SQLCommit(conn);
                        news = sendNews(REPASSWORD, newPassword, OK);
                    } else {
                        rollbackSQL(conn);
                        news = sendNews(REPASSWORD, LOSE, NO_PASSWORD_EQUALS);
                    }
                } else {
                    rollbackSQL(conn);
                    news = sendNews(REPASSWORD, LOSE, NO_PASSWORD);
                }
            } else {
                rollbackSQL(conn);
                news = sendNews(REPASSWORD, newPassword, LOSE, NO_ACCOUNT);
            }
        } finally {
            autoCommit(conn, true);
        }
        return news;
    }

    /**
     * 用于转账的方法
     * 事务获取当前用户金额是否足以支付
     * 不足以则回滚,返回对应字符串
     * 事务,足以则设置双方的值
     * 不成功则回滚操作
     *
     * @param conn          连接器
     * @param account       当前用户
     * @param targetAccount 目标用户
     * @param money         金额
     * @return 对应结果
     */
    @Override
    public String transferById(Connection conn, AccountORM account, AccountORM targetAccount, int money) {
        String news = "";
        try {
            autoCommit(conn, false);
            // 以数据库数据为准
            AccountORM a = getAccountORMById(conn, account.getId());
            AccountORM t = getAccountORMById(conn, targetAccount.getId());
            if (isEmpty(conn, a)) {
                if (isEmpty(conn, t)) {
                    // 判断是否有足够的金额
                    int yuer = getOneLine(conn, AccountORM.class, SQL_SELECT_ALL_ZHANHAO, account.getId()).getRemainderMoney();
                    if (money <= yuer) {
                        // 减少自身金额
                        String dml = SQL_UPDATE_FIRST_ZHANHAO + "yuer = ?" + SQL_UPDATE_LAST_ZHANHAO;
                        update(conn, dml, yuer - money, a.getId());
                        // 增加对方金额
                        update(conn, dml, t.getRemainderMoney() + money, t.getId());
                        // 推送交易记录
                        pushRecording(conn, TRANSFER + "(" + t.getAccount() + ")", a, money);
                        pushRecording(conn, TRANSFER_T + "(" + a.getAccount() + ")", t, money);
                        // 进行提交
                        SQLCommit(conn);
                        news = sendNews(TRANSFER, String.valueOf(money), OK);
                    } else {
                        rollbackSQL(conn);
                        news = sendNews(TRANSFER, String.valueOf(money), LOSE, NO_MONEY);
                    }
                } else {
                    rollbackSQL(conn);
                    news = sendNews(TRANSFER, String.valueOf(money), LOSE, NO_TRANSFER_ID);
                }
            } else {
                rollbackSQL(conn);
                news = AccountEmptyNews(TRANSFER + money);
            }
        } finally {
            autoCommit(conn, true);
        }
        return news;
    }

    /**
     * 用于查看用户的交易记录
     * 使用反射获取该用户的 ID
     * 在交易记录表获取该 ID 的所有交易记录
     * 事务:如果没有ID 则回滚
     * 返回对应的字符串数组
     *
     * @param conn    连接器
     * @param account 用户
     * @return 对应结果
     */
    @Override
    public String[] getRecordingById(Connection conn, AccountORM account) {
        AccountRecordingORMSet<AccountRecordingORM> list = new AccountRecordingORMSet<>();
        list.addAll(selectAll(AccountRecordingORM.class, conn, SQL_SELECT_ALL_JIAOYI, account.getId()));
        return list.getAllToStringArray();
    }

    /**
     * 用于注册用户
     * 首先查看该用户的 ID 是否存在,(效率非常低)
     * 然后用户的金额强行设置为 0,安全考虑,由控制层决定不能初始金额
     * 用户名 或 密码不能为空
     *
     * @param conn 连接器
     * @param a    用户
     * @return 对应字符串结果
     */
    @Override
    public String registeredAccount(Connection conn, AccountORM a) {
        String news = "";
        try {
            autoCommit(conn, false);
            // 如果id存在,则已有该用户
            if (!isEmpty(conn, a)) {
                update(conn, SQL_INSERT_ZHANHAO,
                        a.getId(),
                        a.getAccount(),
                        a.getPassword(),
                        0
                );
                SQLCommit(conn);
                news = sendNews(REGISTERED, String.valueOf(a.getId()), OK);
            } else {
                rollbackSQL(conn);
                news = sendNews(REGISTERED, String.valueOf(a.getId()), LOSE, REGISTERED_ID_EXISTS);
            }
        } finally {
            autoCommit(conn, true);
        }
        return news;
    }

    /**
     * 根据用户ID返回用户信息
     * 传入ID,获取用户对象
     *
     * @param conn 连接器
     * @param id   用户id
     * @return 对应结果
     */
    @Override
    public AccountORM getAccountORMById(Connection conn, int id) {
        return getOneLine(conn, AccountORM.class, SQL_SELECT_ALL_ZHANHAO, id);
    }

    /**
     * 根据用户对象查询用户余额
     * <h1>必须以数据库的数据为准</h1>
     *
     * @param comm 连接器
     * @param a    用户
     * @return 当前余额
     */
    @Override
    public String getYuer(Connection comm, AccountORM a) {
        String news = "";
        try {
            autoCommit(comm, false);
            AccountORM account = getAccountORMById(comm, a.getId());
            if (isEmpty(comm, a)) {
                SQLCommit(comm);
                news = String.valueOf(account.getRemainderMoney());
            } else {
                rollbackSQL(comm);
                news = AccountEmptyNews(YUER);
            }
        } finally {
            autoCommit(comm, true);
        }
        return news;
    }

    /**
     * 私有方法,为所有的操作增加交易记录
     *
     * @param conn  连接器
     * @param type  操作类型
     * @param a     账户
     * @param money 金额
     * @return 是否成功
     */
    private boolean pushRecording(Connection conn, String type, AccountORM a, int money) {
        String dmli = SQL_INSERT_JIAOYI;
        AccountRecordingORM r = new AccountRecordingORM(
                type,
                money,
                new Timestamp(System.currentTimeMillis()),
                a.getId()
        );
        return update(conn, dmli,
                r.getId(),
                r.getType(),
                r.getMoney(),
                r.getTime(),
                r.getAccountId()
        );
    }

    /**
     * 用于开关自动提交
     *
     * @param conn 连接器
     * @param is   是否开关
     * @return 是否抛出异常
     */
    private boolean autoCommit(Connection conn, boolean is) {
        try {
            conn.setAutoCommit(is);
            return false;
        } catch (SQLException throwables) {
            return true;
        }
    }

    /**
     * 用于提交
     *
     * @param conn 连接器
     * @return 是否抛出异常
     */
    private boolean SQLCommit(Connection conn) {
        try {
            conn.commit();
            return false;
        } catch (SQLException throwables) {
            return true;
        }
    }

    /**
     * 用于回滚
     *
     * @param conn 连接器
     * @return 是否抛出异常
     */
    private boolean rollbackSQL(Connection conn) {
        try {
            conn.rollback();
            return false;
        } catch (SQLException throwables) {
            return true;
        }
    }

    /**
     * 用于拼接消息
     *
     * @param args 字符串参数
     * @return 消息字符串
     */
    private String sendNews(String... args) {
        return Str.sendNews(args);
    }

    /**
     * 用于判断该用户的 ID ,该用户是否存在
     *
     * @param conn    连接器
     * @param account 用户
     */
    private boolean isEmpty(Connection conn, AccountORM account) {
        return null != getOneLine(conn, AccountORM.class, SQL_SELECT_ALL_ZHANHAO, account.getId());
    }

    /**
     * 输出用户不存在消息
     *
     * @param operating 进行的操作
     * @return 对应字符串
     */
    private String AccountEmptyNews(String operating) {
        return sendNews(operating, LOSE, NO_ACCOUNT);
    }
}