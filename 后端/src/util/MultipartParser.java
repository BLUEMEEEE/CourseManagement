package util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.File;
//import java.io.OutputStream;
import java.io.OutputStream;
import java.util.List;

public class MultipartParser {
    public static MultiForm parse(HttpServletRequest request) {
        MultiForm multiForm = new MultiForm();
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            if (!ServletFileUpload.isMultipartContent(request)) {
                System.out.println("Content-Type incompatible. multipart/form-data expected.");
                return null;
            }

            List<FileItem> list = upload.parseRequest(request);
            boolean hasFile = false;
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    multiForm.pushPair(name, value);
                } else {
                    String fileName = item.getName();
                    if (fileName == null || fileName.trim().equals("")) {
                        continue;
                    }
                    InputStream in = item.getInputStream();
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1); //separator in Linux or Windows???
                    float fileSize = item.getSize() / 1024f; //KB
//                    String rootPath = request.getSession().getServletContext().getRealPath("/WEB-INF/uploads");
                    UpFile upFile = new UpFile();
                    upFile.setFileName(fileName);
                    upFile.setFileSize(fileSize);
                    upFile.setInputStream(in);
                    multiForm.setUpFile(upFile);
                    multiForm.setFileItem(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return multiForm;
        }
    }
}
