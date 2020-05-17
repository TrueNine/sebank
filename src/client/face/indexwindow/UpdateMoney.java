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
public class UpdateMoney implements FunctionWindow {
    private JPanel home;
    private JTextField textField_account;
    private JButton button_commit;
    private JTextField textField_number;
    private JLabel label_account;
    private JLabel label_money;
    private JLabel label_error;
    private String account;

    public UpdateMoney(String account) {
        this.account = account;
        init();
    }

    private void init() {
        // 隐藏错误信息
        label_error.setVisible(false);
        // 设置输入框隐藏错误事件
        MouseClickVisible.setEvent(label_error,
                textField_account,
                textField_number
        );
        // 设置提交事件
        button_commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (new WarningWindow().isCommit()) {
                    label_error.setText(
                            IndexController.updateMoney(account,
                                    textField_account.getText(),
                                    textField_number.getText())
                    );
                    label_error.setVisible(true);
                }
            }
        });
    }

    public void setData(UpdateMoney data) {
    }

    public void getData(UpdateMoney data) {
    }

    public boolean isModified(UpdateMoney data) {
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
        frame.setContentPane(new UpdateMoney(account).home);
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
