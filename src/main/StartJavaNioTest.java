package main;

import log.WFormatter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartJavaNioTest {

    private static Logger LOGGER;

    public static void main(String[] args) {
        initLogger();
        LOGGER = Logger.getLogger("wlogger");

        try {
            URL website = new URL("http://upload.wikimedia.org/wikipedia/commons/b/b7/Big_smile.png");
            LOGGER.info(String.format("Total file size:%s", getFileSize(website)));

            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("junk/smile.png");
            LOGGER.warning("Download starting...");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            LOGGER.warning("Done downloaded a file!");
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


    private static void initLogger() {
        Logger l = Logger.getLogger("wlogger");
        l.setUseParentHandlers(false);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new WFormatter());

        l.addHandler(consoleHandler);
    }
}
