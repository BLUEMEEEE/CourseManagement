package dao;

import bean.Teacher;

public interface ITeacherDAO {
    public Teacher verify(String account, String passwd);
    public boolean createTeacher(Teacher teacher);
    public boolean deleteTeacher(int teacherId);
}
