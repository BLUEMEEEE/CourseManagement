package bean;

public class Teacher extends User{
    private String name;

    public Teacher(int id, String account, String passwd, String name) {
        super(id, account, passwd);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
