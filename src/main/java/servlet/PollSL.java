package servlet;

import beans.PollAnswer;
import beans.PollData;
import beans.RightEnum;
import handler.AdminHandler;
import handler.PollHandler;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Author:          Ewald Hartmann
 * Created on:
 * Description:     This servlet java class is responsible for a secure voting.
 */

@WebServlet(urlPatterns = {"/PollSL"})
public class PollSL extends HttpServlet {

    //The Instance where all logged users and administrator are saved
    private LoggedUsers userInstance = LoggedUsers.getInstance();

    /**
     * @param config In the init Method we need to set the path in the BlockchainUtil to the keystore in the server environment.
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    /**
     * @param request
     * @param response In this Method we only get the RequestDispatcher which forwards to the "NewPollUI.jsp".
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/PollUI.jsp");
        rd.forward(request, response);
    }

    /**
     * @param req
     * @param resp The doPost method will be fired when the voter uses his right to vote and firstly selects a candidate and then
     *             presses the votebutton. The PollHandler communicates with the Blockchain that the Blockchain kann save the vote
     *             and delete the right to vote for that poll. If the administrator enabled seeing real-time results the voter will
     *             be forwarded to the "EvaluationBarChartUI.jsp", else he will be forwarded to the "ThankYouUI.jsp".
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //Get the value from the radiobuttongroup
            int val = Integer.parseInt(req.getParameter("optradio").trim());

            //Create pollHandler object
            PollHandler pollHandler = new PollHandler((Credentials) req.getSession().getAttribute("credentials"));

            String address = null;
            try {
                //Get Credentials from session scope
                Credentials user = (Credentials) req.getSession().getAttribute("credentials");

                //Create adminHandler object
                AdminHandler adminHandler = new AdminHandler(user);

                //Load Admincontract to adminHandler
                adminHandler.loadSmartContract(AdminReader.getAdminContractAddress(this.getServletContext().getRealPath("/res/admin/")));

                //Get Accountaddress from the Hash(user+salt+password)
                address = userInstance.getAddressOfHash((String) req.getSession().getAttribute("hash"));

                //Get Contractaddress from poll for voter
                Address contractAddress = adminHandler.getContractAddressForVoter(new Address(user.getAddress()));

                //Load Contrat in pollHandler
                pollHandler.loadSmartContract(contractAddress);

                //Give vote to the answer
                pollHandler.vote(new Uint8(val), new Address(address));

                //get the updated pollobject without answers
                PollData pd = pollHandler.getPollData();

                //Add Answers to the pollobject
                LinkedList<PollAnswer> liPollAnswer = new LinkedList<>();
                for (int i = 0; i < pollHandler.getAnswerArraySize(); i++) {
                    liPollAnswer.add(pollHandler.getAnswerData(i));
                }
                pd.setAnswerList(liPollAnswer);

                //Check if showing diagrams is allowed
                if (pd.isDiagramOption()) {
                    req.getSession().setAttribute("voteObject", pd);
                    resp.sendRedirect("EvaluationBarChartUI.jsp");
                } else {
                    resp.sendRedirect("ThankYouUI.jsp");
                }
            } catch (Exception e) {
                Logger.logError("Error while voting: " + e.toString(), PollSL.class);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Bitte wählen Sie einen Kandidaten aus.");
        }
    }

    /**
     * @param req
     * @param resp Because the doGet will be fired everytime we load the JSP, we had the method to check, if the person is logged in,
     *             in there. It only takes the hash from the session object and checks with the loggedUserInstance if the login is correct.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String hash = (String) session.getAttribute("hash");
        if (!userInstance.compareRights(hash, RightEnum.USER)) {
            resp.sendRedirect("/LoginSL");
        } else {
            processRequest(req, resp);
        }
    }
}
