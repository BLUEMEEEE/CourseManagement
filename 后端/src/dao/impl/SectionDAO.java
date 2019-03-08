package dao.impl;

import bean.Section;
import bean.TimeBlock;
import dao.ISectionDAO;
import util.Conndb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SectionDAO implements ISectionDAO {
    private static SectionDAO instance = new SectionDAO();
    private SectionDAO() {}
    public static SectionDAO getInstance() {
        return instance;
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    @Override
    public boolean createSection(Section section) {
        return false;
    }

    @Override
    public boolean deleteSection(int secId) {
        return false;
    }

    @Override
    public boolean updateSection(Section section) {
        return false;
    }

    @Override
    public ArrayList<Section> retrieveSectionList(int id, String group) {
        conn = Conndb.getConn();
        ArrayList<Section> sectionArrayList = null;
        String sqlStr = "";
        switch (group) {
            case "admin":
                sectionArrayList = getSectionListByAdmin();
                break;
            case "student":
                sqlStr = "select section_id, title from t_takes t inner join t_course c on t.course_id=c.id where student_id=?";
                break;
            case "teacher":
                sqlStr = "select section_id, title from t_teaches t inner join t_course c on t.course_id=c.id where teacher_id=?";
                break;
        }
        try {
            stat = conn.prepareStatement(sqlStr);
            if (group.equals("student") || group.equals("teacher")) {
                stat.setInt(1, id);
                ResultSet resultSet = stat.executeQuery();
                sectionArrayList = new ArrayList<>();
                while (resultSet.next()) {
                    Section section = new Section();
                    section.setId(resultSet.getInt("section_id"));
                    section.setTitle(resultSet.getString("title"));
                    sectionArrayList.add(section);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectionArrayList.isEmpty() ? null : sectionArrayList;
    }

    private ArrayList<Section> getSectionListByAdmin() {
        ArrayList<Section> sectionArrayList = null;
        String sqlStr = "select section_id, course_id, title, name, teacher_id from " +
                "t_takes ta inner join t_teacher te on ta.teacher_id=te.id " +
                "inner join t_course c on c.id=ta.course_id";
        try {
            stat = conn.prepareStatement(sqlStr);
            ResultSet resultSet = stat.executeQuery();
            while (resultSet.next()) {
                //怎么封装数据啊，干特么ORM
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectionArrayList;
    }


    @Override
    public Section retrieveSection(int secId) {
        conn = Conndb.getConn();
        Section section = null;
        try {
            String sqlStr = "select year, semester, week, time_slot, room_building, room_no, title, introduction, credit " +
                            "from t_section s inner join t_course c on s.course_id = c.id where s.id=?";
            stat = conn.prepareStatement(sqlStr);
            stat.setInt(1, secId);
            ResultSet resultSet = stat.executeQuery();
            if (resultSet.next()) {
                String year = resultSet.getString("year");
                String semester = resultSet.getString("semester");
                ArrayList<Integer> week = parseWeek(resultSet.getString("week"));
                ArrayList<TimeBlock> timeBlocks = parseTimeBlock(resultSet.getString("time_slot"));
                String building = resultSet.getString("room_building");
                String roomNo = resultSet.getString("room_no");
                String title = resultSet.getString("title");
                String introduction = resultSet.getString("introduction");
                int credit = resultSet.getInt("credit");
                for (TimeBlock timeBlk : timeBlocks) {
                    stat = conn.prepareStatement("select * from t_time_slot where id=? or id=?");
                    stat.setInt(1, timeBlk.getPeriods().get(0));
                    stat.setInt(2, timeBlk.getPeriods().get(timeBlk.getPeriods().size() - 1));
                    resultSet = stat.executeQuery();
                    if (resultSet.next())
                        timeBlk.setStartTime(resultSet.getTime("start_time"));
                    if (resultSet.next())
                        timeBlk.setEndTime(resultSet.getTime("end_time"));
                }
                section = new Section(secId, title, introduction, credit, year, semester, timeBlocks, week, building, roomNo);
            } else
                return null;

            stat = conn.prepareStatement("select tr.name from t_teaches ts inner join t_teacher tr on ts.teacher_id=tr.id where ts.section_id=?");
            stat.setInt(1, secId);
            resultSet = stat.executeQuery();
            if (resultSet.next()) {
                String teacherName = resultSet.getString("name");
                section.setTeacherName(teacherName);
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }

    private ArrayList<TimeBlock> parseTimeBlock(String timeBlockStr) {
        ArrayList<TimeBlock> timeBlockArrayList = new ArrayList<TimeBlock>();
        String[] blockStrs = timeBlockStr.split(";");
        for(String blockStr : blockStrs) {
            int week = Integer.parseInt(blockStr.split("_")[0]);
            String[] periodStr =  blockStr.split("_")[1].split(",");
            ArrayList<Integer> periods = new ArrayList<Integer>();
            for (String per : periodStr) {
                periods.add(Integer.parseInt(per));
            }
            timeBlockArrayList.add(new TimeBlock(week, periods));
        }
        return timeBlockArrayList;
    }

    private ArrayList<Integer> parseWeek(String weeksStr) {
        ArrayList<Integer> weekArrayList = new ArrayList<Integer>();
        String[] weekStrs = weeksStr.split(",");
        for (String weekStr : weekStrs) {
            weekArrayList.add(Integer.parseInt(weekStr));
        }
        return weekArrayList;
    }
}
