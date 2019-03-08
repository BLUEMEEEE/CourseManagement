package bean;

import java.sql.Timestamp;

public class Reply {
    private int id;
    private String author;
    private String content;
    private Timestamp releaseTime;

    public Reply(int id, String author, String content, Timestamp releaseTime) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.releaseTime = releaseTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Timestamp releaseTime) {
        this.releaseTime = releaseTime;
    }
}
