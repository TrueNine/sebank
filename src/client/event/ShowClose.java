package client.event;

import client.face.indexwindow.WarningWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 一个通用的事件类
 * 用于关闭除当前窗口外的其他窗口
 * 实现了: ActionListener
 * 同时本类提供静态方法,使得可以方便一些地方隐藏和显示窗口
 *
 * @author hp
 * @version 1.0
 * @see java.awt.event.ActionListener 实现了此接口
 */
public class ShowClose implements ActionListener {

    /**
     * 核心容器,用于存储所有的需要隐藏的组件
     */
    Component[] coreArray;

    /**
     * 需要单独显示的窗口
     */
    Component show;

    /**
     * 决定是否警告用户
     */
    boolean isWindow = false;

    WarningWindow ww = new WarningWindow();

    /**
     * 传入一系列布局,然后关闭
     * 参数使用了一个级别比较高的父类
     *
     * @param c    所有窗口
     * @param show 需要显示的窗口
     * @see Component 使用此类进行传参
     */
    public ShowClose(Component show, Component... c) {
        this.coreArray = c;
        this.show = show;
    }

    public ShowClose(boolean is, Component show, Component... c) {
        this.isWindow = is;
        this.coreArray = c;
        this.show = show;
    }

    /**
     * 重写的事件监听,忽略传参
     * 事件触发时,直接调用 每个传入布局的 setVisible(false)
     * 将所有组件隐藏
     *
     * @param e 忽略部分
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isWindow) {
            if (ww.isCommit()) {
                for (Component c : this.coreArray) {
                    c.setVisible(false);
                }
                this.show.setVisible(true);
            }
        } else {
            for (Component c : this.coreArray) {
                c.setVisible(false);
            }
            this.show.setVisible(true);
        }
    }

    /**
     * 用于显示或者隐藏一个或多个布局,控件
     *
     * @param is 隐藏或显示
     * @param c  一个或多个布局,控件
     */
    public static void hideOrShow(boolean is, Component... c) {
        for (Component temp : c) {
            temp.setVisible(is);
        }
    }
}
