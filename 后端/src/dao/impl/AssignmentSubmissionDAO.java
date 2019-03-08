/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.AssignmentSubmission;
import dao.IAssignmentSubmissionDAO;
import util.Conndb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BLUEMEEE
 */

public class AssignmentSubmissionDAO implements IAssignmentSubmissionDAO {
    private static AssignmentSubmissionDAO instance = new AssignmentSubmissionDAO();

    private AssignmentSubmissionDAO() {
    }

    public static AssignmentSubmissionDAO getInstance() {
        return instance;
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    @Override
    public int createAssignmentSubmission(AssignmentSubmission assignmentSubmission) {
        conn = Conndb.getConn();
        int id = -1;
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("insert into t_assign_submit values(null,?,?,?,?)");
            stat.setInt(1, assignmentSubmission.getAssign_id());
            stat.setInt(2, assignmentSubmission.getStudentId());
            stat.setString(3, assignmentSubmission.getContent());
            stat.setFloat(4, -1); //mark
            aff_rows = stat.executeUpdate();
            if (aff_rows > 0) {
                stat = conn.prepareStatement("select id from t_assign_submit where id = last_insert_id()");
                ResultSet resultSet = stat.executeQuery();
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean deleteAssignmentSubmission(int subId) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("delete from t_assign_submit where id=?");
            stat.setInt(1, subId);
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public boolean updateContent(AssignmentSubmission assignmentSubmission) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("update t_assign_submit set content=? where id=?");
            stat.setString(1, assignmentSubmission.getContent());
            stat.setInt(2, assignmentSubmission.getId());
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public boolean updateMark(int sub_id, float mark) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("update t_assign_submit set mark=? where id=?");
            stat.setFloat(1, mark);
            stat.setInt(2, sub_id);
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public ArrayList<AssignmentSubmission> retrieveAssignmentSubmissionList(int ass_id) {
        conn = Conndb.getConn();
        ArrayList<AssignmentSubmission> assignSubmitList = null;
        try {
            String sqlStr = "select asub.id, name, account, mark " +
                    "from t_assign_submit asub inner join t_student stu on asub.student_id = stu.id " +
                    "where assignment_id=?";
            stat = conn.prepareStatement(sqlStr);
            stat.setInt(1, ass_id);
            ResultSet resultSet = stat.executeQuery();
            assignSubmitList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("asub.id");
                String studentName = resultSet.getString("name");
                String studentNo = resultSet.getString("account");
                float mark = resultSet.getFloat("mark");
                AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
                assignmentSubmission.setId(id);
                assignmentSubmission.setStudentName(studentName);
                assignmentSubmission.setStudentNo(studentNo);
                assignmentSubmission.setMark(mark);
                assignSubmitList.add(assignmentSubmission);
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignSubmitList.isEmpty() ? null : assignSubmitList;
    }

    @Override
    public AssignmentSubmission retrieveAssignmentSubmission(int sub_id) {
        conn = Conndb.getConn();
        AssignmentSubmission assignmentSubmission = null;
        try {
            String sqlStr = "select assub.id, account, name, content, mark " +
                    "from t_student stu inner join t_assign_submit assub on stu.id=assub.student_id " +
                    "where assub.id=?";
            stat = conn.prepareStatement(sqlStr);
            stat.setInt(1, sub_id);
            ResultSet resultSet = stat.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("assub.id");
                String studentName = resultSet.getString("name");
                String studentNo = resultSet.getString("account");
                String content = resultSet.getString("content");
                float mark = resultSet.getFloat("mark");
                assignmentSubmission = new AssignmentSubmission();
                assignmentSubmission.setId(id);
                assignmentSubmission.setStudentName(studentName);
                assignmentSubmission.setStudentNo(studentNo);
                assignmentSubmission.setContent(content);
                assignmentSubmission.setMark(mark);
            }
            stat = conn.prepareStatement("select count(*) as file_count from t_assign_submit_file where assign_submit_id=?");
            stat.setInt(1, sub_id);
            resultSet = stat.executeQuery();
            if (resultSet.next())
                assignmentSubmission.setFileFlag(resultSet.getInt("file_count"));
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignmentSubmission;
    }
}
