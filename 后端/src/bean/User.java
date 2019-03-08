package bean;

public class User {
    private int id;
    private String account;
    private String passwd;

    public User(){

    }

    public User(int id, String account, String passwd) {
        this.id = id;
        this.account = account;
        this.passwd = passwd;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public String getPasswd() {
        return passwd;
    }

    public int getId() {
        return id;
    }
}
