/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.FileInputStream;
import java.sql.Timestamp;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

import bean.OnlineTest;
import bean.OnlineTestSubmission;
import dao.IOnlineTestDAO;
import dao.IOnlineTestSubmissionDAO;
import dao.impl.OnlineTestDAO;
import dao.impl.OnlineTestSubmissionDAO;
import util.FileType;
import util.UpFile;

import javax.print.attribute.standard.MediaSize;

/**
 * @author BLUEMEEE
 */
public class OnlineTestManageService {
    private static OnlineTestManageService instance = new OnlineTestManageService();

    private OnlineTestManageService() {
    }

    public static OnlineTestManageService getInstance() {
        return instance;
    }

    private OnlineTestDAO onlineTestDAO = OnlineTestDAO.getInstance();
    private OnlineTestSubmissionDAO onlineTestSubmissionDAO = OnlineTestSubmissionDAO.getInstance();

    public int createOnlineTest(int sec_id, String title, String content, Timestamp startTime, Timestamp endTime, UpFile upFile) {
        OnlineTest bean = new OnlineTest(sec_id, title, content, startTime, endTime);

        int otId = onlineTestDAO.createOnlineTest(bean);
        if (otId == -1) {
            return 1;
        }
        if (upFile != null) {
            FileManageService fileManageService = FileManageService.getInstance();
            upFile.setId(otId);
            upFile.setFileType(FileType.F_ONLINETEST);
            if (fileManageService.createFile(upFile))
                return 0;
            else {
                onlineTestDAO.deleteOnlineTest(otId);
                return 1;
            }
        } else
            return 0;
    }

    //删除在线测试
    public int deleteOnlineTest(int otId) {
        FileManageService fileManageService = FileManageService.getInstance();
        if (fileManageService.deleteFile(otId, FileType.F_ONLINETEST)) {
            boolean success = onlineTestDAO.deleteOnlineTest(otId);
            return success ? 0 : 1;
        }
        return 1;
    }

    public int updateOnlineTest(int testId, String title, String content, Timestamp startTime, Timestamp endTime, UpFile upFile) {
        if (upFile != null) {
            FileManageService fileManageService = FileManageService.getInstance();
            upFile.setId(testId);
            upFile.setFileType(FileType.F_ONLINETEST);
            if (!fileManageService.updateFile(upFile)) {
                return 1;
            }
        }
        OnlineTest onlineTest = new OnlineTest(title, content, startTime, endTime);
        onlineTest.setId(testId);
        return onlineTestDAO.updateOnlineTest(onlineTest) ? 0 : 1;
    }

    public OnlineTest getOnlineTestInfo(int testId) {
        return onlineTestDAO.retrieveOnlineTest(testId);
    }

    public ArrayList<OnlineTest> getOnlineTestList(int secId) {
        return onlineTestDAO.retrieveOnlineTestList(secId);
    }

    public int createOnlineTestSubmission(int testId, int student_id, String content, UpFile upFile) {
        OnlineTestSubmission testSubmission = new OnlineTestSubmission();
        testSubmission.setOtId(testId);
        testSubmission.setStudentId(student_id);
        testSubmission.setContent(content);
        int submitId = onlineTestSubmissionDAO.createOnlineTestSubmission(testSubmission);
        if (submitId == -1) {
            return -1;
        }
        if (upFile != null) {
            FileManageService fileManageService = FileManageService.getInstance();
            upFile.setId(submitId);
            upFile.setFileType(FileType.F_TESTSUBMISSION);
            if (fileManageService.createFile(upFile))
                return 0;
            else {
                onlineTestSubmissionDAO.deleteOnlineTestSubmission(submitId);
                return 1;
            }
        }
        return 0;
    }

    public int updateOnlineTestSubmission(int submitId, int testId, int studentId, String content, UpFile upFile) {
        OnlineTestSubmission testSubmission = new OnlineTestSubmission();
        testSubmission.setId(submitId);
        testSubmission.setOtId(testId);
        testSubmission.setStudentId(studentId);
        testSubmission.setContent(content);
        if (upFile != null) {
            FileManageService fileManageService = FileManageService.getInstance();
            upFile.setId(submitId);
            upFile.setFileType(FileType.F_TESTSUBMISSION);
            if (!fileManageService.updateFile(upFile))
                return 1;
        }
        return onlineTestSubmissionDAO.updateContent(testSubmission) ? 0 : 1;
    }

    public int changeMark(int submitId, float mark) {
        return onlineTestSubmissionDAO.updateMark(submitId, mark) ? 0 : 1;
    }

    public OnlineTestSubmission getOnlineTestSubmissionInfo(int submitId) {
        return onlineTestSubmissionDAO.retrieveOnlineTestSubmission(submitId);
    }

    public ArrayList<OnlineTestSubmission> getOnlineTestSubmissionList(int testId) {
        return onlineTestSubmissionDAO.retrieveOnlineTestSubmissionList(testId);
    }
}