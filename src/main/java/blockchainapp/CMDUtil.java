package blockchainapp;

import java.io.IOException;

/**
 * Created by Patrick Windegger on 05.03.2018.
 */
public class CMDUtil {
    private Runtime runtime;
    private String gethDirectory;
    private String dataDirectory;

    public CMDUtil(String gethDirectoy, String dataDirectory) {
        this.gethDirectory = gethDirectoy;
        this.dataDirectory = dataDirectory;
        runtime = Runtime.getRuntime();
    }

    public void startGethNetwork() throws IOException {
        runtime.exec("cmd /c start " + gethDirectory + "geth --dev --datadir " + dataDirectory + " --rpc --gpopercentile 0 --fast");
    }

    public void startJSConsole() throws IOException {
        runtime.exec("cmd /c start " + gethDirectory + "geth --dev attach");
    }
}
