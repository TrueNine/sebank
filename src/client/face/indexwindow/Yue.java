package client.face.indexwindow;

import client.IndexController;

import javax.swing.*;

/**
 * @author hp
 * @version 1.0
 */
public class Yue implements FunctionWindow {
    private JPanel home;
    private JLabel label_head;
    private JLabel label_number;
    private String account;

    public Yue(String account) {
        this.account = account;
        init();
    }

    public void init() {
        // 取出余额渲染
        label_number.setText(IndexController.getYue(account));
    }

    public void setData(Yue data) {
    }

    public void getData(Yue data) {
    }

    public boolean isModified(Yue data) {
        return false;
    }

    /**
     * 用于显示窗口
     * 每个功能窗口提供方法,用于显示窗口
     *
     * @param title 窗口标题
     */
    @Override
    public void getHomeShow(String title) {
        JDialog frame = new JDialog();
        frame.setTitle(title);
        frame.setModal(true);
        frame.setContentPane(new Yue(account).home);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocation(500,144);
        frame.setVisible(true);
    }

    /**
     * 用于设置窗口的当前用户
     *
     * @param account 账户
     */
    @Override
    public void setCurrentAccount(String account) {
        this.account = account;
    }

    /**
     * 用于返回当前账户
     *
     * @return 字符串账户
     */
    @Override
    public String getCurrentAccount() {
        return this.account;
    }
}
