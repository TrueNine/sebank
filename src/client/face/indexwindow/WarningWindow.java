package client.face.indexwindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author hp
 * @version 1.0
 */
public class WarningWindow implements WarningWindowInterface {

    private int value = 0;


    private JPanel home;
    private JLabel label_warning;
    private JButton button_run;
    private JButton button_exit;

    JDialog frame = new JDialog();

    /**
     * 初始化一个窗口,设置为模态
     * 将关闭状态设置为只关闭当前窗口
     * 增加按钮事件,使得每个按钮都会关闭窗口
     * 设置关闭窗口事件,如果发现确认则忽略,否则结果就是2
     */
    public WarningWindow() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        button_run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("WarningWindow.actionPerformed");
                value = 1;
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        button_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("WarningWindow.actionPerformed");
                value = 2;
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("WarningWindow.windowClosing");
                if (value != 1) {
                    value = 2;
                }
                System.out.println(value);
                super.windowClosing(e);
            }
        });

        frame.setTitle("提示");
        frame.setModal(true);
        frame.setContentPane(home);
        frame.pack();
        frame.setLocation(600, 444);
        frame.setVisible(true);
    }

    public void setData(WarningWindow data) {
    }

    public void getData(WarningWindow data) {
    }

    public boolean isModified(WarningWindow data) {
        return false;
    }

    /**
     * 获取当前窗口的值
     * 0  未设置
     * 1  确定
     * 2  取消,包括关闭
     * 同时关闭当前窗口
     *
     * @return 对应 int
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * 不断循环取值,然后关闭窗口
     * 直到取到值为止
     *
     * @return 布尔值
     */
    @Override
    public boolean isCommit() {
        this.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        return this.getValue() == 1;
    }
}
