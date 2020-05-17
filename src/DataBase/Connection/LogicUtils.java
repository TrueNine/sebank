package DataBase.Connection;

/**
 * 一些逻辑工具类
 * 主要是一些逻辑
 * 防止客户端做鬼
 *
 * @author hp
 * @version 1.0
 */
public class LogicUtils {
    /**
     * 判断一个数值是否为负数或者0
     *
     * @param n 数值
     * @return 是否为负数
     */
    public static boolean isNegativeNumberOrZero(Number n) {
        return n.doubleValue() <= 0;
    }
}
