package servlet;

import beans.PollData;
import beans.RightEnum;
import handler.AdminHandler;
import handler.PollHandler;
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

/**
 * Author:          Ewald Hartmann
 * Created on:
 * Description:     This servlet java class is responsible for a secure voting.
 */

@WebServlet(urlPatterns = {"/PollSL"})
public class PollSL extends HttpServlet {

    //The Instance where all logged users and administrator are saved
    private LoggedUsers lU = LoggedUsers.getInstance();

    /**
     * @param config
     * In the init Method we need to set the path in the BlockchainUtil to the keystore in the server environment.
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    /**
     * @param request
     * @param response
     * In this Method we only get the RequestDispatcher which forwards to the "NewPollUI.jsp".
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
     * @param resp
     * The doPost method will be fired when the voter uses his right to vote and firstly selects a candidate and then
     * presses the votebutton. The PollHandler communicates with the Blockchain that the Blockchain kann save the vote
     * and delete the right to vote for that poll. If the administrator enabled seeing real-time results the voter will
     * be forwarded to the "EvaluationBarChartUI.jsp", else he will be forwarded to the "ThankYouUI.jsp".
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int val = Integer.parseInt(req.getParameter("optradio").trim());
        PollHandler pollHandler = new PollHandler((Credentials) req.getSession().getAttribute("credentials"));
        LoggedUsers lu = LoggedUsers.getInstance();
        String address = null;
            try {
                Credentials user = (Credentials) req.getSession().getAttribute("credentials");
                AdminHandler adminHandler = new AdminHandler(user);
                adminHandler.loadSmartContract(AdminReader.getAdminContractAddress(this.getServletContext().getRealPath("/res/admin")));
                address= lu.getAddessOfHash((String)req.getSession().getAttribute("hash"));
                Address contractAddress= adminHandler.getContractAddressForVoter(new Address(user.getAddress()));
                pollHandler.loadSmartContract(contractAddress);
                pollHandler.vote(new Uint8(val),new Address(address));
            } catch (Exception e) {
                e.printStackTrace();
            }
        processRequest(req,resp);

    }

    /**
     * @param req
     * @param resp
     *
     * Because the doGet will be fired everytime we load the JSP, we had the method to check, if the person is logged in,
     * in there. It only takes the hash from the session object and checks with the loggedUserInstance if the login is correct.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String hash = (String) session.getAttribute("hash");
        if (!lU.compareRights(hash, RightEnum.USER)) {
            resp.sendRedirect("/LoginSL");
        } else {
            processRequest(req, resp);
        }
    }
}
