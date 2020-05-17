package client.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 此方法用于隐藏鼠标事件的其他特性
 * 只需要重写鼠标点击事件
 * 其他方法被隐藏
 *
 * @author hp
 * @version 1.0
 */
public abstract class MouseClick implements MouseListener {

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
