package blockchainapp;

import java.io.IOException;

/**
 * Author:          Patrick Windegger
 * Created on:
 * Description:     Class responsible for opening command prompts with certain commands/parameters to start
 *                  the private Blockchain environment.
 */
public class CMDUtil {

    private Runtime runtime;
    private String gethDirectory;
    private String dataDirectory;

    /**
     * Constructor to initialize the Runtime
     *
     * @param gethDirectoy:  path to 'geth.exe'
     * @param dataDirectory: path to the data directory, where the wallet files are stored.
     */
    public CMDUtil(String gethDirectoy, String dataDirectory) {
        this.gethDirectory = gethDirectoy;
        this.dataDirectory = dataDirectory;
        runtime = Runtime.getRuntime();
    }

    /**
     * Method responsible for starting 'geth.exe'
     * This method opens a command line prompt and starts the Blockchain environment automatically with the right parameters.
     *
     * @throws IOException if an I/O error occurs
     */
    public void startGethNetwork() throws IOException {
        runtime.exec("cmd /c start " + gethDirectory + "geth --dev --datadir " + dataDirectory + " --rpc --gpopercentile 0 --gasprice 0 --fast");
    }

    /**
     * Method responsible for starting the java script console
     * This method opens a command line prompt and starts the java script console of the Blockchain environment automatically.
     *
     * @throws IOException if an I/O error occurs
     */
    public void startJSConsole() throws IOException {
        runtime.exec("cmd /c start " + gethDirectory + "geth --dev attach");
    }
}
