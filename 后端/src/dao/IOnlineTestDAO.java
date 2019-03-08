/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

import bean.OnlineTest;
/**
 *
 * @author BLUEMEEE
 */
public interface IOnlineTestDAO {
    public int createOnlineTest(OnlineTest onlineTest);
    public boolean deleteOnlineTest(int OT_id);
    public boolean updateOnlineTest(OnlineTest onlineTest);
    public OnlineTest retrieveOnlineTest(int OT_id);
    public ArrayList<OnlineTest> retrieveOnlineTestList(int sec_id);
}
