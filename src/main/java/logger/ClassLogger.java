package logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ewald on 08.03.2018.
 */
public class ClassLogger {

    private static Logger logger;
    private static final String PATH = System.getProperty("user.dir") + File.separator + "out" + File.separator + "artifacts"
            + File.separator + "test_webapp2_war_exploded" + File.separator + "res"
            + File.separator + "log" + File.separator + "weblog.log";

    private static ClassLogger ourInstance = new ClassLogger();

    public static ClassLogger getInstance(String className) throws IOException {
        logger = Logger.getLogger(className);
        FileHandler fileHandler = new FileHandler(PATH, true);
        logger.addHandler(fileHandler);
        return ourInstance;
    }

    private ClassLogger() {
    }

    public void logInfo(String logMassage) {
        logger.log(Level.INFO, logMassage);
    }

    public void logError(String logMassage) {
        logger.log(Level.SEVERE, logMassage);
    }
}