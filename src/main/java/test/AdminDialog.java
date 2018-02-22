package test;

import election.ElectionTester;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import poll.PollTester;
import util.Utilorschloader;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;

public class AdminDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton volksabstimmungButton;
    private JButton wahlButton;

    private PollTester pt;
    private ElectionTester et;
    private Credentials cr;
    private int election;
    private Tester tester;

    private boolean ok = false;

    public AdminDialog(Credentials cr,Tester tester) {
        this.cr = cr;
        this.tester = tester;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        volksabstimmungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pt = new PollTester(cr);
                    pt.createContract(2, "Gangl is schiach", LocalDate.now(), LocalDate.now().plusDays(10), false);
                    Utilorschloader.saveContractAddress(pt.getContractAddress(),tester.getContractAddressPath());
                    ok = true;
                    election=1;
                } catch (CipherException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        wahlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    et = new ElectionTester(cr);
                    et.createContract(2, "Pommer is a schiach", LocalDate.now(), LocalDate.now().plusDays(20), false);
                    Utilorschloader.saveContractAddress(et.getContractAddress(),tester.getContractAddressPath());
                    ok = true;
                    election=2;
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (CipherException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void onOK() {

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public int getElection(){return election;}

    public PollTester getPt() {
        return pt;
    }

    public ElectionTester getEt() {
        return et;
    }

    public boolean isOk() {
        return ok;
    }

    public static void main(String[] args) {
        //AdminDialog dialog = new AdminDialog(null);
        //dialog.pack();
        //dialog.setVisible(true);
        //System.exit(0);
    }
}
