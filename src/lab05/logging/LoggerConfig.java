package logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LoggerConfig {
    
    public static Logger getLogger() {
        final Logger logger = Logger.getLogger("log das explorações");
        
        String logFilePath = "logs/exploration.log";
        
        FileHandler fileHandler = null; 
        try {
            fileHandler = new FileHandler(logFilePath, true);

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
