package dto;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created on 2014-03-22
 * Author: Wades
 *
 * <Insert Description>
 */
public class DwnFile {

    private String name;
    private URL url;
    private String category;
    private int filesize;
    private String id;
    private String filename;

    public DwnFile(String name, String url, String category, String id) throws MalformedURLException {
        this.name = name;
        this.url = new URL(url);
        this.category = category;
        String[] temp = url.split("\\.");
        this.filename = String.format("%s__%s.%s", this.name, id, temp[temp.length -1]);
    }

    /* Getters */
    public String getName() { return name; }
    public URL getUrl() { return url; }
    public String getCategory() { return category; }
    public int getFilesize() { return filesize; }
    public String getFilename() { return filename; }

    /* Setters */
    public void setFilesize(int filesize) { this.filesize = filesize; }
}
