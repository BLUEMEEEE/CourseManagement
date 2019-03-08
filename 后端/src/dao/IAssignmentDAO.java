/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

import bean.Assignment;
import bean.AssignmentSubmission;

/**
 *
 * @author BLUEMEEE
 */

public interface IAssignmentDAO{
    public int createAssignment(Assignment assignment);
    public boolean deleteAssignment(int assign_id);
    public boolean updateAssignment(Assignment assignment);
    public Assignment retrieveAssignment(int assign_id);
    public ArrayList<Assignment> retrieveAssignmentList(int sec_id);
}


