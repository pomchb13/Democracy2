package logger;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: Christoph Pommer
 * Description: This class logs all errors and information's to a external file.
 */
public class Logger {


    private static File logFile;
    private static BufferedWriter bw;

    /***
     *
     * @param path the path where the file will be saved
     * @throws FileNotFoundException
     */
    public static void setPath(String path) throws FileNotFoundException {
        logFile = new File(path+"log.txt");
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile)));
    }

    /***
     *
     * @param log the massage which will be logged
     * @param type The class where the information comes from
     */
    public static void logInformation(String log,Object type)
    {
        String out = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))+" [Information] "+type.toString()+": "+log;
        try {
           bw.write(out);
           bw.newLine();
           bw.flush();
        } catch (IOException e) {
            e.printStackTrace(); // the logger will hopefully never get an error
        }
    }

    /***
     *
     * @param log the massage which will be logged
     * @param type The class where the error comes from
     */
    public static void logError(String log,Object type)
    {
        String out= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))+" [Error] "+type.toString()+": "+log;
        try {
            bw.write(out);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace(); // the logger will hopefully never get an error
        }
    }



}