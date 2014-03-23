package workers;

import dto.DwnFile;
import singelton.FileQueueSingelton;
import singelton.PendingDownloadsSingelton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Logger;

public class Downloader extends Thread{

    private static Logger LOGGER;

    public Downloader() {
        super();
        LOGGER = Logger.getLogger("wlogger");
    }

    @Override
    public void run() {
        super.run();
        LOGGER = Logger.getLogger("wlogger");

        while(! FileQueueSingelton.getClientQueue().isEmpty()){
            try{
                DwnFile df = FileQueueSingelton.getClientQueue().take();
                LOGGER.info(String.format("Current queue size: %s", FileQueueSingelton.getClientQueue().size()));
                downloadFile(df);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     *
     * @param df, DwnFile
     */
    private void downloadFile(DwnFile df) {
        LOGGER = Logger.getLogger("wlogger");

        try {
            int fs = getFileSize(df.getUrl());
            LOGGER.info(String.format("Total file size:%s", fs));
            df.setFilesize(fs);

            PendingDownloadsSingelton.addDownload(df);

            ReadableByteChannel rbc = Channels.newChannel(df.getUrl().openStream());
            FileOutputStream fos = new FileOutputStream("junk/" + df.getFilename());
            LOGGER.info(String.format("Started download of: %s", df.getName()));
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            LOGGER.info(String.format("Done downloading: %s", df.getName()));
        } catch(Exception e) {
            e.printStackTrace();
            LOGGER.warning("Fail!");
        }
    }

    /**
     * Returns the file size for a file given its url.
     *
     * @param url, url to file
     * @return file size
     */
    private static int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return -1;
        } finally {
            assert conn != null;
            conn.disconnect();
        }
    }


}
