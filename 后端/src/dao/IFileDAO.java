/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import util.FileType;
import util.UpFile;

/**
 *
 * @author BLUEMEEE
 */
public interface IFileDAO {
    public boolean createFile(UpFile upFile);
    public boolean deleteFile(int id, FileType fileType);
    public String getFilePath(int id, FileType fileType);
    public boolean updateFile(UpFile upFile);
}
