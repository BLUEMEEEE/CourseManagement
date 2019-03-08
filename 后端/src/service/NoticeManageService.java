package service;

import bean.*;
import dao.impl.NoticeDAO;

import java.util.ArrayList;

public class NoticeManageService {
    private static NoticeManageService ourInstance = new NoticeManageService();

    public static NoticeManageService getInstance() {
        return ourInstance;
    }

    private NoticeManageService() {
    }

    private NoticeDAO noticeDAO = NoticeDAO.getInstance();

    public ArrayList<Notice> getNoticeList(User user) {
        String group = "";
        if (user instanceof Admin)
            group = "admin";
        if (user instanceof Teacher)
            group = "teacher";
        if (user instanceof Student)
            group = "student";
        int userId = user.getId();
        return noticeDAO.retrieveNoticeList(userId, group);
    }

    public ArrayList<Notice> getNoticeListBySection(int secId) {
        return noticeDAO.retrieveNoticeListBySection(secId);
    }

    public int createNotice(int secId, String title, String content, int teacherId) {
        Notice notice = new Notice();
        notice.setSecId(secId);
        notice.setTitle(title);
        notice.setContent(content);
        notice.setTeacherId(teacherId);
        return noticeDAO.createNotice(notice) ? 0 : 1;
    }

    public int deleteNotice(int noticeId) {
        return noticeDAO.deleteNotice(noticeId) ? 0 : 1;
    }
}
