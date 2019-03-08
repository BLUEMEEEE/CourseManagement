/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.OnlineTest;
import dao.IOnlineTestDAO;
import util.Conndb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author BLUEMEEE
 */
public class OnlineTestDAO implements IOnlineTestDAO {
    private static OnlineTestDAO instance = new OnlineTestDAO();

    private OnlineTestDAO() {
    }

    public static OnlineTestDAO getInstance() {
        return instance;
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    @Override
    public int createOnlineTest(OnlineTest onlineTest) {
        conn = Conndb.getConn();
        int id = -1;
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("insert into t_online_test values(null,?,?,?,?,?)");
            stat.setString(1, onlineTest.getTitle());
            stat.setString(2, onlineTest.getContent());
            stat.setInt(3, onlineTest.getSecId());
            stat.setTimestamp(4, onlineTest.getStartTime());
            stat.setTimestamp(5, onlineTest.getEndTime());
            aff_rows = stat.executeUpdate();
            if (aff_rows > 0) {
                stat = conn.prepareStatement("select id from t_online_test where id = last_insert_id()");
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
    public boolean deleteOnlineTest(int OT_id) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("delete from t_online_test where id=? ");
            stat.setInt(1, OT_id);
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public boolean updateOnlineTest(OnlineTest onlineTest) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("update t_online_test set title=?, content=?, start_time=?, end_time=? where id=?");
            stat.setString(1, onlineTest.getTitle());
            stat.setString(2, onlineTest.getContent());
            stat.setTimestamp(3, onlineTest.getStartTime());
            stat.setTimestamp(4, onlineTest.getEndTime());
            stat.setInt(5, onlineTest.getId());
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public OnlineTest retrieveOnlineTest(int OT_id) {
        conn = Conndb.getConn();
        OnlineTest onlineTest = null;
        try {
            stat = conn.prepareStatement("select * from t_online_test where id=?");
            stat.setInt(1, OT_id);
            ResultSet result = stat.executeQuery();
            if (result.next()) {
                String title = result.getString("title");
                String content = result.getString("content");
                Timestamp startTime = result.getTimestamp("start_time");
                Timestamp endTime = result.getTimestamp("end_time");
                onlineTest = new OnlineTest(title, content, startTime, endTime);
                onlineTest.setId(OT_id);
            }
            if (onlineTest != null) {
                stat = conn.prepareStatement("select count(*) as file_count from t_online_test_file where online_test_id=?");
                stat.setInt(1, OT_id);
                result = stat.executeQuery();
                if (result.next())
                    onlineTest.setFileFlag(result.getInt("file_count"));
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return onlineTest;
    }

    @Override
    public ArrayList<OnlineTest> retrieveOnlineTestList(int sec_id) {
        conn = Conndb.getConn();
        ArrayList<OnlineTest> list = null;
        try {
            stat = conn.prepareStatement("select * from t_online_test where section_id=?");
            stat.setInt(1, sec_id);
            ResultSet resultSet = stat.executeQuery();
            list = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
//                int secId = resultSet.getInt("sec_id");
                String title = resultSet.getString("title");
//                String content = resultSet.getString("content");
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp endTime = resultSet.getTimestamp("end_time");
                OnlineTest onlineTest = new OnlineTest();
                onlineTest.setId(id);
                onlineTest.setTitle(title);
                onlineTest.setStartTime(startTime);
                onlineTest.setEndTime(endTime);
                list.add(onlineTest);
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.isEmpty() ? null : list;
    }
}
