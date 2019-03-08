package servlet;

import bean.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import service.ResourceManageService;
import util.MultiForm;
import util.MultipartParser;
import util.UpFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ResourceManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResourceManageService resourceManageService = ResourceManageService.getInstance();
        String action = request.getParameter("action");
        MultiForm multiForm = null;
        if (action == null) {
            multiForm = MultipartParser.parse(request);
            if (multiForm == null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", 1);
                jsonObject.put("err_msg", "multiform parse error");
                response.getWriter().write(jsonObject.toString());
            }
            action = multiForm.getParameter("action");
        }
        int status = 1;
        switch (action) {
            case "post":
                int resType = Integer.parseInt(multiForm.getParameter("res_type"));
                int secId = (int) request.getSession().getAttribute("sec_id");
                UpFile upFile = multiForm.getUploadFile();
                status = resourceManageService.addResource(secId, resType, upFile);
                break;
            case "delete":
                int resId = Integer.parseInt(request.getParameter("res_id"));
                status = resourceManageService.deleteResource(resId);
                break;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResourceManageService resourceManageService = ResourceManageService.getInstance();
        int secId = (int) request.getSession().getAttribute("sec_id");
        int resType = Integer.parseInt(request.getParameter("res_type"));
        ArrayList<Resource> resourceArrayList = resourceManageService.getResourceList(secId, resType);

        JSONArray jsonArray = new JSONArray();
        if (resourceArrayList != null) {
            for (Resource resource : resourceArrayList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("res_name", resource.getUpFile().getFileName());
                jsonObject.put("res_id", resource.getId());
                jsonArray.put(jsonObject);
            }
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().write(jsonArray.toString());
    }
}
