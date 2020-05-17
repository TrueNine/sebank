package DataBase.DAO.interfaces;

import DataBase.ORM.AccountORM;

import java.sql.Connection;

/**
 * 用于规定操作银行两张表的规约
 * 可以进行所有操作
 *
 * @author hp
 * @version 1.0
 */
public interface BankDAOInterface {

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
    String addMoney(Connection conn, AccountORM accountORM, int money);

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
    String removeMoneyById(Connection conn, AccountORM account, int money);

    /**
     * 用于修改密码
     * 将用户密码使用反射,设置为新的密码
     *
     * @param conn        连接器
     * @param accountORM  用户
     * @param newPassword 新的密码
     * @return 对应结果
     */
    String rePasswordById(Connection conn, AccountORM accountORM, String newPassword);

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
    String transferById(Connection conn, AccountORM account, AccountORM targetAccount, int money);

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
    String[] getRecordingById(Connection conn, AccountORM account);

    /**
     * 用于注册用户
     * 首先查看该用户的 ID 是否存在,(效率非常低)
     * 然后用户的金额强行设置为 0,安全考虑,由控制层决定不能初始金额
     * 用户名 或 密码不能为空
     *
     * @param conn       连接器
     * @param accountORM 用户
     * @return 对应字符串结果
     */
    String registeredAccount(Connection conn, AccountORM accountORM);

    /**
     * 根据用户ID返回用户信息
     * 传入ID,获取用户对象
     *
     * @param conn 连接器
     * @param id   用户id
     * @return 对应结果
     */
    AccountORM getAccountORMById(Connection conn, int id);

    /**
     * 根据用户对象查询用户余额
     * <h1>必须以数据库的数据为准</h1>
     *
     * @param conn 连接器
     * @param a    用户
     * @return 当前余额
     */
    String getYuer(Connection conn, AccountORM a);
}