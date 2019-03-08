package dao.impl;

import bean.Teacher;
import dao.ITeacherDAO;
import util.Conndb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDAO implements ITeacherDAO {
    private static TeacherDAO instance = new TeacherDAO();
    private TeacherDAO() {
    }
    public static TeacherDAO getInstance() {
        return instance;
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    @Override
    public Teacher verify(String account, String passwd) {
        conn = Conndb.getConn();
        Teacher teacher = null;
        try {
            stat = conn.prepareStatement("select * from t_teacher where account=?");
            stat.setString(1, account);
            ResultSet resultSet = stat.executeQuery();
            if (resultSet.next()) {
                String sqlPasswd = resultSet.getString("passwd");
                if (sqlPasswd.equals(passwd)) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    teacher = new Teacher(id, account, passwd, name);
                }
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    @Override
    public boolean createTeacher(Teacher teacher) {
        return false;
    }

    @Override
    public boolean deleteTeacher(int teacherId) {
        return false;
    }
}
