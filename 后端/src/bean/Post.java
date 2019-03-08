package bean;

import java.sql.Timestamp;

public class Post {
    private int id;
    private String title;
    private String description;
    private String author;
    private Timestamp releaseTime;

    public Post(int id, String title, String description, String author, Timestamp releaseTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.releaseTime = releaseTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Timestamp releaseTime) {
        this.releaseTime = releaseTime;
    }
}
