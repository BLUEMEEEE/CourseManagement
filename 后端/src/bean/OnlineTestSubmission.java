package bean;

import java.sql.Timestamp;

public class OnlineTestSubmission {
    private int id;
    private int otId;
    private int studentId;
    private String studentNo;
    private String studentName;
    private String content;
    private float mark;
    private Timestamp uploadTime;
    private int fileFlag;

    public OnlineTestSubmission() {
    }

    public OnlineTestSubmission(int id, int otId, String studentNo, String content, float mark) {
        this.id = id;
        this.otId = otId;
        this.studentNo = studentNo;
        this.content = content;
        this.mark = mark;
    }

    public OnlineTestSubmission(int id, int otId, String studentNo, String content) {
        this.id = id;
        this.otId = otId;
        this.studentNo = studentNo;
        this.content = content;
    }

    public OnlineTestSubmission(int otId, String studentNo, String content) {
        this.otId = otId;
        this.studentNo = studentNo;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOtId() {
        return otId;
    }

    public void setOtId(int otId) {
        this.otId = otId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(int fileFlag) {
        this.fileFlag = fileFlag;
    }
}
