package client.face;

import client.LoginController;
import client.Main;
import client.event.ExitEvent;
import client.event.MouseClickVisible;
import client.event.ShowClose;
import client.face.indexwindow.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主页布局
 * 包含登录布局,注册布局,功能主页布局
 * 三个布局放置于一个 JPanel
 * 使用 setVisible 来控制之间的关系
 *
 * @author hp
 * @version 1.0
 */
public class login {

    /**
     * 用于存储当前登录用户的账号和密码
     * 建议使用 account 和 password
     */
    private String CURRENT_USER;
    /**
     * 询问主窗体是否需要设置事件
     * 初始化为true,当进行一次设置之后
     * 必须改变其值为false,防止再次进行设置
     */
    private boolean isEvent;

    private JLabel test;
    private JPanel home;
    private JPanel login;
    private JButton login_button_exit;
    private JLabel login_label_logo;
    private JPasswordField login_passwordField_password;
    private JTextField login_textField_account;
    private JLabel login_label_account;
    private JLabel login_label_password;
    private JLabel login_label_error;
    private JButton login_button_commit;
    private JButton login_button_regitered;
    private JPanel registered;
    private JTextField registered_textField_password;
    private JTextField registered_textField_okPassword;
    private JButton registered_button_commit;
    private JLabel registered_label_account;
    private JLabel registered_label_okPassword;
    private JLabel registered_label_passowrd;
    private JTextField registered_textField_account;
    private JLabel registered_label_error;
    private JPanel index;
    private JButton index_button_removeMoney;
    private JList<String> index_JList_ad;
    private JPanel index_JPanel_buttonBar;
    private JButton index_button_addMoney;
    private JButton index_button_selectJiaoYi;
    private JButton index_button_updateMoney;
    private JButton index_button_repassword;
    private JButton index_button_yue;
    private JButton index_button_back;
    private JButton registered_button_back;
    private JLabel registered_label_logo;
    private JLabel index_label_welcome;
    private JLabel registered_label_id;
    private JTextField registered_textField_id;

    /**
     * 初始化一些属性
     * 总结就是:
     * 1. 开始只是显示登陆布局,隐藏其他布局
     * 2. 注册,则显示注册布局,隐藏其他布局
     * 3. 只有登陆成功,才能登陆
     */
    public login() {
        // 设置只显示登陆窗口,隐藏其他窗口
        ShowClose.hideOrShow(false,
                this.index,
                this.registered,
                this.login_label_error
        );
        this.isEvent = true;
        // 初始化所有事件
        this.initializeEvent();
    }

    /**
     * 初始化事件监听的调度方法
     */
    private void initializeEvent() {
        // 设置登录退出按钮事件
        loginButtonEvent();
        // 设置登录界面注册事件
        registeredButtonEvent();
    }

    private void loginButtonEvent() {
        // 设置退出按钮可以关闭窗口
        this.login_button_exit.addActionListener(new ExitEvent(Main.getFrame()));
        // 设置提交按钮进行验证事件
        this.login_button_commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = login_textField_account.getText();

                // 防止获取到空值
                String password;
                try {
                    password = String.valueOf(login_passwordField_password.getPassword());
                } catch (NullPointerException e1) {
                    password = "";
                }

                // 调用数据库事件
                String result = LoginController.loginAccountAndPassword(account, password);
                login_label_error.setText(result);
                login_label_error.setVisible(true);

                // 判断结果,是否显示主页,设置容器用户和密码
                if (LoginController.completed(result)) {
                    ShowClose.hideOrShow(false, login, registered);
                    index.setVisible(true);
                    login_label_error.setVisible(false);
                    // 设置欢迎词
                    index_label_welcome.setText("欢迎您:  " + account);
                    // 设置当前变量
                    CURRENT_USER = account;

                    if (isEvent) {
                        // 设置主页事件
                        indexButtonEvent();
                    }

                    // 设置事件重复控制为 false
                    isEvent = false;
                }
            }
        });
        // 设置清空提示事件
        MouseClickVisible.setEvent(login_label_error, login_textField_account, login_passwordField_password);
    }

    private void registeredButtonEvent() {
        // 显示主窗口,隐藏其他布局
        login_button_regitered.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowClose.hideOrShow(false, index, login, registered_label_error);
                ShowClose.hideOrShow(true, registered);
            }
        });
        // 设置返回事件
        registered_button_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowClose.hideOrShow(false, registered, index);
                ShowClose.hideOrShow(true, login);
            }
        });
        // 设置校验事件
        registered_button_commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用数据库事件
                String id = registered_textField_id.getText();
                String result = LoginController.registeredAccountAndPassword(
                        registered_textField_id.getText(),
                        registered_textField_account.getText(),
                        registered_textField_password.getText(),
                        registered_textField_okPassword.getText()
                );
                registered_label_error.setText(result);
                registered_label_error.setVisible(true);

                // 判断结果,是否显示主页,并设置账户密码给容器
                if (LoginController.completed(result)) {
                    ShowClose.hideOrShow(false, login, registered, registered_label_error);

                    index.setVisible(true);
                    // 设置欢迎词
                    index_label_welcome.setText("欢迎您:  " + id);
                    // 设置当前用户
                    CURRENT_USER = id;

                    if (isEvent) {
                        // 设置主页事件
                        indexButtonEvent();
                    }

                    // 设置事件重复控制为 false
                    isEvent = false;
                }
            }
        });
        // 设置鼠标移入框事件
        MouseClickVisible.setEvent(registered_label_error,
                registered_textField_account,
                registered_textField_password,
                registered_textField_okPassword);
    }

    private void indexButtonEvent() {
        // 设置返回按钮事件
        index_button_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (new WarningWindow().isCommit()) {
                    // 清空当前用户
                    CURRENT_USER = "";
                    ShowClose.hideOrShow(true, login);
                    ShowClose.hideOrShow(false, index, registered);
                }
            }
        });
        // 设置存款按钮事件
        index_button_addMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FunctionWindow f = new addMoney(getAccount());
                f.getHomeShow("存款");
            }
        });
        // 设置取款按钮事件
        index_button_removeMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FunctionWindow f = new RemoveMoney(getAccount());
                f.getHomeShow("取款");
            }
        });
        // 设置查询余额事件
        index_button_yue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FunctionWindow f = new Yue(getAccount());
                f.getHomeShow("余额查询");
            }
        });
        // 设置转账按钮事件
        index_button_updateMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FunctionWindow f = new UpdateMoney(getAccount());
                f.getHomeShow("转账");
            }
        });
        // 设置修改密码事件
        index_button_repassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FunctionWindow f = new RePassword(getAccount());
                f.getHomeShow("修改密码");
            }
        });
        // 设置查询交易记录事件
        index_button_selectJiaoYi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FunctionWindow f = new SelectJiaoYi(getAccount());
                f.getHomeShow("账单");
            }
        });
    }

    /**
     * 用于返回当前布局主界面
     *
     * @return 主界面 JPanel
     */
    public JPanel getHome() {
        return home;
    }

    /**
     * 用于返回当前窗口的当前用户
     *
     * @return 当前用户字符串
     */
    public String getAccount() {
        return this.CURRENT_USER;
    }

    /**
     * 用于返回广告面板的容器
     * 经尝试该容器可以像 list 一样使用
     *
     * @return 字符串形式的容器
     */
    public JList<String> getIndex_JList_ad() {
        return index_JList_ad;
    }

    /*
        以下的方法是 ide 自带的一些方法,并没有调用
        但是以防出错,所以留下
     */

    public void setData(client.face.login data) {
    }

    public void getData(client.face.login data) {
    }

    public boolean isModified(client.face.login data) {
        return false;
    }
}