package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * Created by Ewald on 08.03.2018.
 */
public class Logger {


    private static final File logFile = new File(System.getProperty("user.dir") + File.separator + "out" + File.separator + "artifacts"
            + File.separator + "test_webapp2_war_exploded" + File.separator + "res"
            + File.separator + "log" + File.separator + "weblog.log");


    public static void logInformation(String log,Object o)
    {
        String out= LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MMM.yyyy hh:mm:ss"))+" [Information] "+o.getClass().toString()+"log";
        BufferedWriter bw=null;
        try {
           bw = new BufferedWriter(new FileWriter(logFile));
           bw.append(out);
        } catch (IOException e) {

        }
    }

    public static void logError(String log,Object o)
    {
        String out= LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MMM.yyyy hh:mm:ss"))+" [Error] "+o.getClass().toString()+"log";
        BufferedWriter bw=null;
        try {
            bw = new BufferedWriter(new FileWriter(logFile));
            bw.append(out);
        } catch (IOException e) {

        }
    }



}