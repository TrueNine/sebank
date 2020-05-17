package DataBase.ORM;

import java.util.Objects;

/**
 * 账号表映射对象
 * 对应数据库内的账号表的一条数据
 *
 * @author hp
 * @version 1.0
 */
public class AccountORM {
    private int id;
    private String account;
    private String password;
    private int remainderMoney;

    public AccountORM() {
        this(-1);
    }

    public AccountORM(int id) {
        this(id, "none");
    }

    public AccountORM(int id, String account) {
        this(id, account, "none");
    }

    public AccountORM(int id, String account, String password) {
        this(id, account, password, 0);
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public AccountORM(int id, String account, String password, int remainderMoney) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.remainderMoney = remainderMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRemainderMoney() {
        return remainderMoney;
    }

    public void setRemainderMoney(int remainderMoney) {
        this.remainderMoney = remainderMoney;
    }

    @Override
    public String toString() {
        return "银行用户: " +
                "\t账户ID:  " + id +
                "\t账户账号:  " + account +
                "\t账户密码:  " + password +
                "\t账户余额:  " + remainderMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountORM)) return false;
        AccountORM that = (AccountORM) o;
        return getId() == that.getId() &&
                getRemainderMoney() == that.getRemainderMoney() &&
                getAccount().equals(that.getAccount()) &&
                getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccount(), getPassword(), getRemainderMoney());
    }
}
