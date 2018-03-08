package servlet;

import beans.ElectionData;
import beans.PollData;
import beans.RightEnum;
import handler.AdminHandler;
import handler.ElectionHandler;
import handler.PollHandler;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ewald on 06.03.2018.
 */
@WebServlet(urlPatterns = {"/AdminSettingsSL"})
public class AdminSettingsSL extends HttpServlet {

    private LoggedUsers lU = LoggedUsers.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {

        //ToDo: Windi's code bitte hier hin --> Alle Wahlen und Abstimmungen!
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
        LinkedList<PollData> liPollList = new LinkedList<>();
        LinkedList<ElectionData> liElectioData = new LinkedList<>();
        try {
            Credentials credentials = (Credentials) session.getAttribute("credentials");
            AdminHandler adminHandler = new AdminHandler(credentials);
            adminHandler.loadSmartContract(AdminReader.getAdminContractAddress(this.getServletContext().getRealPath("/res/admin/")));

            List<Address> list = null;
            try {
                list = adminHandler.getAllContractAddresses(new Address(credentials.getAddress()));
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
            for (Address a : list) {
                try {
                    ElectionHandler electionHandler = new ElectionHandler(credentials);
                    electionHandler.loadSmartContract(a);
                    liElectioData.add(electionHandler.getElectionData());
                } catch (Exception ex) {
                    PollHandler pollHandler = new PollHandler(credentials);
                    pollHandler.loadSmartContract(a);
                    liPollList.add(pollHandler.getPollData());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.getServletContext().setAttribute("PollList", liPollList);
        this.getServletContext().setAttribute("ElectionList", liElectioData);
        if (!lU.compareRights(hash, RightEnum.ADMIN)) {
            resp.sendRedirect("/LoginSL");
        } else {
            processRequest(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(Files.exists(Paths.get((String) this.getServletContext().getRealPath("/res/userLists/userlist.xlsx"))))
        Files.delete(Paths.get((String) this.getServletContext().getRealPath("/res/userLists/userlist.xlsx")));
        processRequest(req, resp);
    }
}
