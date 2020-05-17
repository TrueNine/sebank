package DataBase;

import java.text.ParseException;

/**
 * 用于给视图层调用的操作接口
 * 避免数据库炸裂
 * 实现类实现一些业务逻辑
 * 一个解析器,负责解析操作是否完成
 * 一个解析器,负责解析为整数数值
 *
 * @author hp
 * @version 1.0
 */
public interface UserInterface {
    /**
     * 用于登录界面返回消息
     *
     * @param account  用户
     * @param password 密码
     * @return 对应结果
     */
    String login(String account, String password);

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
    String registered(String id, String name, String password);

    /**
     * 通过 id 进行存款
     *
     * @param id    账户
     * @param money 金额
     * @return 对应结果
     */
    String addMoney(String id, String money);

    /**
     * 根据 id 进行取款
     *
     * @param id    账户
     * @param money 金额
     * @return 对应结果
     */
    String removeMoney(String id, String money);

    /**
     * 根据 双方 id 进行转账
     *
     * @param id       id
     * @param targetId 对方id
     * @param money    金额
     * @return 对应结果
     */
    String transferTo(String id, String targetId, String money);

    /**
     * 返回对应 id 的所有记录
     *
     * @param id 账户
     * @return 对应结果
     */
    String[] recordingQuery(String id);

    /**
     * 根据 id 返回对应的 余额
     *
     * @param id 账户
     * @return 对应结果
     */
    String remainderMoney(String id);

    /**
     * 用于修改密码
     * 密码长度不得超过 6个字符
     *
     * @param id          账户
     * @param oldPassword 现在的密码
     * @param newPassword 新的密码
     * @return 对应结果
     */
    String rePassword(String id, String oldPassword, String newPassword);

    /**
     * 判断字符串是否为数值,整数
     *
     * @param str 字符串
     * @return 对应结果
     */
    boolean isDigit(String str);

    /**
     * 用于解析字符串为整数
     *
     * @param str 字符串
     * @return 整数
     * @throws ParseException 可能解析不了
     */
    int parseInt(String str) throws ParseException;

    /**
     * 判断长度是否超过了指定长度
     * 判断所有的字符串长度
     *
     * @param length 字符串不能超过的长度
     * @param str    字符串
     * @return 对应结果
     */
    boolean isLength(int length, String... str);

    /**
     * 用于关闭数据库连接
     */
    void close();
}
