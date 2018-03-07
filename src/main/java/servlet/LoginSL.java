package servlet;

import beans.*;
import contracts.AdminContract;
import handler.AdminHandler;
import handler.ElectionHandler;
import handler.PollHandler;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import user.HashGenerator;
import user.LoggedUsers;
import util.BlockchainUtil;
import util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;


@WebServlet(urlPatterns = {"/LoginSL"})
public class LoginSL extends HttpServlet {

    private HashGenerator hashInstance;
    private LoggedUsers userInstance;
    private LinkedList<String> liFilenames = new LinkedList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
        System.out.println(BlockchainUtil.getPATH());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/LoginUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("tries") == null) {
            req.getSession().setAttribute("tries", 4);
        }
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // read useranme and password
        String username = ServletUtil.filter((String) req.getParameter("username"));
        String password = ServletUtil.filter((String) req.getParameter("password"));

        // check if there is more then one try left
        if ((int) req.getSession().getAttribute("tries") > 0) {
            try {
                // login to Blockchain
                Credentials cr = BlockchainUtil.loginToBlockhain(username, password);
                System.out.println(cr.getAddress());

                //set credentials to the session scope
                req.getSession().setAttribute("credentials", cr);
                System.out.println("After setting cr to session");
                //Generation of MD5 Hash
                hashInstance = HashGenerator.getTheInstance();
                String hash = hashInstance.get_SHA_256_SecurePassword(username + password);
                AdminHandler adminHandler = new AdminHandler(cr);
                File file = new File(this.getServletContext().getRealPath("/res/admin/") + "contract.txt");
                if (file.exists()) {


                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String adminContractAddress = br.readLine();
                    adminHandler.loadSmartContract(new Address(adminContractAddress));
                    System.out.println("wtf1");
                    RightEnum right = RightEnum.USER;
                    System.out.println("wtf2");


                    System.out.println("wtf3");
                    // set right of user
                    if (adminHandler.checkIfAdmin(new Address(username))) {
                        System.out.println("isAdmin start");
                        right = RightEnum.ADMIN;
                        System.out.println("isAdmin");
                    }
                    System.out.println("after hashing");
                    //log user in list
                    //if there is an exception the user is already logged in
                    userInstance = LoggedUsers.getInstance();
                    try {
                        userInstance.login(hash, right, username);
                        System.out.println("after loggin in");
                    } catch (Exception e) {
                        req.setAttribute("error", "User bereits eingeloggt");
                    }

                    // check rights of user
                    if (right == RightEnum.USER) {
                        // set hash and right to the session scope
                        HttpSession session = req.getSession();
                        session.setAttribute("hash", hash);
                        session.setAttribute("right", right);

                        //set MaxInactiveInterval to 15 minutes
                        session.setMaxInactiveInterval(15 * 60);

                        // check on with kind of vote the user is authorized
                        // if there is an exception the user is only authorized to vote for an poll
                        // otherwise he/she is authorized to vote for an election
                        try {
                            ElectionHandler eh = new ElectionHandler(cr);
                            String address = eh.getVoteAddressForVoter(new Address(password));
                            eh.loadSmartContract(new Address(address));
                            ElectionData ed = eh.getElectionData();
                            HttpSession ses = req.getSession();
                            ses.setAttribute("election", ed);
                            ses.setMaxInactiveInterval(15 * 60);
                            // check if user has already voted
                            if (eh.getAlreadyVotedForVoter(new Address(address))) {
                                // forwald to EvaluationBarChartUI
                                resp.sendRedirect("EvaluationBarChartUI.jsp");
                            } else {
                                // forwald to ElectionUI
                                resp.sendRedirect("ElectionUI.jsp");
                            }
                        } catch (Exception ex) {
                            try {
                                PollHandler ph = new PollHandler(cr);
                                String address = ph.getVoteAddressForVoter(new Address(password));
                                ph.loadSmartContract(new Address(address));
                                PollData pd = ph.getPollData();
                                HttpSession ses = req.getSession();
                                ses.setAttribute("poll", pd);
                                ses.setMaxInactiveInterval(15 * 60);
                                // check if user has already voted
                                if (ph.getAlreadyVotedForVoter(new Address(address))) {
                                    // forwald to EvaluationBarChartUI
                                    resp.sendRedirect("EvaluationBarChartUI.jsp");
                                } else {
                                    // forwald to PollUI
                                    resp.sendRedirect("PollUI.jsp");
                                }
                            } catch (Exception e) {
                                req.setAttribute("error", "Es ist ein Fehler bei der Weiterleitung aufgetreten");
                            }
                        }
                        // User is an admin
                    } else if (right == RightEnum.ADMIN) {
                        System.out.println("in Admin");
                        // set hash and right to the session scope
                        HttpSession session = req.getSession();
                        session.setAttribute("hash", hash);
                        session.setAttribute("right", right);

                        //set MaxInactiveInterval to 15 minutes
                        session.setMaxInactiveInterval(15 * 60);

                        // set all uploaded files to the list liFilenames
                        this.getAllFiles();

                        // set the list on the request scope
                        this.getServletContext().setAttribute("liFilenames", liFilenames);

                        //forward to AdminSettingsSL
                        resp.sendRedirect("AdminSettingsSL");
                    }
                }
                else
                {
                    //TODO: Error Page --> damit admin admin-contract erstellt
                }
            } catch (Exception e) {
                int tries = (int) req.getSession().getAttribute("tries");
                if (tries > 1) {
                    req.setAttribute("error", "Fehlerhafte Logindaten! Es bleiben noch " + tries-- + " versuche");
                } else {
                    req.setAttribute("error", "Fehlerhafte Logindaten! Es bleiben noch ein " + tries-- + " Versuch");
                }
                req.getSession().setAttribute("tries", tries);
            }

        }
    }

    /**
     * reads all files in the /res/images folder
     */
    private void getAllFiles() {
        File file = new File(this.getServletContext().getRealPath("/res/images"));
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].getName().equals("dummy")) {
                if (!files[i].isDirectory()) {
                    liFilenames.add(files[i].getName());
                }
            }
        }
    }
}
