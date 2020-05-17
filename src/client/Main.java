package client;

import client.Load.AdLoad;
import client.face.login;

import javax.swing.*;

/**
 * 用于启动程序
 * 在 main 方法内 作简单的一些加载
 *
 * @author hp
 * @version 1.0
 */
public class Main {

    private static final JFrame FRAME = new JFrame("复兴银行客户端 version 0.0.0.3");

    public static void main(String[] args) {
        System.out.println("程序启动");

        login l = new login();

        // 显示主页布局
        FRAME.setContentPane(l.getHome());

        // 加载广告到功能主页布局
        l.getIndex_JList_ad().setListData(AdLoad.getAd());

        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setBounds(500, 66, 768, 1024);

        // 设置窗口最大化
        FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);

        FRAME.setVisible(true);
        System.out.println("启动成功");
    }

    /**
     * 对外提供的可以获取主窗口的类
     */
    public static JFrame getFrame() {
        return FRAME;
    }
}