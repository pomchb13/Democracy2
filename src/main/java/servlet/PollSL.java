package servlet;

import beans.PollData;
import handler.PollHandler;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
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
        int val = Integer.parseInt(req.getParameter("optradio"));
        PollHandler handler = new PollHandler((Credentials) req.getSession().getAttribute("credentials"));
        LoggedUsers lu = LoggedUsers.getInstance();
        String address = lu.getAddessOfHash((String) req.getSession().getAttribute("hash"));
        if (!address.isEmpty()) {
            try {
                handler.giveRightToVote(new Address(address));
                handler.vote(new Uint8(val),new Address(address));
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
