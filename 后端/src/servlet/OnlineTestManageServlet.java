package servlet;

import bean.OnlineTest;
import bean.OnlineTestSubmission;
import bean.Student;
import org.json.JSONArray;
import org.json.JSONObject;
import service.OnlineTestManageService;
import util.MultiForm;
import util.MultipartParser;
import util.TimestampUtil;
import util.UpFile;

import javax.imageio.plugins.tiff.TIFFDirectory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OnlineTestManageServlet extends HttpServlet {
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
        OnlineTestManageService onlineTestManageService = OnlineTestManageService.getInstance();

        String[] urlPieces = request.getRequestURI().split("/");
        String lastPiece = urlPieces[urlPieces.length - 1];

        int status = 1;
        switch (action) {
            case "post":
                if (lastPiece.equals("online_test")) {  //添加在线测试
                    int secId = (int) request.getSession().getAttribute("sec_id");
                    String title = multiForm.getParameter("title");
                    String content = multiForm.getParameter("content");
                    Timestamp startTime = TimestampUtil.parse(multiForm.getParameter("start_time"));
                    Timestamp endTime = TimestampUtil.parse(multiForm.getParameter("end_time"));
                    UpFile upFile = multiForm.getUploadFile();
                    status = onlineTestManageService.createOnlineTest(secId, title, content, startTime, endTime, upFile);
                } else if (lastPiece.equals("test_submit")) {   //提交在线测试答案
                    int testId = Integer.parseInt(multiForm.getParameter("test_id"));
                    String content = multiForm.getParameter("content");
                    UpFile upFile = multiForm.getUploadFile();
                    Student student = (Student) request.getSession().getAttribute("user");
                    int studentId = student.getId();
                    status = onlineTestManageService.createOnlineTestSubmission(testId, studentId, content, upFile);
                }
                break;
            case "delete":  //删除在线测试
                int testId = Integer.parseInt(request.getParameter("test_id"));
                status = onlineTestManageService.deleteOnlineTest(testId);
                break;
            case "put":     //在线测试答案打分
                int submitId = Integer.parseInt(request.getParameter("submit_id"));
                float mark = Float.parseFloat(request.getParameter("mark"));
                status = onlineTestManageService.changeMark(submitId, mark);
                break;
            case "update":
                testId = Integer.parseInt(multiForm.getParameter("test_id"));
                String title = multiForm.getParameter("title");
                String content = multiForm.getParameter("content");
                Timestamp startTime = TimestampUtil.parse(multiForm.getParameter("start_time"));
                Timestamp endTime = TimestampUtil.parse(multiForm.getParameter("end_time"));
                UpFile upFile = multiForm.getUploadFile();
                status = onlineTestManageService.updateOnlineTest(testId, title, content, startTime, endTime,  upFile);
                break;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        response.getWriter().write(jsonObject.toString());
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OnlineTestManageService onlineTestManageService = OnlineTestManageService.getInstance();
        String[] urlPieces = request.getRequestURI().split("/");
        String lastPiece = urlPieces[urlPieces.length - 1];
        if (lastPiece.equals("online_test")) {  //获取在线测试列表
            int secId = (int) request.getSession().getAttribute("sec_id");
            ArrayList<OnlineTest> onlineTestArrayList = onlineTestManageService.getOnlineTestList(secId);
            JSONArray jsonArray = new JSONArray();
            if (onlineTestArrayList != null) {
                for (OnlineTest onlineTest : onlineTestArrayList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("test_id", onlineTest.getId());
                    jsonObject.put("title", onlineTest.getTitle());
                    jsonObject.put("start_time", TimestampUtil.parse(onlineTest.getStartTime()));
                    jsonObject.put("end_time", TimestampUtil.parse(onlineTest.getEndTime()));
                    jsonArray.put(jsonObject);
                }
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(jsonArray.toString());
        } else if (lastPiece.equals("test_submit")) {   //获取测试提交列表
            int testId = Integer.parseInt(request.getParameter("test_id"));
            ArrayList<OnlineTestSubmission> testSubList = onlineTestManageService.getOnlineTestSubmissionList(testId);
            JSONArray jsonArray = new JSONArray();
            if (testSubList != null) {
                for (OnlineTestSubmission testSub : testSubList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("submit_id", testSub.getId());
                    jsonObject.put("student_no", testSub.getStudentNo());
                    jsonObject.put("student_name", testSub.getStudentName());
                    jsonObject.put("mark", testSub.getMark());
                    jsonArray.put(jsonObject);
                }
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(jsonArray.toString());
        } else {
            String requestInfoType = urlPieces[urlPieces.length - 2];
            if (requestInfoType.equals("online_test")) {    //获取在线测试详细信息
                int testId = Integer.parseInt(lastPiece);
                OnlineTest onlineTest = onlineTestManageService.getOnlineTestInfo(testId);
                JSONObject jsonObject = new JSONObject();
                if (onlineTest != null) {
                    jsonObject.put("test_id", onlineTest.getId());
                    jsonObject.put("title", onlineTest.getTitle());
                    jsonObject.put("content", onlineTest.getContent());
                    jsonObject.put("start_time", TimestampUtil.parse(onlineTest.getStartTime()));
                    jsonObject.put("end_time", TimestampUtil.parse(onlineTest.getEndTime()));
                    jsonObject.put("file_flag", onlineTest.getFileFlag());
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(jsonObject.toString());
            } else if (requestInfoType.equals("test_submit")) { //获取测试提交详细信息
                int submitId = Integer.parseInt(lastPiece);
                OnlineTestSubmission onlineTestSubmission = onlineTestManageService.getOnlineTestSubmissionInfo(submitId);
                JSONObject submissionJson = new JSONObject();
                if (onlineTestSubmission != null) {
                    submissionJson.put("submit_id", onlineTestSubmission.getId());
                    submissionJson.put("student_no", onlineTestSubmission.getStudentNo());
                    submissionJson.put("student_name", onlineTestSubmission.getStudentName());
                    submissionJson.put("content", onlineTestSubmission.getContent());
                    submissionJson.put("mark", onlineTestSubmission.getMark());
                    submissionJson.put("file_flag", onlineTestSubmission.getFileFlag());
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(submissionJson.toString());
            }
        }
    }
}
