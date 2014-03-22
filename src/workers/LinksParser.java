package workers;

import dto.DwnFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created on 2014-03-22
 * Author: Wades
 *
 * <Insert Description>
 */
public class LinksParser {

    private static final String PATH_TO_LINKS_FILE = "links.txt";
    private static Logger LOGGER;

    public LinksParser() {
        LOGGER = Logger.getLogger("wlogger");
    }

    /**
     *
     * @return ArrayList
     */
    public List<DwnFile> parse(){
        List<DwnFile> listDF = new ArrayList<>();

        String everything = "";

        try{
            BufferedReader br = new BufferedReader(new FileReader(PATH_TO_LINKS_FILE));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();

        } catch (Exception e){
            e.printStackTrace();
        }

        String[] eachLine = everything.split("\\r\\n");
        for (String line : eachLine){
            String[] pipSepataion = line.split("\\|");
            try {
                listDF.add(new DwnFile(pipSepataion[1].trim(), pipSepataion[3].trim(), pipSepataion[2].trim()));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info(String.format("Parsed %s lines from the file.", listDF.size()));
        return listDF;
    }
}
