package client.event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * 用于关闭窗口的事件
 * 让窗口直接刹车
 * 这种刹车方式极不推荐,但项目小,没关系
 *
 * @author hp
 * @version 1.0
 */
public class ExitEvent implements ActionListener {

    JFrame frame;

    public ExitEvent(JFrame f) {
        if (null != f) {
            this.frame = f;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
    }
}
