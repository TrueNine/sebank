# 项目说明
此项目是拿到一个学生的题材,然后自己动手实现的一个模拟程序

## 使用到的技术
1. java 原生JDBC
2. swing (使用idea 的 form 文件进行编写)
3. idea 工具
4. MySQL 数据库

## 遇到的技术难点

1. 图形界面事件绑定,传递
```text
1. 要求是需要一个确认按钮才能退出,然后对提交按钮进行了事件绑定,但是遇到了新的问题
    在登录布局中,进行提交则会不断地添加事件,导致不断有新的窗口出现
        解决:
            在登录的同时,使用一个布尔变量,控制是否已经绑定了事件,如果已经绑定,则不需要重复绑定
2. 在退出登录的同时,当前用户不会更新
    经过 debug 发现,已经初始化的传递账户已经固定,所以不能传递新的值
        解决: 使用内部类进行绑定事件,这样可以方便地拿到新的值,每次进行窗口传递就会拿到新的值
```
2. 数据库无故访问
```text
每次提交登录,注册,取款,,,,,,,,等其他业务,都会在数据库进行查询一遍,这样会大大降低数据库的效率
解决方式是,类似于服务器一样,给数据库来一层 "挡板"(拦截器)
    本着不相信用户任何输入的原则,挡板虽然臃肿,但很有用
客户端进行一些简单的操作(象征性拦截,避免消耗数据库性能),然后以字符串形式发送给挡板
由挡板 决定去操作 DAO,DAO操作数据库
```