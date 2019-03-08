/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.impl.FileDAO;
import org.json.JSONObject;
import org.json.JSONArray;

import java.sql.Timestamp;
import java.util.ArrayList;

import bean.Assignment;
import bean.AssignmentSubmission;
import dao.impl.AssignmentDAO;
import dao.impl.AssignmentSubmissionDAO;
import util.FileType;
import util.UpFile;

/**
 * @author BLUEMEEE
 */
public class AssignmentManageService {
    private static AssignmentManageService instance = new AssignmentManageService();

    private AssignmentManageService() {
    }

    public static AssignmentManageService getInstance() {
        return instance;
    }

    private AssignmentDAO assignmentDAO = AssignmentDAO.getInstance();
    private AssignmentSubmissionDAO assignmentSubmissionDAO = AssignmentSubmissionDAO.getInstance();

    //发布新作业
    public int createAssignment(int secId, String title, Timestamp deadline, String content, UpFile upFile) {
        Assignment bean = new Assignment();
        bean.setSecId(secId);
        bean.setTitle(title);
        bean.setContent(content);
        bean.setDeadline(deadline);
        int assId = assignmentDAO.createAssignment(bean);   //插入assignment记录
        if (assId == -1) { //DAO返回 id = -1，说明记录插入失败
            return 1;
        }
        if (upFile != null) {
            FileManageService fileManageService = FileManageService.getInstance();
            upFile.setId(assId);    //将获得的assignment_id丢进upfile
            upFile.setFileType(FileType.F_ASSIGNMENT);
            if (fileManageService.createFile(upFile)) {
                return 0;
            } else {
                assignmentDAO.deleteAssignment(assId);  //文件上传失败，原记录删除，返回 status = 1
                return 1;
            }
        } else {
            return 0;   //没有附件上传，返回 status = 0
        }

    }

    //删除作业
    public int deleteAssignment(int assignId) {
        FileManageService fileManageService = FileManageService.getInstance();
        if (fileManageService.deleteFile(assignId, FileType.F_ASSIGNMENT)) {    //删除作业文件
            boolean success = assignmentDAO.deleteAssignment(assignId);     //删除作业记录
            return success ? 0 : 1;
        }
        return 1;   //删除失败，返回 status = 1
    }

    //更新作业
    public int updateAssignment(int assignId, String title, Timestamp deadline, String content, UpFile upFile) {
        Assignment assignment = new Assignment();
        assignment.setTitle(title);
        assignment.setContent(content);
        assignment.setDeadline(deadline);
        assignment.setId(assignId);
        if (upFile != null) {       //有附件上传，更新物理文件和文件记录
            FileManageService fileManageService = FileManageService.getInstance();
            upFile.setId(assignId);
            upFile.setFileType(FileType.F_ASSIGNMENT);
            if (!fileManageService.updateFile(upFile)) {
                return 1;
            }
        }
        return assignmentDAO.updateAssignment(assignment) ? 0 : 1;
    }

    //获取作业信息
    public Assignment getAssignmentInfo(int assignId) {
        return assignmentDAO.retrieveAssignment(assignId);
    }

    public ArrayList<Assignment> getAssignmentList(int secId) {
        return assignmentDAO.retrieveAssignmentList(secId);
    }

    //创建作业提交记录
    public int createAssignmentSubmission(int assignId, int studentId, String content, UpFile upFile) {
        AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
        assignmentSubmission.setAssign_id(assignId);
        assignmentSubmission.setStudentId(studentId);
        assignmentSubmission.setContent(content);
        int subId = assignmentSubmissionDAO.createAssignmentSubmission(assignmentSubmission);
        if (subId == -1) {//创建记录失败
            return 1;
        }
//        assignmentSubmission.setId(subId);
        if (upFile != null) {
            FileManageService fileManageService = FileManageService.getInstance();
            upFile.setId(subId);
            upFile.setFileType(FileType.F_ASSIGNSUBMISSION);
            if (fileManageService.createFile(upFile)) {
                return 0;//创建文件成功
            } else {
                assignmentSubmissionDAO.deleteAssignmentSubmission(assignId);
                return 1;
            }
        }
        return 0;
    }

//    //删除作业提交记录
//    public int deleteAssignmentSubmission(int subId) {
//        FileManageService fileManageService = FileManageService.getInstance();
//        if (fileManageService.deleteFile(subId, FileType.F_ASSIGNSUBMISSION)) {
//            boolean success = assignmentSubmissionDAO.deleteAssignmentSubmission(subId);
//            return success ? 0 : 1;
//        }
//        return 1;
//    }

    //更新作业提交记录
    public int updateAssignmentSubmission(int subId, int assignId, int studentId, String content, UpFile upFile) {
        AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
        assignmentSubmission.setId(subId);
        assignmentSubmission.setAssign_id(assignId);
        assignmentSubmission.setStudentId(studentId);
        assignmentSubmission.setContent(content);
        if (upFile != null) {
            FileManageService fileManageService = FileManageService.getInstance();
            upFile.setId(subId);
            upFile.setFileType(FileType.F_ASSIGNSUBMISSION);
            if (!fileManageService.updateFile(upFile)) {
                return 1;
            }
        }
        return assignmentSubmissionDAO.updateContent(assignmentSubmission) ? 0 : 1;
    }

    //获取作业提交记录详细内容
    public AssignmentSubmission getAssignmentSubmissionInfo(int subId) {
        return assignmentSubmissionDAO.retrieveAssignmentSubmission(subId);
    }

    //获得作业提交记录列表
    public ArrayList<AssignmentSubmission> getAssignmentSubmissionList(int assignId) {
        return assignmentSubmissionDAO.retrieveAssignmentSubmissionList(assignId);
    }

    //评分
    public int changeMark(int subId, float mark) {
        return assignmentSubmissionDAO.updateMark(subId, mark) ? 0 : 1;
        //TODO:直接返回？
    }
}
