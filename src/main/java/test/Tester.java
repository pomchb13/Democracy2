package test;



import election.ElectionTester;
import org.web3j.crypto.Credentials;
import poll.PollTester;
import util.BlockchainUtil;
import util.ExcelHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Tester{
    private JTextField tf_user;
    private JTextField tf_password;
    private JButton login;
    private JPanel paMain;
    private Credentials cr;
    private static final String PATH = "D:\\Ethereum\\geth_data\\keystore\\";

    private File adminFile;

    private PollTester pt;
    private ElectionTester et;

    public Tester()
    {


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String address = PATH + File.separator + BlockchainUtil.getFileName(tf_user.getText());
               String passwd = tf_password.getText();
               cr = BlockchainUtil.loginToBlockhain(address,passwd);
               if(cr==null)
               {
                   JOptionPane.showMessageDialog(null,"login fehlgeschlagen");
               }
               else
               {
                   Boolean admin = true;
                   try {
                       admin = ExcelHandler.proofIfAdmin(adminFile,tf_user.getText());
                   } catch (Exception e1) {
                       e1.printStackTrace();
                   }
                   if(admin)
                   {
                       int butt = (JOptionPane.showConfirmDialog(null,"Admin interface öffnen=?","asdf",JOptionPane.YES_NO_OPTION));
                      if(butt==JOptionPane.YES_OPTION)
                      {
                          openAdmin();
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
    }

    private void openAdmin()
    {
        AdminDialog dialog = new AdminDialog();
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
