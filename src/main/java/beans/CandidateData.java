package beans;

import java.time.LocalDate;

/**
 * Author:          Ewald Hartmann
 * Created on:
 * Description:     beans class responsible for the CandidateData object
 */
public class CandidateData {

    /* academic title of the politician */
    private String title;
    /* Forename of the politican */
    private String forename;
    /* surename of the politican */
    private String surname;
    /* birthday of the politican */
    private LocalDate birthday;
    /* party of the politican */
    private String party;
    /* Solgen of the politican */
    private String slogan;
    /* Picture of the politican */
    private String portraitPath;
    /* number of votes for the candidate */
    private int voteCount;

    /**
     *
     * @param title academic title of the candidate
     * @param forename
     * @param surname
     * @param birthday
     * @param party
     * @param slogan
     * @param portraitPath
     */
    public CandidateData(String title, String forename, String surname, LocalDate birthday, String party, String slogan, String portraitPath) {
        this.title = title;
        this.forename = forename;
        this.surname = surname;
        this.birthday = birthday;
        this.party = party;
        this.slogan = slogan;
        this.portraitPath = portraitPath;
    }

    /**
     *
     * @param title academic title of the candidate
     * @param forename
     * @param surname
     * @param birthday
     * @param party
     * @param slogan
     * @param voteCount
     */
    public CandidateData(String title, String forename, String surname, LocalDate birthday, String party, String slogan, int voteCount) {
        this.title = title;
        this.forename = forename;
        this.surname = surname;
        this.birthday = birthday;
        this.party = party;
        this.slogan = slogan;
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "CandidateData{" +
                "title='" + title + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", party='" + party + '\'' +
                ", slogan='" + slogan + '\'' +
                ", portraitPath=" + portraitPath +
                ", voteCount=" + voteCount +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getPortraitPath() {
        return portraitPath;
    }

    public void setPortraitPath(String portraitPath) {
        this.portraitPath = portraitPath;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
