package bean;

import java.sql.Timestamp;

public class OnlineTest {
    private int id;
    private int secId;
    private String title;
    private String content;
    private Timestamp startTime;
    private Timestamp endTime;
    private int fileFlag;

    public OnlineTest(int id, int sec_id,String title, String content, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.secId = sec_id;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public OnlineTest(int sec_id,String title, String content, Timestamp startTime, Timestamp endTime) {
        this.secId = sec_id;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public OnlineTest(String title, String content, Timestamp startTime, Timestamp endTime) {
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public OnlineTest() {}

    public int getId() {
        return id;
    }

    public int getSecId() {
        return secId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSecId(int sec_id) {
        this.secId = sec_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(int fileFlag) {
        this.fileFlag = fileFlag;
    }
}
