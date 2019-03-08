package dao.impl;

import bean.Admin;
import dao.IAdminDAO;
import util.Conndb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO implements IAdminDAO {
    private static AdminDAO instance = new AdminDAO();

    public static AdminDAO getInstance() {
        return instance;
    }

    private AdminDAO() {
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    @Override
    public Admin verify(String account, String passwd) {
        conn = Conndb.getConn();
        Admin admin = null;
        try {
            stat = conn.prepareStatement("select * from t_admin where account=?");
            stat.setString(1, account);
            ResultSet resultSet = stat.executeQuery();
            if (resultSet.next()) {
                String sqlPasswd = resultSet.getString("passwd");
                if (sqlPasswd.equals(passwd)) {
                    int id = resultSet.getInt("id");
                    admin = new Admin(id, account, passwd);
                }
                stat.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
}
