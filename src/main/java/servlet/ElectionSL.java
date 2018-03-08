package servlet;

import beans.CandidateData;
import beans.ElectionData;
import beans.PollData;
import handler.ElectionHandler;
import handler.PollHandler;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import user.LoggedUsers;
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

/**
 * Created by Ewald on 28.02.2018.
 *
 *
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
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());
        System.out.println(request.getParameterNames().toString());
        System.out.println(request.getParameter("candidateID"));
        if (request.getParameter("candidateID") != null)
        {
            int id = Integer.parseInt(request.getParameter("candidateID").trim());
            ElectionData ed = (ElectionData) request.getSession().getAttribute("election");
            CandidateData cd = ed.getLiCandidates().get(id);

            try (PrintWriter out = response.getWriter()) {
                out.format("%s;%s;%s;%s;%s;%s;%s", cd.getTitle(), cd.getForename(), cd.getSurname(),
                        cd.getBirthday(), cd.getParty(), cd.getSlogan(), cd.getPortraitPath());
                out.flush();
            }
        }
        else{
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
        int val = Integer.parseInt(req.getParameter("optradio"));
        ElectionHandler electionHandler = new ElectionHandler((Credentials) req.getSession().getAttribute("credentials"));
        LoggedUsers lu = LoggedUsers.getInstance();
        String address = lu.getAddessOfHash((String) req.getSession().getAttribute("hash"));
        if (!address.isEmpty()) {
            try {
                electionHandler.giveRightToVote(new Address(address));
                electionHandler.vote(new Uint8(val), new Address(address));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        PollData pollData = (PollData) req.getSession().getAttribute("election");
        if (pollData.isDiagramOption()) {
            resp.sendRedirect("EvaluationBarChartUI.jsp");
        } else {
            resp.sendRedirect("ThankYouUI.jsp");
        }

       processRequest(req, resp);
    }

}
