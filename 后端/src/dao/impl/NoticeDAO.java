package dao.impl;

import bean.Notice;
import dao.INoticeDAO;
import util.Conndb;
import util.TimestampUtil;

import java.sql.*;
import java.util.ArrayList;

public class NoticeDAO implements INoticeDAO {
    private static NoticeDAO ourInstance = new NoticeDAO();

    public static NoticeDAO getInstance() {
        return ourInstance;
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    private NoticeDAO() {
    }

    @Override
    public boolean createNotice(Notice notice) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("insert into t_notice values(null, ?, ?, ?, now(), ?)");
            if (notice.getSecId() == 0) {
                stat.setNull(1, Types.INTEGER);
            } else {
                stat.setInt(1, notice.getSecId());
            }
            stat.setString(2, notice.getTitle());
            stat.setString(3, notice.getContent());
            stat.setInt(4, notice.getTeacherId());
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public boolean deleteNotice(int noticeId) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("delete from t_notice where id=?");
            stat.setInt(1, noticeId);
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public ArrayList<Notice> retrieveNoticeList(int userId, String group) {
        conn = Conndb.getConn();
        String sqlStr = "";
        ArrayList<Notice> noticeArrayList = null;
        switch (group) {
            case "admin":
                sqlStr = "select nt.id, title, content, release_time, name " +
                        "from t_notice nt inner join t_teacher ter on nt.teacher_id=ter.id " +
                        "where teacher_id=1 order by release_time desc";   //全局公告对应的teacher_id为1
                break;
            case "student":
                sqlStr = "select nt.id, title, content, release_time, name " +
                        "from t_notice nt inner join t_teacher tea on nt.teacher_id=tea.id " +
                        "where nt.section_id in (" +
                        "select section_id from t_takes where student_id=?) or teacher_id=1 " +
                        "order by release_time desc";
                break;
            case "teacher":
                sqlStr = "select nt.id, title, content, release_time, name " +
                        "from t_notice nt inner join t_teacher tea on nt.teacher_id=tea.id " +
                        "where nt.section_id in (" +
                        "select section_id from t_teaches where teacher_id=?) or teacher_id=1 " +
                        "order by release_time desc";
                break;
        }
        try {
            stat = conn.prepareStatement(sqlStr);
            if (group.equals("teacher") || group.equals("student")) {
                stat.setInt(1, userId);
            }
            ResultSet resultSet = stat.executeQuery();
            noticeArrayList = getNoticeArrayList(resultSet);
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noticeArrayList.isEmpty() ? null : noticeArrayList;
    }


    @Override
    public ArrayList<Notice> retrieveNoticeListBySection(int secId) {
        conn = Conndb.getConn();
        ArrayList<Notice> noticeArrayList = null;
        try {
            stat = conn.prepareStatement("select nt.id, title, content, release_time, name " +
                    "from t_notice nt inner join t_teacher ter on nt.teacher_id=ter.id " +
                    "where nt.section_id=? order by release_time desc");
            stat.setInt(1, secId);
            ResultSet resultSet = stat.executeQuery();
            noticeArrayList = getNoticeArrayList(resultSet);
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noticeArrayList.isEmpty() ? null : noticeArrayList;
    }

    private ArrayList<Notice> getNoticeArrayList(ResultSet resultSet) throws SQLException {
        ArrayList<Notice> noticeArrayList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("nt.id");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            Timestamp releaseTime = resultSet.getTimestamp("release_time");
            String teacherName = resultSet.getString("name");
            Notice notice = new Notice();
            notice.setId(id);
            notice.setContent(content);
            notice.setTitle(title);
            notice.setTeacherName(teacherName);
            notice.setReleaseTime(releaseTime);
            noticeArrayList.add(notice);
        }
        return noticeArrayList;
    }
}
