package dao;

import bean.Section;

import java.util.ArrayList;

public interface ISectionDAO {
    public boolean createSection(Section section);
    public boolean deleteSection(int secId);
    public boolean updateSection(Section section);
    public ArrayList<Section> retrieveSectionList(int id, String group);
    public Section retrieveSection(int secId);
}
