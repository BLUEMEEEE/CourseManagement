package dao;

import bean.Student;

public interface IStudentDAO {
    public Student verify(String account, String passwd);
    public boolean createStudent(Student student);
    public boolean deleteStudent(int studentId);
}
