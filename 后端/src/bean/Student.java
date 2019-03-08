package bean;

public class Student extends User {
    private String name;
    private String major;
    private String grade;
    private String clas;
    private int schoolId;

    public Student(int id, String account, String passwd, String name, int schoolId, String major, String grade, String clas) {
        super(id, account, passwd);
        this.name = name;
        this.schoolId = schoolId;
        this.major = major;
        this.grade = grade;
        this.clas = clas;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public String getGrade() {
        return grade;
    }

    public String getClas() {
        return clas;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }


}
