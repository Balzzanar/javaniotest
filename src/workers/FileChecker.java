package workers;

import java.util.logging.Logger;

/**
 * Created on 2014-03-22
 * Author: Wades
 *
 * <Insert Description>
 */
public class FileChecker extends Thread {

    private static Logger LOGGER;

    public FileChecker() {
        super();
        LOGGER = Logger.getLogger("wlogger");
    }

    @Override
    public void run() {
        super.run();


        while (true){
            try{
            LOGGER.info(String.format("Will sleep for 5s"));
            Thread.sleep(5000);


            }catch (Exception e){

            }

        }
    }
}
