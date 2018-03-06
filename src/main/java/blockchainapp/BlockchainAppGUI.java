package blockchainapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * Created by Patrick Windegger on 05.03.2018.
 */
public class BlockchainAppGUI extends JFrame {

    private CMDUtil cmdUtil;
    private String gethDirectory;
    private String dataDirectory;
    private String res;


    public BlockchainAppGUI(String title) {
        super(title);

        initComponents();
        this.setSize(450, 100);
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 1.5f), Toolkit.getDefaultToolkit().getScreenSize().height / 9);
        try {
            initGeth();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(this, "an error occured!");
        }
        cmdUtil = new CMDUtil(gethDirectory, dataDirectory);
    }

    private void initComponents()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton btGeth = new JButton("Start Blockchain environement");
        btGeth.addActionListener((e) -> onStartGeth(e));

        JButton btJS = new JButton("Start JS console");
        btJS.addActionListener((e) -> onStartJSConsole(e));

        this.getContentPane().setLayout(new GridLayout(1, 2, 5, 5));
        this.getContentPane().add(btGeth);
        this.getContentPane().add(btJS);
    }

    private void onStartGeth(ActionEvent e)
    {
        try {
            cmdUtil.startGethNetwork();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage());
        }
    }

    private void onStartJSConsole(ActionEvent e)
    {
        try {
            cmdUtil.startJSConsole();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage());
        }
    }

    public void initGeth() throws IOException {
        res = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + File.separator + "gethpath.txt";
        File f = new File(res);
        if (f.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                gethDirectory = br.readLine();
                dataDirectory = br.readLine();
                br.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "File not found!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "IOException");
            }
        } else {
            JFileChooser jfc1 = new JFileChooser("user.home");
            jfc1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc1.setDialogTitle("Path to geth.exe");
            JFileChooser jfc2 = new JFileChooser("user.home");
            jfc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc2.setDialogTitle("Path to the data directory");

            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            if (jfc1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                gethDirectory = jfc1.getSelectedFile().getAbsolutePath() + File.separator;
                bw.write(gethDirectory);
                bw.newLine();
            } else {
                JOptionPane.showMessageDialog(this, "Path needed for proper functionality of this application!");
                System.exit(0);
            }

            if (jfc2.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                dataDirectory = jfc2.getSelectedFile().getAbsolutePath() + File.separator;
                bw.write(dataDirectory);
                bw.newLine();
            } else {
                JOptionPane.showMessageDialog(this, "Path needed for proper functionality of this application!");
                System.exit(0);
            }
            bw.close();

        }

    }


    public static void main(String[] args) {
        new BlockchainAppGUI("Blockchain App for admins").setVisible(true);
    }
}
