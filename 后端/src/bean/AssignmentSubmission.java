package bean;

import java.sql.Timestamp;

public class AssignmentSubmission {
    private int id;
    private int assign_id;
    private int studentId;
    private String studentNo;
    private String studentName;
    private String content;
    private float mark;
    private Timestamp uploadTime;
    private int fileFlag;

    public AssignmentSubmission() {
        this.mark = -1;
    }

    public AssignmentSubmission(int id, String studentNo, String studentName, float mark) {
        this.id = id;
        this.studentNo = studentNo;
        this.studentName = studentName;
        this.mark = mark;
    }

    public AssignmentSubmission(int id, String studentNo, String content) {
        this.id = id;
        this.studentNo = studentNo;
        this.content = content;
    }

    public AssignmentSubmission(String studentNo, String content) {
        this.studentNo = studentNo;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getAssign_id() {
        return assign_id;
    }

    public String getContent() {
        return content;
    }

    public float getMark() {
        return mark;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAssign_id(int assign_id) {
        this.assign_id = assign_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public int getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(int fileFlag) {
        this.fileFlag = fileFlag;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
