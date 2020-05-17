package client.event;

import client.face.indexwindow.FunctionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 用于功能页显示一个新的窗口
 *
 * @author hp
 * @version 1.0
 */
public class IndexNewWindow implements ActionListener {

    /**
     * 需要的窗口
     */
    FunctionWindow f;
    String title;
    String account;

    /**
     * 传入一个窗口,用于显示
     *
     * @param f       窗口接口
     * @param title   窗口标题
     * @param account 当前账户
     */
    public IndexNewWindow(FunctionWindow f, String title, String account) {
        this.f = f;
        this.title = title;
        this.account = account;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        f.setCurrentAccount(account);
        f.getHomeShow(title);
    }
}
