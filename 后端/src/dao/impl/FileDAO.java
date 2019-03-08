/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.IFileDAO;
import util.Conndb;
import util.FileType;
import util.UpFile;

import java.sql.*;

/**
 * @author BLUEMEEE
 */
public class FileDAO implements IFileDAO {
    private static FileDAO instance = new FileDAO();

    private FileDAO() {
    }

    public static FileDAO getInstance() {
        return instance;
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    public boolean createFile(UpFile upFile) {
        String tableName = mapTableName(upFile.getFileType());
        conn = Conndb.getConn();
        int aff_rows = 0;
        String sqlStr = String.format("insert into %s values(?,?,?,?,now())", tableName);
        try {
            stat = conn.prepareStatement(sqlStr);
            stat.setInt(1, upFile.getId());
            stat.setString(2, upFile.getFileName());
            stat.setFloat(3, upFile.getFileSize());
            stat.setString(4, upFile.getFilePath());
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return aff_rows != 0;
    }

    @Override
    public boolean updateFile(UpFile upFile) {
        conn = Conndb.getConn();
        String tableName = mapTableName(upFile.getFileType());
        String idName = mapIdName(upFile.getFileType());
        int aff_rows = 0;
        String sqlStr = String.format("update %s set file_name=?, file_size=?, file_path=?, upload_time=now() where %s=?", tableName, idName);
        try {
            stat = conn.prepareStatement(sqlStr);
            stat.setString(1, upFile.getFileName());
            stat.setFloat(2, upFile.getFileSize());
            stat.setString(3, upFile.getFilePath());
            stat.setInt(4, upFile.getId());

            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public boolean deleteFile(int id, FileType fileType) {
        String tableName = mapTableName(fileType);
        String idName = mapIdName(fileType);
        conn = Conndb.getConn();
        int aff_rows = 0;
        String sqlStr = String.format("delete from %s where %s=?", tableName, idName);
        try {
            stat = conn.prepareStatement(sqlStr);
            stat.setInt(1, id);
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return aff_rows != 0;
    }

    @Override
    public String getFilePath(int id, FileType fileType) {
        conn = Conndb.getConn();
        String tableName = mapTableName(fileType);
        String idName = mapIdName(fileType);
        String filePath = null;
        String sqlStr = String.format("select file_path from %s where %s=?", tableName, idName);
        try {
            stat = conn.prepareStatement(sqlStr);
            stat.setInt(1, id);
            ResultSet resultSet = stat.executeQuery();
            if (resultSet.next())
                filePath = resultSet.getString("file_path");
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public UpFile getUpFile(int id, FileType fileType) {
        conn = Conndb.getConn();
        String tableName = mapTableName(fileType);
        String idName = mapIdName(fileType);
        String sqlStr = String.format("select * from %s where %s=?");
        UpFile upFile = null;
        try {
            stat = conn.prepareStatement(sqlStr);
            stat.setInt(1, id);
            ResultSet resultSet = stat.executeQuery();
            if (resultSet.next()) {
                int fileId = resultSet.getInt(idName);
                String fileName = resultSet.getString("file_name");
                float fileSize = resultSet.getFloat("file_size");
                String filePath = resultSet.getString("file_path");
                Timestamp uploadTime = resultSet.getTimestamp("upload_time");
                upFile = new UpFile(fileId, fileName, fileSize, filePath, uploadTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upFile;
    }

    private String mapTableName(FileType fileType) {
        String tableName = "";
        switch (fileType) {
            case F_ONLINETEST:
                tableName = "t_online_test_file";
                break;
            case F_ASSIGNMENT:
                tableName = "t_assignment_file";
                break;
            case F_RESOURCE:
                tableName = "t_resource_file";
                break;
            case F_TESTSUBMISSION:
                tableName = "t_test_submit_file";
                break;
            case F_ASSIGNSUBMISSION:
                tableName = "t_assign_submit_file";
                break;
        }
        return tableName;
    }

    private String mapIdName(FileType fileType) {
        String idName = "";
        switch (fileType) {
            case F_ASSIGNSUBMISSION:
                idName = "assign_submit_id";
                break;
            case F_TESTSUBMISSION:
                idName = "test_submit_id";
                break;
            case F_RESOURCE:
                idName = "resource_id";
                break;
            case F_ASSIGNMENT:
                idName = "assignment_id";
                break;
            case F_ONLINETEST:
                idName = "online_test_id";
                break;
        }
        return idName;
    }
}
