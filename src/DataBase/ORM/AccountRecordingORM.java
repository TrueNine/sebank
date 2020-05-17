package DataBase.ORM;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 交易记录表的映射对象
 * 对应用户记录表的所有一条数据
 *
 * @author hp
 * @version 1.0
 */
public class AccountRecordingORM {
    private int id;
    private String type;
    private int money;
    private java.sql.Timestamp time;
    private int accountId;

    public AccountRecordingORM() {

    }

    public AccountRecordingORM(int id, String type, int money, Timestamp time, int accountId) {
        this.id = id;
        this.type = type;
        this.money = money;
        this.time = time;
        this.accountId = accountId;
    }

    public AccountRecordingORM(String type, int money, Timestamp time, int accountId) {
        this.type = type;
        this.money = money;
        this.time = time;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "记录:  " +
                "  编号: " + id +
                "  类型:  " + type +
                "  额度:  " + money +
                "  时间:  " + time +
                "  操作账户:  :  " + accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountRecordingORM)) return false;
        AccountRecordingORM that = (AccountRecordingORM) o;
        return getId() == that.getId() &&
                Double.compare(that.getMoney(), getMoney()) == 0 &&
                getAccountId() == that.getAccountId() &&
                getType().equals(that.getType()) &&
                getTime().equals(that.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getMoney(), getTime(), getAccountId());
    }
}
