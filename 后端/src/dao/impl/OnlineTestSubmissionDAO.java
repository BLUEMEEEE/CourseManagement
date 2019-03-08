/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.OnlineTestSubmission;
import dao.IOnlineTestSubmissionDAO;
import util.Conndb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author BLUEMEEE
 */
public class OnlineTestSubmissionDAO implements IOnlineTestSubmissionDAO {
    private static OnlineTestSubmissionDAO instance = new OnlineTestSubmissionDAO();

    private OnlineTestSubmissionDAO() {
    }

    public static OnlineTestSubmissionDAO getInstance() {
        return instance;
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    @Override
    public int createOnlineTestSubmission(OnlineTestSubmission onlineTestSubmission) {
        conn = Conndb.getConn();
        int id = -1;
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("insert into t_test_submit values(null, ?, ?, ?, ?)");
            stat.setInt(1, onlineTestSubmission.getOtId());
            stat.setInt(2, onlineTestSubmission.getStudentId());
            stat.setString(3, onlineTestSubmission.getContent());
            stat.setFloat(4, -1);
            aff_rows = stat.executeUpdate();
            if (aff_rows > 0) {
                stat = conn.prepareStatement("select id from t_test_submit where id=last_insert_id()");
                ResultSet resultSet = stat.executeQuery();
                if (resultSet.next())
                    id = resultSet.getInt("id");
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean deleteOnlineTestSubmission(int subId) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("delete from t_test_submit where id=?");
            stat.setInt(1, subId);
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  aff_rows != 0;
    }

    @Override
    public boolean updateMark(int subId, float mark) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("update t_test_submit set mark=? where id=?");
            stat.setFloat(1, mark);
            stat.setInt(2, subId);
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public boolean updateContent(OnlineTestSubmission onlineTestSubmission) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("update t_test_submit set content=? where id=?");
            stat.setString(1, onlineTestSubmission.getContent());
            stat.setInt(2, onlineTestSubmission.getId());
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public ArrayList<OnlineTestSubmission> retrieveOnlineTestSubmissionList(int testId) {
        conn = Conndb.getConn();
        ArrayList<OnlineTestSubmission> testSubmitList = null;
        try {
            String sqlStr = "select otsub.id, name, account, mark " +
                    "from t_test_submit otsub inner join t_student stu on otsub.student_id = stu.id " +
                    "where online_test_id=?";
            stat = conn.prepareStatement(sqlStr);
            stat.setInt(1, testId);
            ResultSet resultSet = stat.executeQuery();
            testSubmitList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("otsub.id");
                String studentName = resultSet.getString("name");
                String studentNo = resultSet.getString("account");
                float mark = resultSet.getFloat("mark");
                OnlineTestSubmission onlinetestSubmission = new OnlineTestSubmission();
                onlinetestSubmission.setId(id);
                onlinetestSubmission.setStudentName(studentName);
                onlinetestSubmission.setStudentNo(studentNo);
                onlinetestSubmission.setMark(mark);
                testSubmitList.add(onlinetestSubmission);
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testSubmitList.isEmpty() ? null : testSubmitList;
    }

    @Override
    public OnlineTestSubmission retrieveOnlineTestSubmission(int sub_id) {
        conn = Conndb.getConn();
        OnlineTestSubmission onlineTestSubmission = null;
        try {
            String sqlStr = "select otsub.id, account, name, content, mark " +
                    "from t_student stu inner join t_test_submit otsub on stu.id=otsub.student_id " +
                    "where otsub.id=?";
            stat = conn.prepareStatement(sqlStr);
            stat.setInt(1, sub_id);
            ResultSet resultSet = stat.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("otsub.id");
                String studentName = resultSet.getString("name");
                String studentNo = resultSet.getString("account");
                String content = resultSet.getString("content");
                float mark = resultSet.getFloat("mark");
                onlineTestSubmission = new OnlineTestSubmission();
                onlineTestSubmission.setId(id);
                onlineTestSubmission.setStudentName(studentName);
                onlineTestSubmission.setStudentNo(studentNo);
                onlineTestSubmission.setContent(content);
                onlineTestSubmission.setMark(mark);
            }
            if (onlineTestSubmission != null) {
                stat = conn.prepareStatement("select count(*) as file_count from t_test_submit_file where test_submit_id=?");
                stat.setInt(1, sub_id);
                resultSet = stat.executeQuery();
                if (resultSet.next())
                    onlineTestSubmission.setFileFlag(resultSet.getInt("file_count"));
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return onlineTestSubmission;
    }
}
