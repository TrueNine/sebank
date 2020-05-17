package client.face.indexwindow;

/**
 * 一个约定的接口,用于方便调度显示窗口
 *
 * @author hp
 * @version 1.0
 */
public interface FunctionWindow {
    /**
     * 用于显示窗口
     * 每个功能窗口提供方法,用于显示窗口
     * 可以忽略该返回值
     *
     * @param title 窗口标题
     */
    void getHomeShow(String title);

    /**
     * 用于设置窗口的当前用户
     *
     * @param account 账户
     */
    void setCurrentAccount(String account);

    /**
     * 用于返回当前账户
     *
     * @return 字符串账户
     */
    String getCurrentAccount();
}
