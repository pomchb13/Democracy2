package blockchainapp;

import handler.AdminHandler;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import user.UserCreator;
import util.BlockchainUtil;
import util.PasswordGenerator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

/**
 * Author:          Patrick Windegger
 * Description:     GUI for the Blockchain app for admins, to start the private Blockchain environment,
 *                  deploy the admin-contract and create new admins.
 */
public class BlockchainAppGUI extends JFrame {

    private CMDUtil cmdUtil;
    private String gethDirectory;
    private String dataDirectory;
    private String res;

    private JList liAdmins;
    private DefaultListModel<Address> dtm;
    private boolean contractExists = false;

    private Credentials adminCredentials;

    private AdminHandler adminHandler;
    private String path = System.getProperty("user.dir") + File.separator + "out" + File.separator + "artifacts"
            + File.separator + "test_webapp2_war_exploded" + File.separator + "res"
            + File.separator;

    /**
     * Constructor for initializing the GUI,
     * initializing CMDUtil class and creating the Default List Model for the JList
     *
     * @param title: title of the GUI
     */
    public BlockchainAppGUI(String title) {
        super(title);
        initComponents();
        this.setSize(450, 500);
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 1.5f), Toolkit.getDefaultToolkit().getScreenSize().height / 9);
        try {
            initGeth();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(this, "an error occured!");
        }
        cmdUtil = new CMDUtil(gethDirectory, dataDirectory);
        dtm = new DefaultListModel<>();
        liAdmins.setModel(dtm);

    }

    /**
     * Method responsible for insitializing all components of the window
     * and adding ActionListeners on the different buttons.
     */
    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton btStart = new JButton("Start Blockchain environement");
        btStart.addActionListener((e) -> {
            try {
                onStartGeth(e);
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (CipherException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "Login terminated!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });

        JPanel paNorth = new JPanel();
        paNorth.setLayout(new BorderLayout());
        paNorth.add(btStart, BorderLayout.SOUTH);

        JEditorPane info = new JEditorPane();
        info.setContentType("text/html");
        info.setEditable(false);
        String htmlString = "<h1>Important information:</h1>" +
                "<p>Please start the miner in the java script console with the 'miner.start()' command " +
                "before creating a new admin! " +
                "Otherwise the application will not run properly!<br>" +
                "If you start this application for the first time, you need to start the miner later. " +
                "This will be explained to you during the initialization process.</p>";
        info.setText(htmlString);
        paNorth.add(info, BorderLayout.CENTER);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(paNorth, BorderLayout.NORTH);

        liAdmins = new JList();
        JScrollPane scPane = new JScrollPane(liAdmins);
        scPane.setBorder(new TitledBorder("admins"));

        this.getContentPane().add(scPane, BorderLayout.CENTER);

        JButton btNewAdmin = new JButton("create new admin");
        btNewAdmin.addActionListener((e) -> {
            try {
                onCreateNewAdmin(e);
            } catch (NoSuchAlgorithmException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (NoSuchProviderException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (InvalidAlgorithmParameterException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (CipherException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        this.getContentPane().add(btNewAdmin, BorderLayout.SOUTH);
    }


    /**
     * Method responsible for starting the Blockchain
     * If the AdminContract contract exists, the admin has to log in to the Blockchain using his login data. All available
     * admins of the contract get loaded into a JList.
     * If the AdminContract contract does not exists, a new admin gets created automatically.
     *
     * @throws Exception if there are errors during the input.
     */
    private void onStartGeth(ActionEvent e) throws Exception {
        File file = new File(path + "admin" + File.separator + "contract.txt");
        if (file.exists()) {
            // Read the contract address of the text file if it exists
            BufferedReader br = new BufferedReader(new FileReader(file));
            String contractAddress = br.readLine();
            // Read username from JOptionPane
            String adminAddress = JOptionPane.showInputDialog(this, "Please enter your admin username:").trim();
            if (adminAddress.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username may not be empty!");
                return;
            }
            // Read password from JOptionPane
            JPasswordField passwordField = new JPasswordField();
            if (JOptionPane.showConfirmDialog(this, passwordField, "Please enter your password!",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.OK_OPTION) {
                String password = new String(passwordField.getPassword());
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Password may not be empty!");
                    return;
                }
                // Load the contract
                adminCredentials = BlockchainUtil.loginToBlockhain(adminAddress, password, path + "geth_data"
                        + File.separator + "keystore" + File.separator);
                adminHandler = new AdminHandler(adminCredentials);
                adminHandler.loadSmartContract(new Address(contractAddress));
                contractExists = true;
                startGeth();
                try {
                    // Get all admins from the contract
                    List<Address> admins = adminHandler.getAllAdmins(new Address(adminCredentials.getAddress()));
                    dtm.removeAllElements();
                    for (Address a : admins) {
                        dtm.addElement(a);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "User is not admin!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        } else {
            // Start the Blockchain and create the first admin user if the contract does not exist
            contractExists = false;
            startGeth();
            JOptionPane.showMessageDialog(this, "The first admin will be created automatically!\n Press OK to continue!",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            onCreateNewAdmin(null);
        }
    }


    /**
     * Method responsible for creating a new admin
     * If the AdminContract contract exists and the contract is loaded, a new user address and password gets created,
     * the admin gets stored in the contract and is added to the JList.
     * If the admin contract does not exist and the contract is not loaded yet, a new user address and password gets created,
     * a new AdminContract contract gets created and the new admin gets added to the JList.
     *
     * @throws NoSuchAlgorithmException           if an error occurs
     * @throws NoSuchProviderException            if an error occurs
     * @throws InvalidAlgorithmParameterException if an error occurs
     * @throws CipherException                    if an error occurs
     * @throws IOException                        if an error occurs
     */
    private void onCreateNewAdmin(ActionEvent e) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        UserCreator creator = new UserCreator();
        if (contractExists) {
            if (adminHandler != null) {
                // Generate new wallet file with random password
                String password = PasswordGenerator.createPassword(20);
                String newAddress = creator.createNewUserAddress(password, path + "geth_data"
                        + File.separator + "keystore" + File.separator);

                // Show login data
                JTextArea optionPaneArea = new JTextArea("Please store the login data at a save place!\n"
                        + "Username: " + newAddress + "\nPassword: " + password
                        + "\nPress OK and start the miner in the java script console with the 'miner.start()' command, if it is not already started!");
                optionPaneArea.setEditable(false);
                JOptionPane.showMessageDialog(this, optionPaneArea, "Information", JOptionPane.INFORMATION_MESSAGE);
                try {
                    Credentials cr = BlockchainUtil.loginToBlockhain(newAddress, password, path
                            + "geth_data" + File.separator + "keystore" + File.separator);
                    // Add the new admin to the list and contract
                    dtm.addElement(new Address(newAddress));
                    adminHandler.addAdminAddress(new Address(cr.getAddress()), new Address(adminCredentials.getAddress()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } else {
            // Generate new wallet file with random password for the first admin
            String password = PasswordGenerator.createPassword(20);
            String newAddress = creator.createNewUserAddress(password, path + "geth_data"
                    + File.separator + "keystore" + File.separator);

            // Show login data
            JTextArea optionPaneArea = new JTextArea("Please store the login data at a save place!\n"
                    + "Username: " + newAddress + "\nPassword: " + password
                    + "\nPress OK and start the miner in the java script console with the 'miner.start()' command, if it is not already started!");
            optionPaneArea.setEditable(false);
            JOptionPane.showMessageDialog(this, optionPaneArea, "Information", JOptionPane.INFORMATION_MESSAGE);
            try {
                adminCredentials = BlockchainUtil.loginToBlockhain(newAddress, password, path
                        + "geth_data" + File.separator + "keystore" + File.separator);

                if (adminHandler == null) {
                    // Create contract and save the address to the text file
                    adminHandler = new AdminHandler(adminCredentials);
                    String contractAddress = adminHandler.createSmartContract();
                    BufferedWriter bw = new BufferedWriter(new FileWriter(path + "admin"
                            + File.separator + "contract.txt"));
                    bw.write(contractAddress);
                    bw.close();
                    // Add admin to the list
                    dtm.addElement(new Address(newAddress));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    /**
     * Method responsible for initializing components of the geth-network
     * If the textfile with the path to 'geth.exe' and the data directory exists, it gets read and the Blockchain
     * is ready to get started.
     * If the textfile does not exist, 2 file chooser are opened and the user has to navigate to the path
     * where 'geth.exe' and the data directory is stored. The Blockchain is ready to get started if this process has been finished
     *
     * @throws IOException if an I/O error occurs
     */
    public void initGeth() throws IOException {
        res = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + File.separator + "gethpath.txt";
        File f = new File(res);
        if (f.exists()) {
            // Read the paths if the file exists
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
            // Create file choosers
            JFileChooser jfc1 = new JFileChooser("user.home");
            jfc1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc1.setDialogTitle("Path to geth.exe");
            JFileChooser jfc2 = new JFileChooser("user.home");
            jfc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc2.setDialogTitle("Path to the data directory");

            // Read the paths from the file choosers and write them into the text file
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

    /**
     * Method responsible for starting the geth-network and starting the java script console
     */
    private void startGeth() {
        try {
            cmdUtil.startGethNetwork();
            cmdUtil.startJSConsole();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage());
        }
    }


    /**
     * Main method responsible for creating the GUI and making the window visible
     */
    public static void main(String[] args) {
        new BlockchainAppGUI("Blockchain app for admins").setVisible(true);
    }
}
