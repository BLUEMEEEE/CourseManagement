package service;

import bean.Admin;
import bean.Teacher;
import bean.User;
import dao.impl.AdminDAO;
import dao.impl.StudentDAO;
import dao.impl.TeacherDAO;

public class LoginService {
    private static LoginService ourInstance = new LoginService();

    public static LoginService getInstance() {
        return ourInstance;
    }

    private LoginService() {
    }

    public User specifyUser(String account, String passwd, int group) {
        User user = null;
        switch (group) {
            case 1:                                     //admin
                AdminDAO adminDAO = AdminDAO.getInstance();
                user = adminDAO.verify(account, passwd);
                break;
            case 2:                                      //teacher
                TeacherDAO teacherDAO = TeacherDAO.getInstance();
                user = teacherDAO.verify(account, passwd);
                break;
            case 3:                                      //student
                StudentDAO studentDAO = StudentDAO.getInstance();
                user = studentDAO.verify(account, passwd);
                break;
        }
        return user;
    }
}
