package servlet;

import bean.*;
import org.json.JSONArray;
import org.json.JSONObject;
import service.NoticeManageService;
import util.TimestampUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class NoticeManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NoticeManageService noticeManageService = NoticeManageService.getInstance();
        String action = request.getParameter("action");
        User user = (User) request.getSession().getAttribute("user");
        int status = 1;
        switch (action) {
            case "post":
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                int secId = 0;  //0在dao被视为null
                if (user instanceof Teacher) {
                    secId = (int) request.getSession().getAttribute("sec_id");
                }
                int userId = user.getId();
                if (user instanceof Admin)  //全局公告对应的teacher_id为1
                    userId = 1;
                status = noticeManageService.createNotice(secId, title, content, userId);
                break;
            case "delete":
                int noticeId = Integer.parseInt(request.getParameter("notice_id"));
                status = noticeManageService.deleteNotice(noticeId);
                break;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NoticeManageService noticeManageService = NoticeManageService.getInstance();
        int allFlag = Integer.parseInt(request.getParameter("all"));
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.getWriter().println("Unable to obtain user in session.");
        }
        ArrayList<Notice> noticeArrayList;
        if (allFlag == 1) {     //返回所有公告
            noticeArrayList = noticeManageService.getNoticeList(user);
        }
        else {          //返回课程公告
            int secId = (int) request.getSession().getAttribute("sec_id");
            noticeArrayList = noticeManageService.getNoticeListBySection(secId);
        }

        JSONArray jsonArray = new JSONArray();
        if (noticeArrayList != null) {
            for (Notice notice : noticeArrayList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("notice_id", notice.getId());
                jsonObject.put("title", notice.getTitle());
                jsonObject.put("content", notice.getContent());
                jsonObject.put("release_time", TimestampUtil.parse(notice.getReleaseTime()));
                jsonObject.put("teacher_name", notice.getTeacherName());
                jsonArray.put(jsonObject);
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(jsonArray.toString());
    }
}
