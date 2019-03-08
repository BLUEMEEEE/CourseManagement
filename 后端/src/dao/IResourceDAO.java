package dao;

import bean.Resource;

import java.util.ArrayList;

public interface IResourceDAO {
    public int createResource(Resource resource);
    public boolean deleteResource(int resId);
    public ArrayList<Resource> retrieveResourceList(int secId, String resType);
}
