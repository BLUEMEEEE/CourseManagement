package service;

import java.io.*;
import java.util.Properties;

import dao.impl.FileDAO;
import util.FileType;
import util.UpFile;

import javax.servlet.ServletOutputStream;

public class FileManageService {
    private static FileManageService instance = new FileManageService();

    private FileManageService() {
    }

    public static FileManageService getInstance() {
        return instance;
    }

    private FileDAO fileDAO = FileDAO.getInstance();
    private static String fileDir;

    static {
        Properties prop = new Properties();
        InputStream in = FileManageService.class.getClassLoader().getResourceAsStream("conf/upload.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileDir = prop.getProperty("root");
    }

    public File getFile(int fileId, String sourceType) {    //文件不存在则返回空
        FileType fileType = null;
        switch (sourceType) {
            case "resource":
                fileType = FileType.F_RESOURCE;
                break;
            case "assignment":
                fileType = FileType.F_ASSIGNMENT;
                break;
            case "assign_submit":
                fileType = FileType.F_ASSIGNSUBMISSION;
                break;
            case "online_test":
                fileType = FileType.F_ONLINETEST;
                break;
            case "test_submit":
                fileType = FileType.F_TESTSUBMISSION;
                break;
        }
        File downloadFile = null;
        String filePath = fileDAO.getFilePath(fileId, fileType);
        if (filePath != null) {
            downloadFile = new File(filePath);
            if (!downloadFile.exists()) {
                downloadFile = null;
            }
        }
        return downloadFile;
    }

    public boolean createFile(UpFile upFile) {
        String folderName = "";
        String filePath = fileDir + "/" + getFolder(upFile.getFileType()) + upFile.getFileName();
        boolean uploadSuccess = uploadNewFile(upFile.getInputStream(), filePath);
        if (uploadSuccess) {
            upFile.setFilePath(filePath);
            if (fileDAO.createFile(upFile))
                return true;
        }
        return false;
    }

    public boolean deleteFile(int id, FileType fileType) {
        String filePath = fileDAO.getFilePath(id, fileType);
        if (filePath == null)
            return true;
        return removeFile(filePath);
    }

    public boolean updateFile(UpFile upFile) {
        String replacePath = fileDAO.getFilePath(upFile.getId(), upFile.getFileType());
        String outputPath = fileDir + "/" + getFolder(upFile.getFileType()) + upFile.getFileName();
        boolean uploadSuccess = uploadNewFile(upFile.getInputStream(), outputPath);
        if (uploadSuccess) {
            if (!replacePath.equals(outputPath) && replacePath != null) {  //若新上传文件的文件名不同且存在旧文件，则将旧文件删除
                File file = new File(replacePath);
                if (file.exists()) {
                    file.delete();
                }
            }
            upFile.setFilePath(outputPath);
            if (fileDAO.updateFile(upFile))
                return true;
        }
        return false;
    }

    private boolean uploadNewFile(InputStream in, String filePath) {        //将文件输出到对应文件夹
        FileOutputStream out;
        try {
            out = new FileOutputStream(filePath);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean removeFile(String filePath) {   //删除物理文件
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    private String getFolder(FileType fileType) {
        String folderName = "";
        switch (fileType) {
            case F_RESOURCE:
                folderName = "resource/";
                break;
            case F_ASSIGNMENT:
                folderName = "assignment/";
                break;
            case F_ONLINETEST:
                folderName = "online_test/";
                break;
            case F_ASSIGNSUBMISSION:
                folderName = "assign_submission/";
                break;
            case F_TESTSUBMISSION:
                folderName = "test_submission/";
                break;
        }
        return folderName;
    }
}

