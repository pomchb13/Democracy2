package servlet;

import beans.*;
import handler.AdminHandler;
import handler.ElectionHandler;
import handler.PollHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Address;
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
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 * Author:          Ewald Hartmann
 * Description:     Servlet behind the LoginUI
 */

@WebServlet(urlPatterns = {"/LoginSL"})
public class LoginSL extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginSL.class);

    private HashGenerator hashInstance;
    private LoggedUsers userInstance;
    private LinkedList<String> liFilenames = new LinkedList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/LoginUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //read useranme and password
        String username = ServletUtil.filter(req.getParameter("username"));
        String password = ServletUtil.filter(req.getParameter("password"));
        //Check if inputFields are empty
        if (username.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Bitte die Einlogdaten eingeben");
        }
        try {
            //login to Blockchain
            Credentials credentials = BlockchainUtil.loginToBlockhain(username, password);
            LOGGER.info("Account {} logged into Blockchain", credentials.getAddress());
            //Create session-object
            HttpSession session = req.getSession();

            //set credentials to the session scope
            session.setAttribute("credentials", credentials);

            //Generation of MD5 Hash
            hashInstance = HashGenerator.getTheInstance();
            String hash = hashInstance.get_SHA_256_SecurePassword(username + password);

            //Check if the contract.txt file exists
            File file = new File(this.getServletContext().getRealPath("/res/admin/") + "contract.txt");
            if (file.exists()) {
                AdminHandler adminHandler = new AdminHandler(credentials);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String adminContractAddress = br.readLine();
                adminHandler.loadSmartContract(new Address(adminContractAddress));

                //Set right to User before it checks if the credentials are admin credentials
                RightEnum right = RightEnum.USER;
                if (adminHandler.checkIfAdmin(new Address(username))) {
                    right = RightEnum.ADMIN;
                }

                //log user in list
                //if there is an exception the user is already logged in
                userInstance = LoggedUsers.getInstance();
                try {
                    userInstance.login(hash, right, username);
                    if (right == RightEnum.ADMIN) {
                        LOGGER.info("Admin {} is logged in",credentials.getAddress());
                    } else {
                        LOGGER.info("User {} is logged in",credentials.getAddress());
                    }
                } catch (Exception e) {
                    req.setAttribute("error", "Account bereits eingeloggt");
                    processRequest(req, resp);
                }

                // check rights of logged in Account
                if (right == RightEnum.USER) {
                    // set hash and right to the session scope
                    session.setAttribute("hash", hash);
                    session.setAttribute("right", right);

                    //set MaxInactiveInterval to 15 minutes
                    session.setMaxInactiveInterval(15 * 60);

                    // check on which kind of vote the user is authorized for
                    // if there is an exception the user is only authorized to vote for a poll
                    // otherwise he/she is authorized to vote for an election
                    String contractAddress = adminHandler.getContractAddressForVoter(new Address(credentials.getAddress())).toString();
                    try {
                        ElectionHandler electionHandler = new ElectionHandler(credentials);
                        electionHandler.loadSmartContract(new Address(contractAddress));
                        ElectionData election = electionHandler.getElectionData();
                        LinkedList<CandidateData> candidateList = new LinkedList<>();
                        //Add Candidates from Blockchain to election-object
                        for (int i = 0; i < electionHandler.getCandidateArraySize(); i++) {
                            candidateList.add(electionHandler.getCandidateData(i));
                        }
                        election.setLiCandidates(candidateList);
                        session.setAttribute("voteObject", election);
                        // check if user has already voted
                        if (electionHandler.getAlreadyVotedForVoter(new Address(credentials.getAddress())) || election.getDate_due().isBefore(LocalDate.now())) {
                            // forward to EvaluationBarChartUI
                            resp.sendRedirect("EvaluationBarChartUI.jsp");
                        } else if ((LocalDate.now().isAfter(election.getDate_from()) || LocalDate.now().isEqual(election.getDate_from())) &&
                                (LocalDate.now().isBefore(election.getDate_due()) || LocalDate.now().isEqual(election.getDate_due()))) {
                            // forward to ElectionSL
                            resp.sendRedirect("/ElectionSL");
                        } else {
                            resp.sendRedirect("/TimerUI.jsp");
                        }
                    } catch (Exception ex) {
                        try {
                            PollHandler pollHandler = new PollHandler(credentials);
                            pollHandler.loadSmartContract(new Address(contractAddress));
                            PollData poll = pollHandler.getPollData();
                            LinkedList<PollAnswer> pollAnswerList = new LinkedList<>();
                            //Add Asnwers from Blockchain to poll-object
                            for (int i = 0; i < pollHandler.getAnswerArraySize(); i++) {
                                pollAnswerList.add(pollHandler.getAnswerData(i));
                            }
                            poll.setAnswerList(pollAnswerList);

                            //Set poll-object to session scope
                            session.setAttribute("voteObject", poll);

                            //check if user has already voted
                            if (pollHandler.getAlreadyVotedForVoter(new Address(credentials.getAddress())) || poll.getDate_due().isBefore(LocalDate.now())) {
                                //forward to EvaluationBarChartUI
                                resp.sendRedirect("EvaluationBarChartUI.jsp");
                            } else if ((LocalDate.now().isAfter(poll.getDate_from()) || LocalDate.now().isEqual(poll.getDate_from())) &&
                                    (LocalDate.now().isBefore(poll.getDate_due()) || LocalDate.now().isEqual(poll.getDate_due()))) {
                                // forward to ElectionSL
                                resp.sendRedirect("/PollSL");
                            } else {
                                resp.sendRedirect("/TimerUI.jsp");
                            }
                        } catch (Exception e) {
                            req.setAttribute("error", "Es ist ein Fehler bei der Weiterleitung aufgetreten");
                            LOGGER.error("Error while forwarding {}",e.toString());
                        }
                    }
                    //Account is an Adminaccount
                } else if (right == RightEnum.ADMIN) {
                    //set hash and right to the session scope
                    session.setAttribute("hash", hash);
                    session.setAttribute("right", right);

                    //set all uploaded files to the list "liFilenames"
                    this.getAllFiles();

                    //set the list of filenames to the application scope
                    this.getServletContext().setAttribute("liFilenames", liFilenames);

                    //forward to AdminSettingsSL
                    resp.sendRedirect("AdminSettingsSL");
                }
            } else {
                LOGGER.error("Contract file doesn't exist");
                resp.sendRedirect("ErrorUI.jsp");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Username oder Passwort ist falsch");
            processRequest(req, resp);
        }
    }

    /**
     * reads all files from the /res/images folder
     */
    private void getAllFiles() {
        File file = new File(this.getServletContext().getRealPath("/res/images"));
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].getName().equals("dummy") && !liFilenames.contains(files[i].getName())) {
                if (!files[i].isDirectory()) {
                    liFilenames.add(files[i].getName());
                }
            }
        }
    }
}
