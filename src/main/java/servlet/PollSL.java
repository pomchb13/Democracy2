package servlet;

import beans.ElectionData;
import beans.PollData;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import poll.PollTester;
import user.LoggedUsers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ewald on 28.02.2018.
 */

@WebServlet(urlPatterns = {"/PollSl"})
public class PollSL extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/PollUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int val = (int) Integer.parseInt(req.getParameter("optradio"));
        //ToDo: Stimmer vergeben und dann Wahlrecht enziehen
        PollTester pollTester = new PollTester((Credentials) req.getSession().getAttribute("credentials"));
        LoggedUsers lu = LoggedUsers.getInstance();
        String address = lu.getAddessOfHash((String) req.getSession().getAttribute("hash"));
        if (!address.isEmpty()) {
            try {
                pollTester.giveRightToVote(new Address(address));
                pollTester.vote(new Uint8(val),new Address(address));
            } catch (Exception e) {
                //ToDo: Catch them all!
            }
        }
        PollData pollData = (PollData) req.getSession().getAttribute("poll");
        if (pollData.isDiagramOption()) {
            resp.sendRedirect("EvaluationBarChartUI.jsp");
        } else {
            resp.sendRedirect("ThankYouUI.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
