package util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

public class UpFile {
    private static String rootPath;
    private InputStream in;
    private int id = 0;
    private String fileName;
    private float fileSize;
    private String filePath;
    private FileType fileType;
    private Timestamp uploadTime;

    public UpFile() {
    }

    public UpFile(InputStream in, String fileName, float fileSize, String rootPath) {
        this.in = in;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.rootPath = rootPath;
//        this.uploadTime = TimestampUtil.parse(new Date());
    }

    public UpFile(int id, String fileName, float fileSize, String filePath, Timestamp uploadTime) {
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.uploadTime = uploadTime;
    }

    public FileType getFileType() {
        return fileType;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public InputStream getInputStream() {
        return in;
    }

    public String getFileName() {
        return fileName;
    }

    public float getFileSize() {
        return fileSize;
    }

    public static String getRootPath() {
        return rootPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setRootPath(String rootPath) {
        UpFile.rootPath = rootPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setInputStream(InputStream in) {
        this.in = in;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(float fileSize) {
        this.fileSize = fileSize;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
