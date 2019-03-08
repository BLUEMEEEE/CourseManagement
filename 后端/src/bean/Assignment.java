package bean;

import java.sql.Timestamp;

public class Assignment {
    private int id;
    private int sec_id;
    private String title;
    private Timestamp release_time;
    private Timestamp deadline;
    private String content;
    private int fileFlag;

    public Assignment() {

    }

    public Assignment(int id, int sec_id, String title, Timestamp release_time, Timestamp deadline, String content) {
        this.id = id;
        this.sec_id = sec_id;
        this.title = title;
        this.release_time = release_time;
        this.deadline = deadline;
        this.content = content;
    }

    public Assignment(int sec_id, String title, Timestamp release_time, Timestamp deadline, String content) {
        this.sec_id = sec_id;
        this.title = title;
        this.release_time = release_time;
        this.deadline = deadline;
        this.content = content;
    }
    
    public Assignment(String title, Timestamp release_time, Timestamp deadline, String content) {
        this.title = title;
        this.release_time = release_time;
        this.deadline = deadline;
        this.content = content;
    }


    public int getId() {
        return id;
    }
    
    public int getSecId(){
        return sec_id;
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getRelease_time() {
        return release_time;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setSecId(int sec_id){
        this.sec_id=sec_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease_time(Timestamp release_time) {
        this.release_time = release_time;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(int fileFlag) {
        this.fileFlag = fileFlag;
    }
}
