package test;



import election.ElectionTester;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import poll.PollTester;
import user.UserCreator;
import util.BlockchainUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Tester{
    private JTextField tf_user;
    private JTextField tf_password;
    private JButton login;
    private JPanel paMain;
    private JButton btcreateUser;
    private Credentials cr;
    private static final String PATH = "F:\\Geth\\geth_data\\keystore\\";
    private AdminDialog dialog;

    private PollTester pt;
    private ElectionTester et;
    private String contractAddressPath = System.getProperty("user.dir")+File.separator+"src"+File.separator+ "images" +File.separator+"ContractAdresses.txt";


    public Tester()
    {

        //0x5365a53ffbeadb2bd0d02a16d2f73c50a6999b78
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String address = BlockchainUtil.getFileName(tf_user.getText());
               String passwd = tf_password.getText();
                try {
                    cr = BlockchainUtil.loginToBlockhain(address,passwd);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (CipherException e1) {
                    e1.printStackTrace();
                }
                if(cr==null)
               {
                   JOptionPane.showMessageDialog(null,"login fehlgeschlagen");
               }
               else
               {
                   Boolean admin = true;
                   try {
                       //admin = ExcelHandler.proofIfAdmin(adminFile,tf_user.getText());
                   } catch (Exception e1) {
                       e1.printStackTrace();
                   }
                   if(admin)
                   {
                       int butt = (JOptionPane.showConfirmDialog(null,"Admin interface öffnen=?","asdf",JOptionPane.YES_NO_OPTION));
                      if(butt==JOptionPane.YES_OPTION)
                      {
                          openAdmin();
                          admin=false;
                      }
                      else
                      {
                          //openUser();
                      }
                   }
                   else
                   {
                    openUser();
                   }
               }

            }
        });
        btcreateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserCreator uc = new UserCreator();
                try {
                    System.out.println(uc.createNewUserAddress("1234",PATH));
                } catch (CipherException e1) {
                    e1.printStackTrace();
                } catch (InvalidAlgorithmParameterException e1) {
                    e1.printStackTrace();
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                } catch (NoSuchProviderException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * User
     * 0x5365a53ffbeadb2bd0d02a16d2f73c50a6999b78
     * 0x062b4ef9ac762dd77354625d8ece6f493e868b96
     * 0xfa98e684ecdc84a5258aab8c3c7936c17f2594bb
     * 0x4fb52a51258209ed3d794e0660a09e21617cd925
     */

    private void openAdmin()
    {
        dialog = new AdminDialog(cr,this);
        dialog.pack();
        dialog.setVisible(true);
        if(dialog.isOk())
        {
            pt = dialog.getPt();
            et = dialog.getEt();
            System.out.println(pt.getContractAddress());
            System.out.println(et.getContractAddress());
        }
    }

    private void openUser()
    {
        if(et==null)
        {
            UserDialog udialog = new UserDialog(cr,pt);
        }
        else
        {
            UserDialog udialog = new UserDialog(cr,et);
        }

    }


    public void setContractAddressPath(String a)
    {
        this.contractAddressPath=a;
    }
    public String getContractAddressPath()
    {
        return this.contractAddressPath;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("scheiß orsch designer");
        frame.setContentPane(new Tester().paMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}
