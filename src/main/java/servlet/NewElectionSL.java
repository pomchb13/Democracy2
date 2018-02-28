package servlet;

import beans.Politician;
import beans.RightEnum;
import beans.Vote;
import election.ElectionTester;
import org.web3j.crypto.Credentials;
import user.loggedUsers;
import util.ServletUtil;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Leonhard on 28.11.2017.
 */
@WebServlet(urlPatterns = {"/NewElectionSL"})
public class NewElectionSL extends HttpServlet {
    private loggedUsers lU = loggedUsers.getInstance();
    private ElectionTester election;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/NewVoteUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String hash = (String) session.getAttribute("hash");
        if (!lU.compareRights(hash, RightEnum.ADMIN)) {
            resp.sendRedirect("/LoginSL");
        } else {
            processRequest(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getParameter("actionButton"));

        if (req.getParameter("actionButton").equals("createVote")) {
            String error = null;
            try {
                String voteTitle = ServletUtil.filter(req.getParameter("input_Title"));
                String fromDate = ServletUtil.filter((req.getParameter("input_Start")));
                String dueDate = ServletUtil.filter(req.getParameter("input_End"));

                boolean voteDiagrams;
                if (ServletUtil.filter(req.getParameter("input_DiaOption")).equals("1"))
                    voteDiagrams = true;
                else
                    voteDiagrams = false;


                LocalDate vote_fromDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                LocalDate vote_dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                if (LocalDate.now().isBefore(vote_fromDate)
                        || LocalDate.now().isEqual(vote_fromDate)
                        && LocalDate.now().isBefore(vote_dueDate)
                        && vote_fromDate.isBefore(vote_dueDate)) {
                    Vote newVote = new Vote(voteTitle, vote_fromDate, vote_dueDate, voteDiagrams);
                    //Testing Statement --> Delete After not needed
                    System.out.println(newVote.toString());
                    this.getServletContext().setAttribute("newElection", newVote);
                } else {
                    throw new Exception("wrong Date");
                }
            } catch (Exception ex) {
                error = "Bitte überprüfen Sie Ihre Eingaben!";
                req.setAttribute("errorVot", error);
            }
        } else if (req.getParameter("actionButton").equals("addPolitician")) {
            String error = null;
            try {

                if (this.getServletContext().getAttribute("newElection") != null) {

                    System.out.println("Adding Politician");
                    String candTitle = ServletUtil.filter(req.getParameter("input_cand_Title"));
                    String candFirstname = ServletUtil.filter(req.getParameter("input_cand_Firstname"));
                    String candLastname = ServletUtil.filter((req.getParameter("input_cand_Lastname")));
                    LocalDate dateOfBirth = LocalDate.parse(ServletUtil.filter(req.getParameter("input_cand_Birthday")), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    String party = ServletUtil.filter(req.getParameter("input_cand_Party"));
                    String slogan = ServletUtil.filter(req.getParameter("input_cand_Slogan"));
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new File(this.getServletContext().getRealPath("/")
                                + "images"
                                + File.separator
                                + ServletUtil.filter(req.getParameter("input_cand_Picture"))));
                    } catch (IOException ex) {
                        img = null;
                    }
                    Politician pot = new Politician(candTitle, candFirstname, candLastname, dateOfBirth, party, slogan, img);
                    System.out.println(pot.toString());
                    int count = 0;

                    Vote newVote = (Vote) this.getServletContext().getAttribute("newElection");
                    LinkedList<Politician> liPolit = newVote.getLiCandidates();
                    System.out.println(liPolit.toString());
                    liPolit.add(pot);
                    newVote.setLiCandidates(liPolit);

                    System.out.println(newVote.toString());
                    this.getServletContext().setAttribute("newElection", newVote);


                }
            } catch (Exception ex) {
                ex.printStackTrace();
                error = "Bitte überprüfen Sie ihre Eingaben!" + ex.toString();
                req.setAttribute("errorPol", error);
            }
        } else {
            //TODO:Save complete Election in Blockchain --> DONE
            Credentials cr = (Credentials) req.getSession().getAttribute("credentials");
            election = new ElectionTester(cr);
            Vote liVoteList = (Vote) this.getServletContext().getAttribute("newElection");
            try {
                election.createContract(liVoteList.getLiCandidates().size(), liVoteList.getTitle(), liVoteList.getDate_from(),
                        liVoteList.getDate_due(), liVoteList.isShow_diagrams());
            } catch (Exception e) {
                //TODO: Exception handling
                e.printStackTrace();
            }
            List<Politician> liPolit = (List<Politician>) this.getServletContext().getAttribute("politList");
            for (int i = 0; i < liPolit.size(); i++) {
                try {
                    election.storeCandidateData(i, liPolit.get(i).getTitle(), liPolit.get(i).getForename(), liPolit.get(i).getSurname(),
                            liPolit.get(i).getBirthday(), liPolit.get(i).getParty(), liPolit.get(i).getSlogan());
                } catch (Exception e) {
                    //TODO: Exception handling
                    e.printStackTrace();
                }
            }
        }

        processRequest(req, resp);
    }
}