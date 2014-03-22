package singelton;

import dto.DwnFile;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

/**
 * FileQueueSingelton
 * Author: Wades
 *
 * Keeps track of the files that shall be downloaded.
 */
public class FileQueueSingelton {

    private static FileQueueSingelton instance = null;
    private static Logger LOGGER;

    /* The big file queue that keeps all the files in line! */
    private static ArrayBlockingQueue<DwnFile> fileQueue = null;


    protected FileQueueSingelton() {
        // Exists only to defeat instantiation.
    }


    public static ArrayBlockingQueue<DwnFile> getClientQueue(){
        if(fileQueue == null) {
            LOGGER = Logger.getLogger("wlogger");
            initQueue();
        }
        return fileQueue;
    }

    /**
     * Initialise the FileQueue.
     */
    private static void initQueue(){
        fileQueue = new ArrayBlockingQueue<DwnFile>(Integer.MAX_VALUE/100000);
        LOGGER.info(String.format("FileQueue started with length of %s", Integer.MAX_VALUE/100000));
    }
}
