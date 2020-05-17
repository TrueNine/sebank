package client.face.indexwindow;

/**
 * 一个警告框,用于回滚用户操作
 * 该警告框包含两个按钮
 * 确定,取消
 * 取消则回滚操作
 * 确定则提交事件
 * 继承了 操作窗口的接口
 *
 * @author hp
 * @version 1.0
 * @see FunctionWindow 继承自此接口
 */
public interface WarningWindowInterface {
    /**
     * 获取当前窗口的值
     * 0  未设置
     * 1  确定
     * 2  取消,包括关闭
     * 同时关闭当前窗口
     *
     * @return 对应 int
     */
    int getValue();

    /**
     * 不断循环取值,然后关闭窗口
     * 直到取到值为止
     *
     * @return 布尔值
     */
    boolean isCommit();
}
