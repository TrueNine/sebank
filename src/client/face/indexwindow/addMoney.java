package client.face.indexwindow;

import client.IndexController;
import client.event.MouseClickVisible;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 存款窗口
 * 用于存款
 * 为提交按钮设置简单校验事件
 *
 * @author hp
 * @version 1.0
 */
public class addMoney implements FunctionWindow {
    private String account;
    private JPanel home;
    private JLabel label_head;
    private JTextField textField_number;
    private JButton button_commit;
    private JLabel label_error;

    public addMoney(String account) {
        this.account = account;
        init();
    }

    private void init() {
        // 设置错误不可见
        label_error.setVisible(false);
        // 设置提交按钮事件
        button_commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (new WarningWindow().isCommit()) {
                    label_error.setText(
                            IndexController.addMoney(account,
                                    textField_number.getText())
                    );
                    label_error.setVisible(true);
                }
            }
        });
        // 设置消除错误事件
        MouseClickVisible.setEvent(label_error, textField_number);
    }

    public void setData(addMoney data) {
    }

    public void getData(addMoney data) {
    }

    public boolean isModified(addMoney data) {
        return false;
    }


    /**
     * 用于显示窗口
     * 每个功能窗口提供方法,用于显示窗口
     * 可以忽略该返回值
     *
     * @param title 窗口标题
     */
    @Override
    public void getHomeShow(String title) {
        JDialog frame = new JDialog();
        frame.setTitle(title);
        frame.setModal(true);
        frame.setContentPane(new addMoney(account).home);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocation(500, 144);
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
