package bean;

import util.UpFile;

import java.sql.Timestamp;

public class Resource {
    private int id;
    private int secId;
    private String type;
    private UpFile upFile;

    public Resource() {

    }

    public Resource(int id, int secId, String type) {
        this.id = id;
        this.secId = secId;
        this.type = type;
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

    public int getSecId() {
        return secId;
    }

    public void setSecId(int secId) {
        this.secId = secId;
    }

    public UpFile getUpFile() {
        return upFile;
    }

    public void setUpFile(UpFile upFile) {
        this.upFile = upFile;
    }
}
