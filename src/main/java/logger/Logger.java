package logger;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author:
 * Description:
 */
public class Logger {


    private static File logFile;
    private static BufferedWriter bw;

    public static void setPath(String path) throws FileNotFoundException {
        logFile = new File(path+"log.txt");
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile)));
    }


    public static void logInformation(String log,Object type)
    {
        String out = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))+" [Information] "+type.toString()+": "+log;
        try {
           bw.write(out);
           bw.newLine();
           bw.flush();
        } catch (IOException e) {

        }
    }

    public static void logError(String log,Object type)
    {
        String out= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))+" [Error] "+type.toString()+": "+log;
        try {
            bw.write(out);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {

        }
    }



}