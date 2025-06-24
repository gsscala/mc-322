package logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LoggerConfig {
    
    public static Logger getLogger(String pathName) {
        final Logger logger = Logger.getLogger("missoes");
                
        FileHandler fileHandler = null; 
        try {
            fileHandler = new FileHandler(pathName, true);

            fileHandler.setFormatter(new SimpleFormatter());

            fileHandler.setLevel((Level.ALL));

            logger.addHandler(fileHandler);

            // logger.setUseParentHandlers(false);

        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return logger;
    }
}
