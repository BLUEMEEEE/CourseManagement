package dao;

import bean.Admin;

public interface IAdminDAO {
    public Admin verify(String account, String passwd);

}
