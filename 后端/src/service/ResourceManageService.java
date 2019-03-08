package service;

import bean.Resource;
import dao.impl.ResourceDAO;
import util.FileType;
import util.UpFile;

import java.util.ArrayList;

public class ResourceManageService {
    private static ResourceManageService instance = new ResourceManageService();

    private ResourceManageService() {
    }

    public static ResourceManageService getInstance() {
        return instance;
    }

    private ResourceDAO resourceDAO = ResourceDAO.getInstance();

    private FileManageService fileManageService = FileManageService.getInstance();

    public int addResource(int secId, int resType, UpFile upFile) {
        Resource resource = new Resource();
        resource.setSecId(secId);
        resource.setType(mapResourceType(resType));
        int resId = resourceDAO.createResource(resource);
        if (resId != -1) {
            upFile.setFileType(FileType.F_RESOURCE);
            upFile.setId(resId);
            return fileManageService.createFile(upFile) ? 0 : 1;
        }
        return 1;
    }

    public int deleteResource(int resId) {
        if (fileManageService.deleteFile(resId, FileType.F_RESOURCE)) {
            return resourceDAO.deleteResource(resId) ? 0 : 1;
        }
        return 1;
    }

    public ArrayList<Resource> getResourceList(int secId, int resType) {
        return resourceDAO.retrieveResourceList(secId, mapResourceType(resType));
    }

    private String mapResourceType(int resNo) {
        String resType = "";
        switch (resNo) {
            case 0:
                resType = "courseware";     //课件
                break;
            case 1:
                resType = "test_paper";     //试卷
                break;
            case 2:
                resType = "lab_material";   //实验资料
                break;
            case 3:
                resType = "references";     //参考资料
                break;
        }
        return resType;
    }
}
