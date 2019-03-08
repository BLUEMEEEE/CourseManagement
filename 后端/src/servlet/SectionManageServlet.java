package servlet;

import bean.Section;
import bean.TimeBlock;
import bean.User;
import org.json.JSONArray;
import org.json.JSONObject;
import service.SectionManageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class SectionManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SectionManageService sectionManageService = SectionManageService.getInstance();
        String[] urlPieces = request.getRequestURI().split("/");
        String lastPiece = urlPieces[urlPieces.length - 1];
        if (lastPiece.equals("section")) {      //获取section列表
            User user = (User) request.getSession().getAttribute("user");
            ArrayList<Section> sectionArrayList = sectionManageService.getSectionList(user);
            JSONArray secJsonArray = new JSONArray();
            if (sectionArrayList != null) {
                for (Section section : sectionArrayList) {
                    JSONObject secJson = new JSONObject();
                    secJson.put("section_id", section.getId());
                    secJson.put("course_name", section.getTitle());
                    secJsonArray.put(secJson);
                }
            }
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().write(secJsonArray.toString());
        } else {                                                       //获取section信息
            int secId = Integer.parseInt(lastPiece);
            Section section = sectionManageService.getSection(secId);
            JSONObject secJson = new JSONObject();
            if (section != null) {
                secJson.put("course_id", section.getId());
                secJson.put("course_name", section.getTitle());
                secJson.put("course_intro", section.getIntroduction());
                secJson.put("course_credit", section.getCredit());
                secJson.put("section_week", new JSONArray(section.getWeek()));
                JSONArray timeSlotJson = new JSONArray();
                for (TimeBlock timeBlock : section.getTimeBlocks()) {
                    JSONObject timeBlockJson = new JSONObject();
                    timeBlockJson.put("week_day", timeBlock.getWeedDay());
                    timeBlockJson.put("period", new JSONArray(timeBlock.getPeriods()));
                    timeBlockJson.put("start_time", timeBlock.getStartTime());
                    timeBlockJson.put("end_time", timeBlock.getEndTime());
                    timeSlotJson.put(timeBlockJson);
                }
                secJson.put("time_slot", timeSlotJson);
                secJson.put("building", section.getBuilding());
                secJson.put("room", section.getRoomNumber());
                secJson.put("teacher_name", section.getTeacherName());
            }
            request.getSession().setAttribute("sec_id", secId);     //session中保存sec_id, 所有子页面的service从中取sec_id
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(secJson.toString());
        }
    }
}
