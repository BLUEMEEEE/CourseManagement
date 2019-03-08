package service;

import bean.*;
import dao.impl.SectionDAO;

import java.util.ArrayList;

public class SectionManageService {
    private static SectionManageService ourInstance = new SectionManageService();

    public static SectionManageService getInstance() {
        return ourInstance;
    }

    private SectionManageService() {
    }

    private SectionDAO sectionDAO = SectionDAO.getInstance();

    public ArrayList<Section> getSectionList(User user) {
        ArrayList<Section> sectionArrayList = null;
        if (user instanceof Teacher) {
            sectionArrayList = sectionDAO.retrieveSectionList(user.getId(), "teacher");
        }
        if (user instanceof Student) {
            sectionArrayList = sectionDAO.retrieveSectionList(user.getId(), "student");
        }
        if (user instanceof Admin) {
            sectionArrayList = sectionDAO.retrieveSectionList(user.getId(), "admin");
        }
        return sectionArrayList;
    }

    public Section getSection(int secId) {
        return sectionDAO.retrieveSection(secId);
    }
}
