package util;

import java.io.InputStream;
import java.util.HashMap;
import org.apache.commons.fileupload.FileItem;


public class MultiForm {
    private boolean hasfile = false;
    private UpFile upFile = null;
    private HashMap<String, String> form = new HashMap<>();
    private FileItem fileItem;

    public boolean hasFile() {
        return this.hasfile;
    }

    public String getParameter(String key) {
        return this.form.get(key);
    }

    public UpFile getUploadFile() {
        return this.upFile;
    }

    public void pushPair(String key, String value) {
        form.put(key, value);
    }

    public void setUpFile(UpFile upfile) {
        this.upFile = upfile;
        this.hasfile = true;
    }

    public void setFileItem(FileItem fileItem) {
        this.fileItem = fileItem;
    }

    public void deleteTempFile() {
        fileItem.delete();
    }
}
