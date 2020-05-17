package client;

import client.Load.ConnDataBase;

/**
 * 用于客户端的一些校验工作<br />
 * 负责内容如下:
 * 0. 校验账号是否存在<br />
 * 1. 校验账号密码是否正确<br />
 * 2. 校验注册两次密码是否正确<br />
 * 3. 校验注册账户是否已经存在<br />
 * 4. 提供静态方法,返回布尔值是否成功<br />
 * <br />
 * 工作完成后,统一返回字符串,而非布尔值
 * 方便渲染到界面
 *
 * @author hp
 * @version 1.0
 */
public class LoginController {

    private static final String EMPTY_STRING = "账户或密码不能为空";
    private static final String ON_SAME = "输入的密码不一致";
    private static final String OK = "006";

    /**
     * 用于校验登录窗口的账号和密码
     *
     * @param account  账户字符串
     * @param password 账户密码
     * @return 字符串结果 成功则返回 "1"
     */
    public static String loginAccountAndPassword(String account, String password) {
        // 输入不能为空
        boolean empty = isEmpty(account, password);
        if (empty) {
            return EMPTY_STRING;
        }
        // 连接数据库对比
        return ConnDataBase.USER_INTERFACE.login(account, password);
    }

    /**
     * 此方法用于判断注册时的一些结果
     *
     * @param id         账户编号
     * @param account    账户字符串
     * @param password   账户第一次输入的密码
     * @param okPassword 账户第二次输入的密码
     * @return 字符串结果, 成功则返回 "1"
     */
    public static String registeredAccountAndPassword(String id, String account, String password, String okPassword) {
        // 是否有没有填的项目
        boolean empty = isEmpty(id, account, password, okPassword);
        if (empty) {
            return EMPTY_STRING;
        }
        // 是否两次输入的密码不一致
        if (!password.equals(okPassword)) {
            return ON_SAME;
        }
        // 连接数据库对比
        return ConnDataBase.USER_INTERFACE.registered(id, account, password);
    }

    /**
     * 传入得到的字符串结果,判断是否登录成功
     *
     * @param result 字符串结果
     * @return 布尔值, 用于控制是否显示主页
     */
    public static boolean completed(String result) {
        return result.endsWith(OK);
    }

    /**
     * 内部私有方法
     * 判断字符串是否为 空或者为 null
     *
     * @param str 字符串
     * @return 布尔值
     */
    private static boolean isEmpty(String... str) {
        for (String s : str) {
            if (("".equals(s) || null == s)) {
                return true;
            }
        }
        return false;
    }
}