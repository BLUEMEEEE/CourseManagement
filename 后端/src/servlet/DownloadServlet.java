package servlet;

import com.sun.net.httpserver.HttpServer;
import service.FileManageService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.net.URLEncoder;

public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String[] urlPieces = request.getRequestURI().split("/");
        String sourceType = urlPieces[urlPieces.length - 2];

        FileManageService fileManageService = FileManageService.getInstance();
        File downloadFile = fileManageService.getFile(id, sourceType);  //通过FileManageService获取文件
        if (downloadFile != null) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            String fileName = downloadFile.getName();
//            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "utf-8"));
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            FileInputStream fileIn = new FileInputStream(downloadFile);
            ServletOutputStream servletOut = response.getOutputStream();

            byte[] bytes = new byte[4096];
            while (fileIn.read(bytes, 0, 4096) != -1) {
                servletOut.write(bytes, 0, 4096);
            }
            fileIn.close();
            servletOut.flush();
            servletOut.close();
        }
    }
}
