package servlet;

import beans.Politician;
import beans.Vote;

import util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@WebServlet(urlPatterns = {"/addCandidateToVoteSL"})
@MultipartConfig(location = "../java/images")
public class AddCandidateToVoteSL extends HttpServlet {
    private String filePath;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/newVoteUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String error = null;
        try {

            System.out.println(req.getParameter("hiddenVote"));
            if (!req.getParameter("hiddenVote").equals("null")) {

                System.out.println("Adding Politician");
                String voteTitle = ServletUtil.filter(req.getParameter("hiddenVote"));
                String candTitle = ServletUtil.filter(req.getParameter("input_cand_Title"));
                String candFirstname = ServletUtil.filter(req.getParameter("input_cand_Firstname"));
                String candLastname = ServletUtil.filter((req.getParameter("input_cand_Lastname")));
                LocalDate dateOfBirth = LocalDate.parse(ServletUtil.filter(req.getParameter("input_cand_Birthday")), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                String party = ServletUtil.filter(req.getParameter("input_cand_Party"));
                String slogan = ServletUtil.filter(req.getParameter("input_cand_Slogan"));
                //Uploading File to Server
                Part filePart = req.getPart("input_cand_Picture");
                String filename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                InputStream fileContent = filePart.getInputStream();
                String Path = this.getServletContext().getRealPath("/res/");

                System.out.println(filePart);
                System.out.println(filename);
                System.out.println(fileContent);


                filePart.write("image");


                Politician pot = new Politician(candTitle, candFirstname, candLastname, dateOfBirth, party, slogan, null);
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
        processRequest(req, resp);
    }
}
