package client.Load;

import java.io.*;

/**
 * 用于加载广告配置文件的工具类
 * 不建议实例化
 *
 * @author hp
 * @version 1.0
 */
public class AdLoad {
    @Deprecated
    private AdLoad() {
    }

    /**
     * 用于加载广告配置文件
     *
     * @return 字符串数组
     */
    @SuppressWarnings("all")
    public static String[] getAd() {
        // 获取系统类加载器,加载配置文件
        StringBuilder sb = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                ClassLoader.getSystemResourceAsStream("client/Load/ad.txt"),
                "UTF-16"
        ))) {
            if (null != br) {

                String str = "";
                sb = new StringBuilder();
                while (null != (str = br.readLine())) {
                    sb.append(str + " ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("广告配置加载成功,结果为: " + sb.toString());
        // 返回使用空格分隔后的字符串数组
        return sb.toString().split(" ");
    }
}
