package test;



import org.web3j.crypto.Credentials;
import util.ExcelHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Tester{
    private JTextField tf_user;
    private JTextField tf_password;
    private JButton login;
    private JPanel paMain;
    private Credentials cr;
    private static final String PATH = choosePath();
    private BlockchainUtil bu;
    private File adminFile;


    public Tester()
    {
        bu = new BlockchainUtil();

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String address = PATH+File.separator+getFileName(tf_user.getText());
               String passwd = tf_password.getText();
               cr = bu.loginToBlockhain(address,passwd);
               if(cr==null)
               {
                   JOptionPane.showMessageDialog(null,"login fehlgeschlagen");
               }
               else
               {
                   Boolean admin = false;
                   try {
                       admin = ExcelHandler.proofIfAdmin(adminFile,tf_user.getText());
                   } catch (Exception e1) {
                       e1.printStackTrace();
                   }
                   if(admin)
                   {
                       int butt = (JOptionPane.showConfirmDialog(null,"Admin interface öffnen=?","asdf",JOptionPane.YES_NO_OPTION));
                      if(butt!=JOptionPane.NO_OPTION)
                      {
                          openAdmin();
                      }
                      else
                      {
                          openUser();
                      }
                   }
                   else
                   {
                    openUser();
                   }
               }
                System.out.println("adf");
                System.out.println(getFileName(tf_user.getText()));
            }
        });
    }

    private void openAdmin()
    {

    }

    private void openUser()
    {

    }

    private static String choosePath() {
      return "F:\\Geth\\geth_data\\keystore";
    }

    public String getFileName(String adress)
    {

        //5365a53ffbeadb2bd0d02a16d2f73c50a6999b78
        String name="";
        File file = new File(PATH);
        if(file.exists() && file.isDirectory())
        {
            String[] files = file.list();
           for(String fileName : files)
           {
               if(fileName.contains(adress))
               {
                   name = fileName;
               }
           }
        }
        return name;
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
