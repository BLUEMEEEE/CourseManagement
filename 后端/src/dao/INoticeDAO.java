package dao;

import bean.Notice;

import java.util.ArrayList;

public interface INoticeDAO {
    public boolean createNotice(Notice notice);
    public boolean deleteNotice(int noticeId);
    public ArrayList<Notice> retrieveNoticeList(int userId, String group);
    public ArrayList<Notice> retrieveNoticeListBySection(int secId);
}
