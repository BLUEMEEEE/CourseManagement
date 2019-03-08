package servlet;

import bean.User;
import org.json.JSONObject;
import service.LoginService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService loginService = LoginService.getInstance();
        String account = request.getParameter("account");
        String passwd = request.getParameter("passwd");
        int group = Integer.parseInt(request.getParameter("group"));
        User user = loginService.specifyUser(account, passwd, group);
        int status = (user == null) ? 1 : 0;
        if (status == 0) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        }
        response.setContentType("application/json");    //设置返回类型为JSON
        response.setCharacterEncoding("utf-8");     //设置utf8,避免中文乱码

        JSONObject jsonObject = new JSONObject();   //数据格式化成JSON
        jsonObject.put("status", status);
        PrintWriter writer = response.getWriter();  //printwriter将JSON字符流返回
        writer.write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.append("GET ni ma ne");
    }
}
