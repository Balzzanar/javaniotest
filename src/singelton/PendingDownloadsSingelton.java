package singelton;

import dto.DwnFile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * PendingDownloadsSingelton
 * Author: Wades
 *
 * Keeps track of the downloads that area active.
 */
public class PendingDownloadsSingelton {

    private static PendingDownloadsSingelton instance = null;
    private static Logger LOGGER;

    /* The big file queue that keeps all the files in line! */
    private static List<DwnFile> pendingDownloads = null;


    protected PendingDownloadsSingelton() {
        // Exists only to defeat instantiation.
    }


    public static List<DwnFile> getPendingDownloads(){
        if(pendingDownloads == null) {
            LOGGER = Logger.getLogger("wlogger");
            LOGGER.info(String.format("Started a new pending list"));
            pendingDownloads = new ArrayList<>();
        }
        return pendingDownloads;
    }

    public static void addDownload(DwnFile df){
        synchronized (pendingDownloads){
            getPendingDownloads().add(df);
            LOGGER.info(String.format("Added a new file to pending list."));
        }
    }

    public static void removeDownload(DwnFile df){
        synchronized (pendingDownloads){
            getPendingDownloads().remove(df);
            LOGGER.info(String.format("Removed a file form pending list."));
        }
    }

}
