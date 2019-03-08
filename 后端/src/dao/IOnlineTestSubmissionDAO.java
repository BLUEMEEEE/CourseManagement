/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import bean.OnlineTestSubmission;
/**
 *
 * @author BLUEMEEE
 */
public interface IOnlineTestSubmissionDAO {
    public int createOnlineTestSubmission(OnlineTestSubmission OT_submission);
    public boolean deleteOnlineTestSubmission(int sub_id);
    public boolean updateMark(int sub_id,float mark);
    public boolean updateContent(OnlineTestSubmission onlineTestSubmission);
    public ArrayList<OnlineTestSubmission> retrieveOnlineTestSubmissionList(int OT_id);
    public OnlineTestSubmission retrieveOnlineTestSubmission(int sub_id);
}
