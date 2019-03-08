package dao.impl;

import bean.Student;
import dao.IStudentDAO;
import util.Conndb;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO implements IStudentDAO {
    private static StudentDAO instance = new StudentDAO();
    private StudentDAO() {}
    public static StudentDAO getInstance() {
        return instance;
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    @Override
    public Student verify(String account, String passwd) {
        conn = Conndb.getConn();
        Student student = null;
        try {
            stat = conn.prepareStatement("select * from t_student where account=?");
            stat.setString(1, account);
            ResultSet resultSet = stat.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String sqlPasswd = resultSet.getString("passwd");
                if (sqlPasswd.equals(passwd)) {
                    String name = resultSet.getString("name");
                    int schId = resultSet.getInt("school_id");
                    String major = resultSet.getString("major");
                    String grade = resultSet.getString("grade");
                    String sqlClass = resultSet.getString("class");
                    student = new Student(id, account, passwd, name, schId, major, grade, sqlClass);
                }
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public boolean createStudent(Student student) {
        return false;
    }

    @Override
    public boolean deleteStudent(int studentId) {
        return false;
    }
}
