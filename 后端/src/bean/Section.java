package bean;

import bean.TimeBlock;

//import java.lang.reflect.Array;
import java.util.ArrayList;

public class Section {
    private int id;
    private String title;
    private String introduction;
    private int credit;
    private String year;
    private  String semester;
    private ArrayList<TimeBlock> timeBlocks;
    private ArrayList<Integer> week;
    private String building;
    private String roomNumber;
    private String teacherName;


    public Section() {}

    public Section(int id, String title, String introduction, int credit, String year, String semester, ArrayList<TimeBlock> timeBlocks, ArrayList<Integer> week, String building, String roomNumber) {
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.credit = credit;
        this.year = year;
        this.semester = semester;
        this.timeBlocks = timeBlocks;
        this.week = week;
        this.building = building;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getCredit() {
        return credit;
    }

    public String getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    public ArrayList<TimeBlock> getTimeBlocks() {
        return timeBlocks;
    }

    public ArrayList<Integer> getWeek() {
        return week;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setTimeBlocks(ArrayList<TimeBlock> timeBlocks) {
        this.timeBlocks = timeBlocks;
    }

    public void setWeek(ArrayList<Integer> week) {
        this.week = week;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
