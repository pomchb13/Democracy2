package beans;

import java.time.LocalDate;

/**
 * Author:          Ewald Hartmann
 * Description:     beans class responsible for the CandidateData object
 */
public class CandidateData {

    private String title;
    private String forename;
    private String surname;
    private LocalDate birthday;
    private String party;
    private String slogan;
    private String portraitPath;
    private int voteCount;


    public CandidateData(String title, String forename, String surname, LocalDate birthday, String party, String slogan, String portraitPath) {
        this.title = title;
        this.forename = forename;
        this.surname = surname;
        this.birthday = birthday;
        this.party = party;
        this.slogan = slogan;
        this.portraitPath = portraitPath;
    }


    public CandidateData(String title, String forename, String surname, LocalDate birthday, String party, String slogan, int voteCount) {
        this.title = title;
        this.forename = forename;
        this.surname = surname;
        this.birthday = birthday;
        this.party = party;
        this.slogan = slogan;
        this.voteCount = voteCount;
        this.portraitPath = "";
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
