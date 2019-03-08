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
public interface IAssignmentSubmissionDAO {
    public int createAssignmentSubmission(AssignmentSubmission assignmentSubmission);
    public boolean deleteAssignmentSubmission(int subId);
    public boolean updateContent(AssignmentSubmission assignmentSubmission);
    public boolean updateMark(int subId, float mark);
    public ArrayList<AssignmentSubmission> retrieveAssignmentSubmissionList(int assId);
    public AssignmentSubmission retrieveAssignmentSubmission(int subId);
}
