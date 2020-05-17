package client.face.indexwindow;

import client.IndexController;
import client.event.MouseClickVisible;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author hp
 * @version 1.0
 */
public class RePassword implements FunctionWindow {
    private JTextField textField_okPassword;
    private JTextField textField_password;
    private JButton button_commit;
    private JLabel label_error;
    private JLabel label_password;
    private JLabel label_okPassowrd;
    private JPanel home;
    private JLabel label_oldPassword;
    private JTextField textField_oldPassword;
    private String account;

    public RePassword(String account) {
        this.account = account;
        init();
    }

    private void init() {
        // 设置错误信息不可见
        label_error.setVisible(false);
        // 绑定输入框清除错误事件
        MouseClickVisible.setEvent(label_error,
                textField_oldPassword,
                textField_password,
                textField_okPassword);
        // 设置提交按钮事件
        button_commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (new WarningWindow().isCommit()) {
                    label_error.setText(
                            IndexController.repassword(account,
                                    textField_oldPassword.getText(),
                                    textField_password.getText(),
                                    textField_okPassword.getText())
                    );
                    label_error.setVisible(true);
                }
            }
        });
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
        frame.setContentPane(new RePassword(account).home);
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

    public void setData(RePassword data) {
    }

    public void getData(RePassword data) {
    }

    public boolean isModified(RePassword data) {
        return false;
    }
}
