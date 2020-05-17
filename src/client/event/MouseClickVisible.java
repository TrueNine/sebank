package client.event;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * 复写的鼠标进入事件
 * 构造时传入需要隐藏的 label
 *
 * @author hp
 * @version 1.0
 */
public class MouseClickVisible extends MouseClick {

    JLabel label;

    public MouseClickVisible(JLabel label) {
        this.label = label;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        label.setVisible(false);
    }

    /**
     * 用于设置鼠标移入清除错误事件
     *
     * @param l 需要清除的错误标签
     * @param t 需要设置的文本框
     */
    public static void setEvent(JLabel l, JTextField... t) {
        if (null != t) {
            for (JTextField te : t) {
                te.addMouseListener(new MouseClickVisible(l));
            }
        }
    }
}
