package DataBase.Connection;

/**
 * 用于存放一些静态字符串常量
 *
 * @author hp
 * @version 1.0
 */
public class Str {

    public static final String ADD = "存款";
    public static final String REMOVE = "取款";
    public static final String REPASSWORD = "改密";
    public static final String TRANSFER = "转账";
    public static final String TRANSFER_T = "被转";
    public static final String YUER = "查余";
    public static final String REGISTERED = "注册";
    public static final String LOGIN = "登录";

    public static final String NO_ACCOUNTID = "账户ID已存在 001";
    public static final String NO_NUMBER = "数值错误 002";
    public static final String NO_MONEY = "余额不足 003";
    public static final String NO_CONNECTION = "数据库宕机 004";
    public static final String NO_PASSWORD = "旧密码不符 005";
    public static final String NO_PASSWORD_EQUALS = "新密码与旧密码一致 007";
    public static final String NO_TRANSFER_ID = "对方账户不存在 008";
    public static final String NO_ACCOUNT = "当前用户非法 009";
    public static final String REGISTERED_ID_EXISTS = "注册的账号已存在 010";
    public static final String LOGIN_ID = "账户不是数值 011";
    public static final String ID_LENGTH = "长度超过限制 012";
    public static final String NO_HTIS_TRANSFER = "不能给自己转账 013";
    public static final String NO_ACCOUNT_LOGIN = "用户不存在 014";
    public static final String LOGIN_PASSWORD_ERROR = "用户密码不正确 015";

    public static final String OK = " : 操作成功! 006";
    public static final String LOSE = " : 操作失败! 错误原因: ";

    /**
     * 用于自定义拼接返回信息
     *
     * @param args 信息列表
     * @return 自定义消息
     */
    public static String sendNews(String... args) {
        StringBuilder result = new StringBuilder();
        for (String s : args) {
            result.append(s);
        }
        return result.toString();
    }

    /**
     * 用于拼接错误信息
     *
     * @param operating 操作
     * @param origin    原因和错误代码
     * @return 对应结果
     */
    public static String error(String operating, String origin) {
        return sendNews(operating, LOSE, origin);
    }

    /**
     * 用于拼接成功信息
     *
     * @param operating 进行操作
     * @return 对应结果
     */
    public static String ok(String operating) {
        return sendNews(operating, OK);
    }
}
