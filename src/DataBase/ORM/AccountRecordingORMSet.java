package DataBase.ORM;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 一个容器,用于存放取出的交易记录
 * 继承了队列
 * 提供一些方法,可以取出字符串数组!!
 *
 * @author hp
 * @version 1.0
 */
public class AccountRecordingORMSet<E> extends LinkedBlockingQueue<E> {

    /**
     * 取出容器内的所有交易记录,转换为字符串
     * <h1>如果没有交易记录则为空</h1>
     *
     * @return 交易记录字符串数组
     */
    public String[] getAllToStringArray() {
        Object[] objects = this.toArray();
        String[] result = new String[objects.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = objects[i].toString();
        }
        return result;
    }
}
