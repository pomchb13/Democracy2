package servlet;

import beans.Politician;
import beans.RightEnum;
import beans.Vote;
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
import java.util.Map;

/**
 * Created by Leonhard on 28.11.2017.
 */
@WebServlet(urlPatterns = {"/newVoteSL"})
public class newVoteSL extends HttpServlet {
    public LinkedList<Vote> voteList = new LinkedList<>();
    private loggedUsers lU = loggedUsers.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/newVoteUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String hash = (String) session.getAttribute("hash");
        if (!lU.compareRights(hash, RightEnum.ADMIN)) {
            resp.sendRedirect("/loginSL");
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
                    //ToDo: Save in Blockchain
                    voteList.add(newVote);
                    this.getServletContext().setAttribute("voteList", voteList);
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
                System.out.println(req.getParameter("input_cand_Title"));
                Enumeration<String> eNames = req.getParameterNames();
                while (eNames.hasMoreElements() != false) {
                    System.out.println(eNames.nextElement().toString());
                }
                System.out.println(eNames.toString());
                System.out.println("Nach der Ausgabe");

                if (!req.getParameter("hiddenVote").equals("null")) {

                    System.out.println("Adding Politician");
                    String voteTitle = ServletUtil.filter(req.getParameter("hiddenVote"));
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
                    LinkedList<Vote> liVoteList = (LinkedList) this.getServletContext().getAttribute("voteList");
                    System.out.println(liVoteList.toString());
                    int count = 0;
                    for (Vote v : liVoteList) {
                        if (v.getTitle().equals(voteTitle)) {
                            System.out.println("seas");
                            LinkedList<Politician> liPolit = v.getLiCandidates();
                            System.out.println(liPolit.toString());
                            liPolit.add(pot);
                            v.setLiCandidates(liPolit);
                            liVoteList.set(count, v);
                            break;
                        }
                        count++;
                    }
                    System.out.println(liVoteList.toString());

                    this.getServletContext().setAttribute("voteList", liVoteList);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                error = "Bitte überprüfen Sie ihre Eingaben!" + ex.toString();
                req.setAttribute("errorPol", error);
            }
        }

        processRequest(req, resp);
    }
}