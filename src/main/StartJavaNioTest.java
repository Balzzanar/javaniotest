package main;

import dto.DwnFile;
import log.WFormatter;
import singelton.FileQueueSingelton;
import workers.Downloader;
import workers.LinksParser;

import java.io.File;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartJavaNioTest {

    private static Logger LOGGER;

    public static void main(String[] args) {
        initLogger();
        LOGGER = Logger.getLogger("wlogger");

        List<DwnFile> listDF = new LinksParser().parse();
        for (DwnFile df : listDF){
            try {
                FileQueueSingelton.getClientQueue().put(df);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info(String.format("Queue at %s Files", FileQueueSingelton.getClientQueue().size()));

        new Downloader().start();
        new Downloader().start();
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
