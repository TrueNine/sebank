package client.face.indexwindow;

import client.IndexController;

import javax.swing.*;

/**
 * @author hp
 * @version 1.0
 */
public class SelectJiaoYi implements FunctionWindow {
    private JPanel home;
    private JList<String> list;
    private String account;

    public SelectJiaoYi(String account) {
        this.account = account;
        init();
    }

    private void init() {
        // 向 JList 渲染数据
        list.setListData(IndexController.selectJiaoyi(account));
    }

    public void setData(SelectJiaoYi data) {
    }

    public void getData(SelectJiaoYi data) {
    }

    public boolean isModified(SelectJiaoYi data) {
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
        frame.setContentPane(new SelectJiaoYi(account).home);
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

    /**
     * 用于返回当前类的List
     *
     * @return 字符串list(窗口)
     */
    public JList<String> getList() {
        return list;
    }
}
