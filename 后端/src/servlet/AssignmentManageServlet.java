package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;

import bean.Assignment;
import bean.AssignmentSubmission;
import bean.Student;
import org.json.JSONObject;
import org.json.JSONArray;
import service.AssignmentManageService;
import service.FileManageService;
import util.*;

public class AssignmentManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        MultiForm multiForm = null;
        if (action == null) {   //ContentType 为 multipart/form-data
            multiForm = MultipartParser.parse(request);
            if (multiForm == null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", 1);
                jsonObject.put("err_msg", "multiform parse error");
                response.getWriter().write(jsonObject.toString());
            }
            action = multiForm.getParameter("action");
        }
        AssignmentManageService assignmentManageService = AssignmentManageService.getInstance();
        String[] urlPieces = request.getRequestURI().split("/");
        String lastPiece = urlPieces[urlPieces.length - 1];

        int status = 1;
        switch (action) {
            case "post":
                if (lastPiece.equals("assignment")) {   //发布作业
                    int secId = (int) request.getSession().getAttribute("sec_id");
                    String title = multiForm.getParameter("title");
                    String content = multiForm.getParameter("content");
//                    Timestamp releaseTime = TimestampUtil.parse(multiForm.getParameter("release_time"));
                    Timestamp deadline = TimestampUtil.parse(multiForm.getParameter("deadline"));
                    UpFile upFile = multiForm.getUploadFile();
                    status = assignmentManageService.createAssignment(secId, title, deadline, content, upFile);
                } else if (lastPiece.equals("assign_submit")) { //提交作业，创建提交记录
//                    MultiForm multiForm = MultipartParser.parse(request);
                    int assignId = Integer.parseInt(multiForm.getParameter("assignment_id"));
                    String content = multiForm.getParameter("content");
                    Student student = (Student) request.getSession().getAttribute("user");
                    int studentId = student.getId();
                    UpFile upFile = multiForm.getUploadFile();
                    status = assignmentManageService.createAssignmentSubmission(assignId, studentId, content, upFile);
                }
                break;
            case "delete":  //删除作业
                int assignId = Integer.parseInt(request.getParameter("assign_id"));
                status = assignmentManageService.deleteAssignment(assignId);
                break;
            case "put": //作业评分
                int submitId = Integer.parseInt(request.getParameter("sub_id"));
                float mark = Float.parseFloat(request.getParameter("mark"));
                status = assignmentManageService.changeMark(submitId, mark);
                break;
            case "update":  //修改作业信息
//                MultiForm multiForm = MultipartParser.parse(request);
                assignId = Integer.parseInt(multiForm.getParameter("assign_id"));
                String title = multiForm.getParameter("title");
                String content = multiForm.getParameter("content");
//                Timestamp releaseTime = TimestampUtil.parse(multiForm.getParameter("release_time"));
                Timestamp deadline = TimestampUtil.parse(multiForm.getParameter("deadline"));
                UpFile upFile = multiForm.getUploadFile();
                status = assignmentManageService.updateAssignment(assignId, title, deadline, content, upFile);
                break;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AssignmentManageService assignmentManageService = AssignmentManageService.getInstance();
        String[] urlPieces = request.getRequestURI().split("/");
        String lastPiece = urlPieces[urlPieces.length - 1];
        if (lastPiece.equals("assignment")) {        //获取作业列表
            int secId = (int) request.getSession().getAttribute("sec_id");
            ArrayList<Assignment> assignmentArrayList = assignmentManageService.getAssignmentList(secId);
            JSONArray jsonArray = new JSONArray();
            if (assignmentArrayList != null) {
                for (Assignment assignment : assignmentArrayList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("assign_id", assignment.getId());
                    jsonObject.put("title", assignment.getTitle());
                    jsonObject.put("release_time", TimestampUtil.parse(assignment.getRelease_time()));
                    jsonObject.put("deadline", TimestampUtil.parse(assignment.getDeadline()));
                    jsonArray.put(jsonObject);
                }
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(jsonArray.toString());
        } else if (lastPiece.equals("assign_submit")) { //获取作业提交列表
            int assignId = Integer.parseInt(request.getParameter("ass_id"));
            ArrayList<AssignmentSubmission> assignSubmList = assignmentManageService.getAssignmentSubmissionList(assignId);
            JSONArray jsonArray = new JSONArray();
            if (assignSubmList != null) {
                for (AssignmentSubmission assignSub : assignSubmList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("submit_id", assignSub.getId());
                    jsonObject.put("student_no", assignSub.getStudentNo());
                    jsonObject.put("student_name", assignSub.getStudentName());
                    jsonObject.put("mark", assignSub.getMark());
                    jsonArray.put(jsonObject);
                }
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(jsonArray.toString());
        } else {
            String requestInfoType = urlPieces[urlPieces.length - 2];
            if (requestInfoType.equals("assignment")) { //获取作业详细信息
                int assignId = Integer.parseInt(lastPiece);
                Assignment assignment = assignmentManageService.getAssignmentInfo(assignId);
                JSONObject assignJson = new JSONObject();
                if (assignment != null) {
                    assignJson.put("assign_id", assignment.getId());
                    assignJson.put("title", assignment.getTitle());
                    assignJson.put("release_time", TimestampUtil.parse(assignment.getRelease_time()));
                    assignJson.put("deadline", TimestampUtil.parse(assignment.getDeadline()));
                    assignJson.put("content", assignment.getContent());
                    assignJson.put("file_flag", assignment.getFileFlag());
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(assignJson.toString());
            } else if (requestInfoType.equals("assign_submit")) {  //获取作业提交详细信息
                int submitId = Integer.parseInt(lastPiece);
                AssignmentSubmission assignmentSubmission = assignmentManageService.getAssignmentSubmissionInfo(submitId);
                JSONObject submissionJson = new JSONObject();
                if (assignmentSubmission != null) {
                    submissionJson.put("submit_id", assignmentSubmission.getId());
                    submissionJson.put("student_no", assignmentSubmission.getStudentNo());
                    submissionJson.put("student_name", assignmentSubmission.getStudentName());
                    submissionJson.put("content", assignmentSubmission.getContent());
                    submissionJson.put("mark", assignmentSubmission.getMark());
                    submissionJson.put("file_flag", assignmentSubmission.getFileFlag());
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(submissionJson.toString());
            }
        }
    }
}

