package dao.impl;

import bean.Resource;
import dao.IResourceDAO;
import util.Conndb;
import util.UpFile;

import java.sql.*;
import java.util.ArrayList;

public class ResourceDAO implements IResourceDAO {
    private static ResourceDAO ourInstance = new ResourceDAO();

    public static ResourceDAO getInstance() {
        return ourInstance;
    }

    private ResourceDAO() {
    }

    private Connection conn = null;
    private PreparedStatement stat = null;

    @Override
    public int createResource(Resource resource) {
        conn = Conndb.getConn();
        int resId = -1;
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("insert into t_resource values(null, ?, ?)");
            stat.setInt(1, resource.getSecId());
            stat.setString(2, resource.getType());
            aff_rows = stat.executeUpdate();
            if (aff_rows > 0) {
                stat = conn.prepareStatement("select id from t_resource where id=last_insert_id()");
                ResultSet resultSet = stat.executeQuery();
                if (resultSet.next()) {
                    resId = resultSet.getInt("id");
                }
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resId;
    }

    @Override
    public boolean deleteResource(int resId) {
        conn = Conndb.getConn();
        int aff_rows = 0;
        try {
            stat = conn.prepareStatement("delete from t_resource where id=?");
            stat.setInt(1, resId);
            aff_rows = stat.executeUpdate();
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aff_rows != 0;
    }

    @Override
    public ArrayList<Resource> retrieveResourceList(int secId, String resType) {
        conn = Conndb.getConn();
        ArrayList<Resource> resourceArrayList = null;
        try {
            stat = conn.prepareStatement("select * from t_resource res inner join t_resource_file ref on res.id=ref.resource_id " +
                    "where section_id=? and type=?");
            stat.setInt(1, secId);
            stat.setString(2, resType);
            ResultSet resultSet = stat.executeQuery();
            resourceArrayList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("res.id");
                String fileName = resultSet.getString("file_name");
                String filePath = resultSet.getString("file_path");
                float fileSize = resultSet.getFloat("file_size");
                Timestamp uploadTime = resultSet.getTimestamp("upload_time");
                UpFile upFile = new UpFile(id, fileName, fileSize, filePath, uploadTime);
                Resource resource = new Resource(id, secId, resType);
                resource.setUpFile(upFile);
                resourceArrayList.add(resource);
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resourceArrayList.isEmpty() ? null : resourceArrayList;
    }
}
