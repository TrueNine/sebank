package client;

import client.Load.ConnDataBase;

/**
 * 用于功能页的逻辑
 * 1. 负责校验是否为空,返回字符串
 * 2. 负责校验判断是否为数值,返回字符串
 * 3. 负责校验两个密码是否一致
 * 4. 负责连接 数据业务层 查询,接取数据,返回字符串
 * 5. 负责连接 数据业务层 增加,删除,改动,返回字符串
 * 全部方法为静态方法
 *
 * @author hp
 * @version 1.0
 */
public class IndexController {

    private static final String NULL_USER = "账户数据异常";
    private static final String NEGATIVE_NUMBER = "输入为负数";
    private static final String NO_SAME = "输入的不一致";
    private static final String NO_NUMBER = "输入的项不正确";
    private static final String EMPTY = "输入项不能为空";
    private static final String OK = "006";


    /**
     * 用于修改密码
     *
     * @param account 用户
     * @param s1      旧密码
     * @param s2      新密码
     * @param s3      确认密码
     * @return 相应字符串提示
     */
    public static String repassword(String account, String s1, String s2, String s3) {
        String result;
        if (OK.equals(result = isEmpty(account))) {
            if (OK.equals(result = isEmpty(s1, s2, s3))) {
                if (OK.equals(result = isSame(s2, s3))) {
                    if (completed(result)) {
                        // 连接数据库
                        result = ConnDataBase.USER_INTERFACE.rePassword(account, s1, s3);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 用于存钱
     *
     * @param account 用户
     * @param str     钱数
     * @return 对应结果
     */
    public static String addMoney(String account, String str) {
        String result;
        if (OK.equals(result = isMoney(account, str))) {
            // 连接数据库
            result = ConnDataBase.USER_INTERFACE.addMoney(account, str);
        }
        return result;
    }

    /**
     * 用于取款
     *
     * @param account 用户
     * @param str     钱数
     * @return 对应结果
     */
    public static String removeMoney(String account, String str) {
        String result;
        if (OK.equals(result = isMoney(account, str))) {
            // 连接数据库
            result = ConnDataBase.USER_INTERFACE.removeMoney(account, str);
        }
        return result;
    }

    /**
     * 用于向目标账户转账
     *
     * @param account       当前账户
     * @param targetAccount 目标账户
     * @param str           钱数
     * @return 相应结果
     */
    public static String updateMoney(String account, String targetAccount, String str) {
        String result;
        if (OK.equals(result = isEmpty(account, targetAccount))) {
            if (OK.equals(result = isMoney(account, str))) {
                // 连接数据库
                result = ConnDataBase.USER_INTERFACE.transferTo(account, targetAccount, str);
            }
        }
        return result;
    }

    /**
     * 通过账户查询当前账户的所有交易记录
     * 该事件需要绑定于查询按钮,提前渲染
     *
     * @param account 账户
     * @return 相应结果
     */
    public static String[] selectJiaoyi(String account) {
        // 连接数据库
        return ConnDataBase.USER_INTERFACE.recordingQuery(account);
    }

    /**
     * 用于查询用户余额
     *
     * @param account 用户
     * @return 对应结果
     */
    public static String getYue(String account) {
        // 连接数据库
        return ConnDataBase.USER_INTERFACE.remainderMoney(account);
    }

    /**
     * 私有
     * 判断账户是否为空
     * 判断钱是否为空
     * 判断输入的钱是否为浮点数
     *
     * @param account 用户
     * @param str     钱数
     * @return 对应结果
     */
    private static String isMoney(String account, String str) {
        String result;
        if (OK.equals(result = isEmpty(account))) {
            if (OK.equals(result = isEmpty(str))) {
                if (OK.equals(result = isInt(str))) {
                    if (OK.equals(result = isNegativeNumber(Double.parseDouble(str)))) {
                        result = OK;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 判断字符串是否符合条件解析为浮点型
     *
     * @param str 字符串
     * @return 对应提示
     */
    private static String isDouble(String... str) {
        String result = OK;
        boolean isBreak = false;
        if (null != str && str.length >= 1) {
            for (String s : str) {
                result = isEmpty(s);
                if (s.endsWith(".") || s.startsWith(".")) {
                    result = NO_NUMBER;
                    break;
                }
                char[] cs = s.toCharArray();
                for (char c : cs) {
                    if (!Character.isDigit(c)) {
                        if ('.' != c) {
                            result = NO_NUMBER;
                            isBreak = true;
                            break;
                        }
                    }
                }
                if (isBreak) {
                    result = NO_NUMBER;
                    break;
                }
            }
        } else {
            result = isEmpty(str);
        }
        return result;
    }

    /**
     * 解析字符串是否符合解析条件,为一个整数
     *
     * @param str 字符串
     * @return 对应结果
     */
    private static String isInt(String... str) {
        if (null != str) {
            for (String s : str) {
                char[] cs = s.toCharArray();
                for (char c : cs) {
                    if (!Character.isDigit(c)) {
                        return NO_NUMBER;
                    }
                }
            }
        } else {
            return isEmpty(str);
        }
        return OK;
    }

    private static String isNegativeNumber(Number n) {
        // 不能为负数或者 0
        // 0 没有意义,浪费数据库资源
        if (n.doubleValue() <= 0) {
            return NEGATIVE_NUMBER;
        }
        return OK;
    }

    /**
     * 判断两个字符串是否一致
     *
     * @param s1 字符串
     * @param s2 字符串
     * @return 相应提示
     */
    private static String isSame(String s1, String s2) {
        if (null != s1 && null != s2) {
            if (s1.equals(s2)) {
                return OK;
            } else {
                return NO_SAME;
            }
        } else {
            return isEmpty(s1, s2);
        }
    }

    /**
     * 传入得到的字符串结果,判断是否登录成功
     *
     * @param result 字符串结果
     * @return 布尔值, 用于控制是否显示主页
     */
    public static boolean completed(String result) {
        return OK.equals(result);
    }

    /**
     * 判断所有项,是否为空
     *
     * @param str 所有字符串
     * @return 对应提示
     */
    private static String isEmpty(String... str) {
        if (null != str && str.length >= 1) {
            for (String s : str) {
                if ("".equals(s) || (null == s)) {
                    return EMPTY;
                }
            }
        } else {
            return EMPTY;
        }
        return OK;
    }
}