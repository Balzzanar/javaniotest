package workers;

import dto.DwnFile;
import singelton.PendingDownloadsSingelton;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created on 2014-03-22
 * Author: Wades
 *
 * <Insert Description>
 */
public class FileChecker extends Thread {

    private static Logger LOGGER;
    private static final String PATH_TO_JUNK_FOLDER = "junk/";

    public FileChecker() {
        super();
        LOGGER = Logger.getLogger("wlogger");
    }

    @Override
    public void run() {
        super.run();


        while (true){
            try{
                Thread.sleep(10000);

                LOGGER.info(String.format("--------------------PROGRESS REPORT-------------------------"));
                for (DwnFile df : PendingDownloadsSingelton.getPendingDownloads()){
                    Long size = getFileSizeInPath(df.getFilename());
                    float p = ((float)(size) / (float) df.getFilesize()) * 100;
                    LOGGER.info(String.format("File: %s, is at: %s percent (%s)", df.getName(), p, df.getFilesize() - size));
                }
                LOGGER.info(String.format("-------------------END PROGRESS REPORT-----------------------"));

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private long getFileSizeInPath(String path) {
        File f = new File(PATH_TO_JUNK_FOLDER + path);
        if(f.exists()){
            return f.length();
        }
        return -1;
    }


}
