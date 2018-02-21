package test;


import election.Election;
import election.ElectionTester;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import poll.PollTester;

import javax.swing.*;
import java.awt.event.*;

public class UserDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton bt1;
    private JButton bt2;
    private JLabel lbwhal1;
    private JLabel lbTitle;
    private JLabel lbwahl2;
    private Credentials cr;
    private VoteType vt;
    private ElectionTester et;
    private PollTester pt;
    public UserDialog() {
        initComponents();

        bt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btOne();
            }
        });
        bt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               btTwo();
            }
        });
    }



    private void initComponents() {
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
    }

    public UserDialog(Credentials cr, ElectionTester et)
    {
        initComponents();
        this.cr = cr;
        initElection(et);
    }

    private void initElection(ElectionTester et) {
        this.et = et;
        try {
            lbTitle.setText(et.getElectionData().getTitle());
            lbwhal1.setText(et.getCandidateData(0).getFirstname());
            lbwahl2.setText(et.getCandidateData(1).getFirstname());
        } catch (Exception e) {
            e.printStackTrace();
        }
        vt=VoteType.ELECTION;
    }

    public UserDialog(Credentials cr, PollTester pt)
    {
        initComponents();
        this.cr=cr;
        initPoll(pt);

    }

    private void initPoll(PollTester pt) {
        this.pt=pt;
        try {
            lbTitle.setText(pt.getPollData().getTitle());
            lbwhal1.setText(pt.getAnswerData(0).getDescription());
            lbwahl2.setText(pt.getAnswerData(1).getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
        vt=VoteType.POLL;
    }

    public void btTwo() {
        if(vt.equals(VoteType.ELECTION))
        {
            try {
                et.vote(new Uint8(1),new Address(cr.getAddress()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                pt.vote(new Uint8(1),new Address(cr.getAddress()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void btOne() {
        if(vt.equals(VoteType.ELECTION))
        {
            try {
                et.vote(new Uint8(0),new Address(cr.getAddress()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                pt.vote(new Uint8(0),new Address(cr.getAddress()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void onOK() {
        if(vt.equals(VoteType.ELECTION))
        {
            try {
                System.out.println(et.winningCandidate());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                System.out.println(pt.winningAnswer());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        UserDialog dialog = new UserDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
