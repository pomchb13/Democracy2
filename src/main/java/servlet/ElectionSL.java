package servlet;

import beans.CandidateData;
import beans.ElectionData;
import handler.AdminHandler;
import handler.ElectionHandler;
import logger.Logger;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint8;
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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Author:          Ewald Hartmann
 * Description:
 */
@WebServlet(urlPatterns = {"/ElectionSL"})
public class ElectionSL extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //AJAX Request for showing candidate info in a popup <div>
        if (request.getParameter("candidateID") != null) {
            int id = Integer.parseInt(request.getParameter("candidateID").trim());
            ElectionData ed = (ElectionData) request.getSession().getAttribute("voteObject");
            CandidateData cd = ed.getLiCandidates().get(id);
            request.setAttribute("candidateID", null);
            //Writes the whole candidate info back to the xmlhttp object from the javascript file
            try (PrintWriter out = response.getWriter()) {
                String path = cd.getPortraitPath();
                path = File.separator + "res" + path.split("res")[1];
                out.format("%s;%s;%s;%s;%s;%s;%s", cd.getTitle(), cd.getForename(), cd.getSurname(),
                        cd.getBirthday(), cd.getParty(), cd.getSlogan(), path);
                out.flush();
            }
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("ElectionUI.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //gets the value from the radiobuttongroup
            int val = Integer.parseInt(req.getParameter("optradio").trim());

            //Creates electionHandler object
            ElectionHandler electionHandler = new ElectionHandler((Credentials) req.getSession().getAttribute("credentials"));

            //The Instance where all logged users and administrator are saved
            LoggedUsers userInstance = LoggedUsers.getInstance();
            String address = null;
            try {

                //Get Credentials from the session scope
                Credentials user = (Credentials) req.getSession().getAttribute("credentials");

                //Create adminHandler object
                AdminHandler adminHandler = new AdminHandler(user);

                //Load the Admincontract to adminHandler
                adminHandler.loadSmartContract(AdminReader.getAdminContractAddress(this.getServletContext().getRealPath("/res/admin/")));

                //Get userAddress from Hash(username+salt+password)
                address = userInstance.getAddressOfHash((String) req.getSession().getAttribute("hash"));

                //Get Contractaddress from election for voter
                Address contractAddress = adminHandler.getContractAddressForVoter(new Address(user.getAddress()));

                //Load Usercontract to elecitonHandler
                electionHandler.loadSmartContract(contractAddress);

                //Give the vote to a candidate
                electionHandler.vote(new Uint8(val), new Address(address));

                //Get the election from electionHandler
                ElectionData ed = electionHandler.getElectionData();

                //Add the candidates to the electionobject
                LinkedList<CandidateData> liCandidateList = new LinkedList<>();
                for (int i = 0; i < electionHandler.getCandidateArraySize(); i++) {
                    liCandidateList.add(electionHandler.getCandidateData(i));
                }
                ed.setLiCandidates(liCandidateList);

                //Check if showing diagrams is allowed
                if (ed.isShow_diagrams()) {
                    req.getSession().setAttribute("voteObject", ed);
                    resp.sendRedirect("EvaluationBarChartUI.jsp");
                } else {
                    resp.sendRedirect("ThankYouUI.jsp");
                }
            } catch (Exception e) {
                Logger.logError("Error while voting: " + e.toString(), ElectionSL.class);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Bitte wählen Sie einen Kandidaten aus.");
        }
    }

}
