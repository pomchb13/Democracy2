package servlet;

import beans.*;
import handler.AdminHandler;
import handler.ElectionHandler;
import handler.PollHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import user.LoggedUsers;
import util.AdminReader;
import util.BlockchainUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


/**
 * Author: Ewald Hartmann
 * Description: This class is placed in the Backend from the "AdminSettingsUI.jsp" and is responsible for loading
 * all polls and elections from the Blockchain and set them to session scope so the "ActiveVotesUI.jsp" is able
 * to list them up. This servlet also checks if the administrator is logged in correctly.
 */
@WebServlet(urlPatterns = {"/AdminSettingsSL"})
public class AdminSettingsSL extends HttpServlet {

    //The instance where all logged users and administrators are saved
    private LoggedUsers userInstance = LoggedUsers.getInstance();

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminSettingsSL.class);
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/AdminSettingsUI.jsp");
        rd.forward(request, response);
    }

    /**
     * @param req
     * @param resp This method will be fired when the "AdminSettingsUI.jsp" will be loaded and so it fits perfectly for loading
     *             all elections and polls from Blockchain. This method also has double login validation. Firstly it checks if the Account
     *             is allowed to see this site. Then it checks if the Account is logged in the Blockchain. After that it creates
     *             Handler objects and then loads the elections and polls to later save it to the session scope.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String hash = (String) session.getAttribute("hash");

        //Check if Account is logged into platform
        if (!userInstance.compareRights(hash, RightEnum.ADMIN)) {
            resp.sendRedirect("/LoginSL");
        } else {
            LinkedList<PollData> pollList = new LinkedList<>();
            LinkedList<ElectionData> electionList = new LinkedList<>();
            try {
                Credentials credentials = (Credentials) session.getAttribute("credentials");

                //Create AdminHandler object
                AdminHandler adminHandler = new AdminHandler(credentials);

                //Load all contracts
                adminHandler.loadSmartContract(AdminReader.getAdminContractAddress(this.getServletContext().getRealPath("/res/admin/")));

                List<Address> contractList = null;
                try {
                    contractList = adminHandler.getAllContractAddresses(new Address(credentials.getAddress()));
                } catch (Exception ex) {
                    LOGGER.error("Error with the admin object: {}", ex.toString());
                }

                for (Address address : contractList) {
                    //try --> object is an election | catch --> object is a poll
                    try {
                        //Create ElectionHandler object
                        ElectionHandler electionHandler = new ElectionHandler(credentials);

                        //Load all Elections without candidates
                        electionHandler.loadSmartContract(address);

                        //Get election from electionHandler
                        ElectionData election = electionHandler.getElectionData();
                        LinkedList<CandidateData> candidateList = new LinkedList<>();

                        for (int i = 0; i < electionHandler.getCandidateArraySize(); i++) {
                            candidateList.add(electionHandler.getCandidateData(i));
                        }
                        election.setLiCandidates(candidateList);
                        electionList.add(election);
                    } catch (Exception ex) {
                        //Create PollHandler object
                        PollHandler pollHandler = new PollHandler(credentials);

                        //Load all polls without answers
                        pollHandler.loadSmartContract(address);

                        //Get poll from pollHandler
                        PollData poll = pollHandler.getPollData();

                        LinkedList<PollAnswer> liPollAnswer = new LinkedList<>();
                        for (int i = 0; i < pollHandler.getAnswerArraySize(); i++) {
                            liPollAnswer.add(pollHandler.getAnswerData(i));
                        }
                        poll.setAnswerList(liPollAnswer);
                        pollList.add(poll);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Account is not logged in: {}", e.toString());
            }
            session.setAttribute("pollList", pollList);
            session.setAttribute("electionList", electionList);

            processRequest(req, resp);
        }
    }

    /**
     * @param req
     * @param resp This method will be fired from the "UploadUserFileUI.jsp". When the administrator successfully downloaded the
     *             userkeys, this method will delete the .xlsx file from the server environment
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Files.exists(
                Paths.get(
                        this.getServletContext().getRealPath("/res/userLists/userlist.xlsx")))) {
            Files.delete(
                    Paths.get(
                            this.getServletContext().getRealPath("/res/userLists/userlist.xlsx")));
        }

        processRequest(req, resp);
    }
}
