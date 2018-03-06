package blockchainapp;

import handler.AdminHandler;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import user.UserCreator;
import util.BlockchainUtil;
import util.PasswordGenerator;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

/**
 * Created by Patrick Windegger on 05.03.2018.
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

    public BlockchainAppGUI(String title) {
        super(title);

        initComponents();
        this.setSize(450, 250);
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

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton btGeth = new JButton("Start Blockchain environement");
        btGeth.addActionListener((e) -> {
            try {
                onStartGeth(e);
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString());
                return;
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString());
                return;
            } catch (CipherException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString());
                return;
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "Login terminated!");
                return;
            }
        });

        JButton btJS = new JButton("Start JS console");
        btJS.addActionListener((e) -> onStartJSConsole(e));

        this.getContentPane().setLayout(new BorderLayout());

        JPanel paNorth = new JPanel(new GridLayout(1, 2, 5, 5));
        paNorth.add(btGeth);
        paNorth.add(btJS);
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
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString());
                return;
            } catch (NoSuchProviderException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString());
                return;
            } catch (InvalidAlgorithmParameterException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString());
                return;
            } catch (CipherException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString());
                return;
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString());
                return;
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "An error occured!\n" + e1.toString());
                return;
            }
        });


        this.getContentPane().add(btNewAdmin, BorderLayout.SOUTH);
    }

    private void onStartGeth(ActionEvent e) throws Exception {
        File file = new File(path + "admin" + File.separator + "contract.txt");
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String contractAddress = br.readLine();
            String adminAddress = JOptionPane.showInputDialog(this, "Please enter your admin username:").trim();
            if (adminAddress.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username may not be empty!");
                return;
            }
            JPasswordField passwordField = new JPasswordField();
            if (JOptionPane.showConfirmDialog(this, passwordField, "Please enter your password!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
                String password = new String(passwordField.getPassword());
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Password may not be empty!");
                    return;
                }
                adminCredentials = BlockchainUtil.loginToBlockhain(adminAddress, password, path + "geth_data"
                        + File.separator + "keystore" + File.separator);
                adminHandler = new AdminHandler(adminCredentials);
                adminHandler.loadSmartContract(new Address(contractAddress));
                contractExists = true;
                startGeth();
                try {
                    List<Address> admins = adminHandler.getAllAdmins(new Address(adminCredentials.getAddress()));
                    dtm.removeAllElements();
                    for (Address a : admins) {
                        dtm.addElement(a);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "User is not admin!");
                }

            }
        } else {
            contractExists = false;
            startGeth();
        }
    }

    private void onStartJSConsole(ActionEvent e) {
        try {
            cmdUtil.startJSConsole();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage());
        }
    }

    private void onCreateNewAdmin(ActionEvent e) throws Exception {
        UserCreator creator = new UserCreator();

        if (contractExists) {
            if (adminHandler != null) {
                String password = PasswordGenerator.createPassword(20);
                String newAddress = creator.createNewUserAddress(password, path + "geth_data"
                        + File.separator + "keystore" + File.separator);

                JTextArea optionPaneArea = new JTextArea("Please store the login data on a save place!\n"
                        + "Username: " + newAddress + "\nPassword: " + password);
                optionPaneArea.setEditable(false);
                JOptionPane.showMessageDialog(this, optionPaneArea);
                Credentials cr = BlockchainUtil.loginToBlockhain(newAddress, password, path
                        + "geth_data" + File.separator + "keystore" + File.separator);
                dtm.addElement(new Address(newAddress));
                adminHandler.addAdminAddress(new Address(cr.getAddress()), new Address(adminCredentials.getAddress()));
            }
        } else {
            String password = PasswordGenerator.createPassword(20);
            String newAddress = creator.createNewUserAddress(password, path + "geth_data"
                    + File.separator + "keystore" + File.separator);
            JTextArea optionPaneArea = new JTextArea("Please store the login data on a save place!\n"
                    + "Username: " + newAddress + "\nPassword: " + password);
            optionPaneArea.setEditable(false);
            JOptionPane.showMessageDialog(this, optionPaneArea);
            adminCredentials = BlockchainUtil.loginToBlockhain(newAddress, password, path
                    + "geth_data" + File.separator + "keystore" + File.separator);
            dtm.addElement(new Address(newAddress));

            if (adminHandler == null) {
                adminHandler = new AdminHandler(adminCredentials);
                String contractAddress = adminHandler.createSmartContract();
                BufferedWriter bw = new BufferedWriter(new FileWriter(path + "admin"
                        + File.separator + "contract.txt"));
                bw.write(contractAddress);
                bw.close();
            }
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

    private void startGeth() {
        try {
            cmdUtil.startGethNetwork();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage());
        }
    }


    public static void main(String[] args) {
        new BlockchainAppGUI("Blockchain App for admins").setVisible(true);
    }
}
