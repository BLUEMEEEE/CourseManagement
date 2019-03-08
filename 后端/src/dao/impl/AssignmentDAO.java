/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Assignment;
import dao.IAssignmentDAO;

import java.sql.*;
import java.util.ArrayList;

import util.Conndb;

/**
 * @author BLUEMEEE
 */
public class AssignmentDAO implements IAssignmentDAO {
    private static AssignmentDAO instance = new AssignmentDAO();

    private AssignmentDAO() {
    }

    public static AssignmentDAO getInstance() {
        return instance;
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    @Override
    public int createAssignment(Assignment assignment) {
        conn = Conndb.getConn();
        int id = -1;
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("insert into t_assignment values(null,?,?,now(),?,?)");
            stat.setInt(1, assignment.getSecId());
            stat.setString(2, assignment.getTitle());
//            stat.setTimestamp(3, assignment.getRelease_time());
            stat.setTimestamp(3, assignment.getDeadline());
            stat.setString(4, assignment.getContent());
            aff_rows = stat.executeUpdate();
            if (aff_rows > 0) {
                stat = conn.prepareStatement("select id from t_assignment where id = last_insert_id()");
                ResultSet result = stat.executeQuery();
                if (result.next())
                    id = result.getInt("id");
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean deleteAssignment(int assign_id) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("delete from t_assignment where id=?");
            stat.setInt(1, assign_id);
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public boolean updateAssignment(Assignment assignment) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("update t_assignment set title=?, content=?, release_time=now(), deadline=? where id =?");
            stat.setString(1, assignment.getTitle());
            stat.setString(2, assignment.getContent());
//            stat.setTimestamp(3, assignment.getRelease_time());
            stat.setTimestamp(3, assignment.getDeadline());
            stat.setInt(4, assignment.getId());
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public Assignment retrieveAssignment(int assign_id) {
        conn = Conndb.getConn();
        Assignment assignment = null;
        try {
            stat = conn.prepareStatement("select * from t_assignment where id=?");
            stat.setInt(1, assign_id);
            ResultSet result = stat.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
//                int secId = result.getInt("sec_id");
                String title = result.getString("title");
                Timestamp releaseTime = result.getTimestamp("release_time");
                Timestamp deadline = result.getTimestamp("deadline");
                String content = result.getString("content");
                assignment = new Assignment(title, releaseTime, deadline, content);
                assignment.setId(id);
            }
            if (assignment != null) {
                stat = conn.prepareStatement("select count(*) as file_count from t_assignment_file where assignment_id=?");
                stat.setInt(1, assign_id);
                result = stat.executeQuery();
                if (result.next())
                    assignment.setFileFlag(result.getInt("file_count"));
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignment;
    }

    @Override
    public ArrayList<Assignment> retrieveAssignmentList(int sec_id) {
        conn = Conndb.getConn();
        ArrayList<Assignment> list = null;
        try {
            stat = conn.prepareStatement("select * from t_assignment where section_id=? order by release_time");
            stat.setInt(1, sec_id);
            ResultSet result = stat.executeQuery();
            list = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                Timestamp releaseTime = result.getTimestamp("release_time");
                Timestamp deadline = result.getTimestamp("deadline");
                String content = result.getString("content");
                Assignment bean = new Assignment(title, releaseTime, deadline, content);
                bean.setId(id);
                list.add(bean);                         //内存会被回收吗
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.isEmpty() ? null : list;
    }
}
