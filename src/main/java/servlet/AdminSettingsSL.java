package servlet;

import beans.*;
import handler.AdminHandler;
import handler.ElectionHandler;
import handler.PollHandler;
import logger.Logger;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import user.LoggedUsers;
import util.AdminReader;

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
 * Created by Ewald on 06.03.2018.
 */
@WebServlet(urlPatterns = {"/AdminSettingsSL"})
public class AdminSettingsSL extends HttpServlet {

    private LoggedUsers userInstance = LoggedUsers.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/AdminSettingsUI.jsp");
        rd.forward(request, response);
    }

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
                    Logger.logError("Error with the admin object: " + ex.toString(), AdminSettingsSL.class);
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
                Logger.logError("Account is not logged in: " + e.toString(), AdminSettingsSL.class);
            }
            session.setAttribute("pollList", pollList);
            session.setAttribute("electionList", electionList);

            processRequest(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Files.exists(Paths.get(this.getServletContext().getRealPath("/res/userLists/userlist.xlsx"))))
            Files.delete(Paths.get( this.getServletContext().getRealPath("/res/userLists/userlist.xlsx")));
        processRequest(req, resp);
    }
}
